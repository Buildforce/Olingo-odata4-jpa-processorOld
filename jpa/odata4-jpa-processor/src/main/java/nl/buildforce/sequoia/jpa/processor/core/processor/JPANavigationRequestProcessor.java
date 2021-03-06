package nl.buildforce.sequoia.jpa.processor.core.processor;

import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAAssociationPath;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.exception.ODataJPAException;
import nl.buildforce.sequoia.jpa.processor.core.api.JPAODataCRUDContextAccess;
import nl.buildforce.sequoia.jpa.processor.core.api.JPAODataPage;
import nl.buildforce.sequoia.jpa.processor.core.api.JPAODataRequestContextAccess;
import nl.buildforce.sequoia.jpa.processor.core.converter.JPAExpandResult;
import nl.buildforce.sequoia.jpa.processor.core.converter.JPATupleChildConverter;
import nl.buildforce.sequoia.jpa.processor.core.exception.ODataJPAProcessorException;
import nl.buildforce.sequoia.jpa.processor.core.query.JPACollectionItemInfo;
import nl.buildforce.sequoia.jpa.processor.core.query.JPACollectionJoinQuery;
import nl.buildforce.sequoia.jpa.processor.core.query.JPAConvertibleResult;
import nl.buildforce.sequoia.jpa.processor.core.query.JPAExpandItemInfo;
import nl.buildforce.sequoia.jpa.processor.core.query.JPAExpandItemInfoFactory;
import nl.buildforce.sequoia.jpa.processor.core.query.JPAExpandJoinQuery;
import nl.buildforce.sequoia.jpa.processor.core.query.JPAExpandQueryResult;
import nl.buildforce.sequoia.jpa.processor.core.query.JPAJoinQuery;
import nl.buildforce.sequoia.jpa.processor.core.query.JPAKeyBoundary;
import nl.buildforce.sequoia.jpa.processor.core.query.JPANavigationPropertyInfo;
import nl.buildforce.sequoia.jpa.processor.core.query.Util;
import org.apache.olingo.commons.api.data.ComplexValue;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.*;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfoResource;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceKind;
import org.apache.olingo.server.api.uri.UriResourcePartTyped;
import org.apache.olingo.server.api.uri.queryoption.CountOption;
import org.apache.olingo.server.api.uri.queryoption.SystemQueryOptionKind;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static nl.buildforce.sequoia.jpa.processor.core.converter.JPAExpandResult.ROOT_RESULT_KEY;
import static nl.buildforce.sequoia.jpa.processor.core.exception.ODataJPAProcessorException.MessageKeys.*;

public final class JPANavigationRequestProcessor extends JPAAbstractGetRequestProcessor {
  private final ServiceMetadata serviceMetadata;
  private final UriResource lastItem;
  private final JPAODataPage page;

  public JPANavigationRequestProcessor(final OData odata, final ServiceMetadata serviceMetadata,
      final JPAODataCRUDContextAccess context, final JPAODataRequestContextAccess requestContext)
      throws ODataJPAException {

    super(odata, context, requestContext);
    this.serviceMetadata = serviceMetadata;
    final List<UriResource> resourceParts = uriInfo.getUriResourceParts();
    this.lastItem = resourceParts.get(resourceParts.size() - 1);
    this.page = requestContext.getPage();
  }

  @Override
  public <K extends Comparable<K>> void retrieveData(final ODataRequest request, final ODataResponse response,
      final ContentType responseFormat) throws ODataJPAException, ODataApplicationException, SerializerException {

    final int handle = debugger.startRuntimeMeasurement(this, "retrieveData");
    // Create a JPQL Query and execute it
    JPAJoinQuery query;
    try {
      query = new JPAJoinQuery(odata, sessionContext, request.getAllHeaders(), requestContext);
    } catch (ODataException e) {
      debugger.stopRuntimeMeasurement(handle);
      throw new ODataJPAProcessorException(QUERY_PREPARATION_ERROR, HttpStatusCode.INTERNAL_SERVER_ERROR, e);
    }

    final JPAConvertibleResult result = query.execute();
    // Read Expand and Collection
    final Optional<JPAKeyBoundary> keyBoundary = result.getKeyBoundary(requestContext, query.getNavigationInfo());
    result.putChildren(readExpandEntities(request.getAllHeaders(), query.getNavigationInfo(), uriInfo, keyBoundary));
    // Convert tuple result into an OData Result
    final int converterHandle = debugger.startRuntimeMeasurement(this, "convertResult");
    EntityCollection entityCollection;
    try {
      entityCollection = result.asEntityCollection(new JPATupleChildConverter(sd, odata.createUriHelper(),
          serviceMetadata)).get(ROOT_RESULT_KEY);
      debugger.stopRuntimeMeasurement(converterHandle);
    } catch (ODataApplicationException e) {
      debugger.stopRuntimeMeasurement(converterHandle);
      debugger.stopRuntimeMeasurement(handle);
      throw new ODataJPAProcessorException(QUERY_RESULT_CONV_ERROR, HttpStatusCode.INTERNAL_SERVER_ERROR, e);
    }
    // Set Next Link
    entityCollection.setNext(buildNextLink(page));
    // Count results if requested
    final CountOption countOption = uriInfo.getCountOption();
    if (countOption != null && countOption.getValue())
      entityCollection.setCount(new JPAJoinQuery(odata, sessionContext, request.getAllHeaders(), requestContext)
          .countResults().intValue());

    /*
     * See part 1:
     * -9.1.1 Response Code 200 OK: A request that does not create a resource returns 200 OK if it is completed
     * successfully and the value of the resource is not null. In this case, the response body MUST contain the value of
     * the resource specified in the request URL.
     * - 9.2.1 Response Code 404 Not Found: 404 Not Found indicates that the resource specified by the request URL does
     * not exist. The response body MAY provide additional information.
     * - 11.2.1 Requesting Individual Entities:
     * -- If no entity exists with the key values specified in the request URL, the service responds with 404 Not Found.
     * - 11.2.3 Requesting Individual Properties:
     * -- If the property is single-valued and has the null value, the service responds with 204 No Content.
     * -- If the property is not available, for example due to permissions, the service responds with 404 Not Found.
     * - 11.2.6 Requesting Related Entities:
     * -- If the navigation property does not exist on the entity indicated by the request URL, the service returns 404
     * Not Found.
     * -- If the relationship terminates on a collection, the response MUST be the format-specific representation of the
     * collection of related entities. If no entities are related, the response is the format-specific representation of
     * an empty collection.
     * -- If the relationship terminates on a single entity, the response MUST be the format-specific representation of
     * the related single entity. If no entity is related, the service returns 204 No Content.
     */
    if (hasNoContent(entityCollection.getEntities()))
      response.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    else if (doesNoeExists(entityCollection.getEntities()))
      response.setStatusCode(HttpStatusCode.NOT_FOUND.getStatusCode());
    // 200 OK indicates that either a result was found or that the a Entity Collection query had no result
    else if (entityCollection.getEntities() != null) {
      final int serializerHandle = debugger.startRuntimeMeasurement(serializer, "serialize");
      final SerializerResult serializerResult = serializer.serialize(request, entityCollection);
      debugger.stopRuntimeMeasurement(serializerHandle);
      createSuccesResponse(response, responseFormat, serializerResult);
    } else {
      // A request returns 204 No Content if the requested resource has the null value, or if the service applies a
      // return=minimal preference. In this case, the response body MUST be empty.
      response.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    }
    debugger.stopRuntimeMeasurement(handle);
  }

  private URI buildNextLink(final JPAODataPage page) throws ODataJPAProcessorException {
    if (page != null && page.getSkipToken() != null) {
      try {
        if (page.getSkipToken() instanceof String)
          return new URI(Util.determineTargetEntitySet(uriInfo.getUriResourceParts()).getName() + "?"
              + SystemQueryOptionKind.SKIPTOKEN.toString() + "='" + page.getSkipToken() + "'");
        else
          return new URI(Util.determineTargetEntitySet(uriInfo.getUriResourceParts()).getName() + "?"
              + SystemQueryOptionKind.SKIPTOKEN.toString() + "=" + page.getSkipToken().toString());
      } catch (URISyntaxException e) {
        throw new ODataJPAProcessorException(ODATA_MAXPAGESIZE_NOT_A_NUMBER, HttpStatusCode.INTERNAL_SERVER_ERROR, e);
      }
    }
    return null;
  }

  private boolean complexHasNoContent(final List<Entity> entities) {
    final String name;
    if (entities.isEmpty())
      return false;
    name = Util.determineStartNavigationPath(uriInfo.getUriResourceParts()).getProperty().getName();
    final Property property = entities.get(0).getProperty(name);
    if (property != null) {
      for (Property p : ((ComplexValue) property.getValue()).getValue()) {
        if (p.getValue() != null) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean doesNoeExists(List<Entity> entities) throws ODataApplicationException {
    // handle ../Organizations('xx')
    return (entities.isEmpty()
        && (lastItem.getKind() == UriResourceKind.primitiveProperty
            || lastItem.getKind() == UriResourceKind.complexProperty
            || lastItem.getKind() == UriResourceKind.entitySet
                && !Util.determineKeyPredicates(lastItem).isEmpty()));
  }

  private boolean hasNoContent(List<Entity> entities) {

    if (lastItem.getKind() == UriResourceKind.primitiveProperty
        || lastItem.getKind() == UriResourceKind.navigationProperty
        || lastItem.getKind() == UriResourceKind.complexProperty) {

      if (((UriResourcePartTyped) this.lastItem).isCollection()) {
        // Collections always return 200 no matter if type are empty or not
        return false;
      }

      if (lastItem.getKind() == UriResourceKind.primitiveProperty) {
        return primitiveHasNoContent(entities);
      }
      if (lastItem.getKind() == UriResourceKind.complexProperty) {
        return complexHasNoContent(entities);
      }
        return entities.isEmpty();
    }
    return false;
  }

  private boolean primitiveHasNoContent(final List<Entity> entities) {
    final String name;
    if (entities.isEmpty())
      return false;
    name = Util.determineStartNavigationPath(uriInfo.getUriResourceParts()).getProperty().getName();
    final Property property = entities.get(0).getProperty(name);
    return (property != null && property.getValue() == null);
  }

  /**
   * $expand is implemented as a recursively processing of all expands with a DB round trip per expand item.
   * Alternatively also a <i>big</i> join could be created. This would lead to a transport of redundant data, but has
   * only one round trip. As of now it has not been measured under which conditions which solution has the better
   * performance, but a big join has also the following draw back:
   * <ul>
   * <li>In case a multiple $expands are requested maybe on multiple levels
   * including filtering and ordering the query becomes very complex which reduces the maintainability and comes with
   * the risk that some databases are not able to handles those.</li>
   * <li>The number of returned columns becomes big, which may become a problem for some databases</li>
   * <li>This hard to create a big join for <code>$level=*</code></li>
   * <li>Server driven paging seems to be more complicated</li>
   * </ul>
   * and the goal is to implement a general solution, multiple round trips have been taken.
   * <p>For a general overview see:
   * <a href=
   * "http://docs.oasis-open.org/odata/odata/v4.0/errata02/os/complete/part1-protocol/odata-v4.0-errata02-os-part1-protocol-complete.html#_Toc406398298"
   * >OData Version 4.0 Part 1 - 11.2.4.2 System Query Option $expand</a><p>
   *

   * For a detailed description of the URI syntax see:
   * <a href=
   * "http://docs.oasis-open.org/odata/odata/v4.0/errata02/os/complete/part2-url-conventions/odata-v4.0-errata02-os-part2-url-conventions-complete.html#_Toc406398162"
   * >OData Version 4.0 Part 2 - 5.1.2 System Query Option $expand</a> boundary
   * @param headers
   * @param uriResourceInfo
   * @return
   * @throws ODataJPAException
   */
  private Map<JPAAssociationPath, JPAExpandResult> readExpandEntities(final Map<String, List<String>> headers,
      final List<JPANavigationPropertyInfo> parentHops, final UriInfoResource uriResourceInfo,
      final Optional<JPAKeyBoundary> keyBoundary) throws ODataJPAException, ODataApplicationException {

    final int handle = debugger.startRuntimeMeasurement(this, "readExpandEntities");
    final Map<JPAAssociationPath, JPAExpandResult> allExpResults =
        new HashMap<>();
    // x/a?$expand=b/c($expand=d,e/f)&$filter=...&$top=3&$orderBy=...
    // For performance reasons the expand query should only return results for the results of the higher-level query.
    // The solution for restrictions like a given key or a given filter condition, as it can be propagated to a
    // sub-query.
    // For $top and $skip things are more difficult as the Subquery does not support LIMIT and OFFSET, this is
    // done on the TypedQuery created out of the CriteriaQuery. In addition not all databases support LIMIT within a
    // sub-query used within EXISTS.
    // Solution: Forward the highest and lowest key from the root and create a "between" those.

    final List<JPAExpandItemInfo> itemInfoList = new JPAExpandItemInfoFactory()
        .buildExpandItemInfo(sd, uriResourceInfo, parentHops);
    for (final JPAExpandItemInfo item : itemInfoList) {
      final JPAExpandJoinQuery expandQuery = new JPAExpandJoinQuery(odata, sessionContext, item, headers,
          requestContext, keyBoundary);
      final JPAExpandQueryResult expandResult = expandQuery.execute();
      if (expandResult.getNoResults() > 0)
        // Only go the next hop if the current one has a result
        expandResult.putChildren(readExpandEntities(headers, item.getHops(), item.getUriInfo(), keyBoundary));
      allExpResults.put(item.getExpandAssociation(), expandResult);
    }

    // process collection attributes
    final List<JPACollectionItemInfo> collectionInfoList = new JPAExpandItemInfoFactory()
        .buildCollectionItemInfo(sd, uriResourceInfo, parentHops, requestContext.getGroupsProvider());
    for (final JPACollectionItemInfo item : collectionInfoList) {
      final JPACollectionJoinQuery collectionQuery = new JPACollectionJoinQuery(odata, sessionContext, em, item,
          headers, new JPAODataRequestContextImpl(item.getUriInfo(), requestContext), keyBoundary);
      final JPAExpandResult expandResult = collectionQuery.execute();
      allExpResults.put(item.getExpandAssociation(), expandResult);
    }
    debugger.stopRuntimeMeasurement(handle);
    return allExpResults;
  }

}