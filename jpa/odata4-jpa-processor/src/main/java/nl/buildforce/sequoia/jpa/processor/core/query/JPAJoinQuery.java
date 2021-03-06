package nl.buildforce.sequoia.jpa.processor.core.query;

import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAAssociationPath;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPACollectionAttribute;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAEntityType;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAPath;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.exception.ODataJPAException;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.exception.ODataJPAModelException;
import nl.buildforce.sequoia.jpa.processor.core.api.JPAODataCRUDContextAccess;
import nl.buildforce.sequoia.jpa.processor.core.api.JPAODataRequestContextAccess;
import nl.buildforce.sequoia.jpa.processor.core.exception.ODataJPAQueryException;
import org.apache.olingo.commons.api.edm.EdmNavigationProperty;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriInfoResource;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceNavigation;
import org.apache.olingo.server.api.uri.UriResourceProperty;
import org.apache.olingo.server.api.uri.queryoption.OrderByItem;
import org.apache.olingo.server.api.uri.queryoption.OrderByOption;
import org.apache.olingo.server.api.uri.queryoption.expression.Member;

import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.AbstractQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static nl.buildforce.sequoia.jpa.processor.core.converter.JPAExpandResult.ROOT_RESULT_KEY;

public class JPAJoinQuery extends JPAAbstractJoinQuery implements JPACountQuery {

  private static List<JPANavigationPropertyInfo> determineNavigationInfo(
      final JPAODataCRUDContextAccess sessionContext, final UriInfoResource uriResource) throws ODataJPAException, ODataApplicationException {

    return Util.determineNavigationPath(sessionContext.getEdmProvider().getServiceDocument(), uriResource
        .getUriResourceParts(), uriResource);
  }

  private static JPAEntityType determineTargetEntityType(final JPAODataCRUDContextAccess sessionContext,
      final JPAODataRequestContextAccess requestContext) throws ODataJPAException {

    return sessionContext.getEdmProvider().getServiceDocument().getEntity(Util.determineTargetEntitySet(requestContext
        .getUriInfo().getUriResourceParts()).getName());
  }

  public JPAJoinQuery(final OData odata, final JPAODataCRUDContextAccess sessionContext,
                      final Map<String, List<String>> requestHeaders, final JPAODataRequestContextAccess requestContext)
          throws ODataJPAException, ODataApplicationException {

    super(odata, sessionContext, determineTargetEntityType(sessionContext, requestContext),
        requestContext, requestHeaders, determineNavigationInfo(sessionContext, requestContext.getUriInfo()));
  }

  /**
   * Fulfill $count requests. For details see
   * <a href=
   * "http://docs.oasis-open.org/odata/odata/v4.0/errata03/os/complete/part1-protocol/odata-v4.0-errata03-os-part1-protocol-complete.html#_Toc453752288"
   * >OData Version 4.0 Part 1 - 11.2.5.5 System Query Option $count</a>
   * @return
   * @throws ODataApplicationException
   */
  @Override
  public Long countResults() throws ODataApplicationException {
    /*
     * URL example:
     * .../Organizations?$count=true
     * .../Organizations/$count
     * .../Organizations('3')/Roles/$count
     */
    final int handle = debugger.startRuntimeMeasurement(this, "countResults");
    final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
    try {
      createFromClause(Collections.emptyList(), Collections.emptyList(), countQuery, lastInfo);

      final Expression<Boolean> whereClause = createWhere();
      if (whereClause != null)
        countQuery.where(whereClause);
      countQuery.select(cb.countDistinct(target));
      debugger.stopRuntimeMeasurement(handle);
      return em.createQuery(countQuery).getSingleResult();
    } catch (JPANoSelectionException e) {
      return 0L;
    }
  }

  @Override
  public JPAConvertibleResult execute() throws ODataApplicationException {
    // Pre-process URI parameter, so they can be used at different places
    final int handle = debugger.startRuntimeMeasurement(this, "execute");

    final List<JPAAssociationPath> orderByNaviAttributes = extractOrderByNaviAttributes();
    final Collection<JPAPath> selectionPath = buildSelectionPathList(this.uriResource);
    try {
      final Map<String, From<?, ?>> joinTables = createFromClause(orderByNaviAttributes, selectionPath, cq, lastInfo);

      cq.multiselect(createSelectClause(joinTables, selectionPath, target, groups)).distinct(
          determineDistinct());

      final Expression<Boolean> whereClause = createWhere();
      if (whereClause != null)
        cq.where(whereClause);

      cq.orderBy(createOrderByList(joinTables, uriResource.getOrderByOption()));

      if (!orderByNaviAttributes.isEmpty())
        cq.groupBy(createGroupBy(joinTables, selectionPath));

      final TypedQuery<Tuple> tq = em.createQuery(cq);
      addTopSkip(tq);

      final HashMap<String, List<Tuple>> result = new HashMap<>(1);
      final int resultHandle = debugger.startRuntimeMeasurement(tq, "getResultList");
      final List<Tuple> intermediateResult = tq.getResultList();

      debugger.stopRuntimeMeasurement(resultHandle);
      result.put(ROOT_RESULT_KEY, intermediateResult);

      debugger.stopRuntimeMeasurement(handle);
      return returnResult(selectionPath, result);
    } catch (JPANoSelectionException e) {
      debugger.stopRuntimeMeasurement(handle);
      return returnEmptyResult(selectionPath);
    }
  }

  public List<JPANavigationPropertyInfo> getNavigationInfo() {
    return navigationInfo;
  }

  @Override
  public AbstractQuery<?> getQuery() {
    return cq;
  }

  private List<Expression<?>> createGroupBy(final Map<String, From<?, ?>> joinTables,
      final Collection<JPAPath> selectionPathList) {
    final int handle = debugger.startRuntimeMeasurement(this, "createGroupBy");

    final List<Expression<?>> groupBy =
        new ArrayList<>();

    for (final JPAPath jpaPath : selectionPathList) {
      groupBy.add(ExpressionUtil.convertToCriteriaPath(joinTables, root, jpaPath.getPath()));
    }

    debugger.stopRuntimeMeasurement(handle);
    return groupBy;
  }

  private Expression<Boolean> createWhere() throws ODataApplicationException {
    return addWhereClause(super.createWhere(uriResource, navigationInfo), createProtectionWhere(claimsProvider));
  }

  /**
   * Desired if SELECT DISTINCT shall be generated. This is required e.g. if multiple values for the same claims are
   * present. As a DISTINCT is usually slower the decision algorithm my need to be enhanced in the future
   * @return
   */
  private boolean determineDistinct() {
    return claimsProvider.isPresent();
  }

  private List<JPAAssociationPath> extractOrderByNaviAttributes() throws ODataApplicationException {

    final List<JPAAssociationPath> naviAttributes = new ArrayList<>();
    final OrderByOption orderBy = uriResource.getOrderByOption();
    if (orderBy != null) {
      for (final OrderByItem orderByItem : orderBy.getOrders()) {
        final org.apache.olingo.server.api.uri.queryoption.expression.Expression expression = orderByItem.getExpression();
        if (expression instanceof Member) {
          final UriInfoResource resourcePath = ((Member) expression).getResourcePath();
          final StringBuilder pathString = new StringBuilder();
          for (final UriResource uriResource : resourcePath.getUriResourceParts()) {
            try {
              if (uriResource instanceof UriResourceNavigation) {
                final EdmNavigationProperty edmNaviProperty = ((UriResourceNavigation) uriResource).getProperty();
                naviAttributes.add(jpaEntity.getAssociationPath(edmNaviProperty.getName()));
              } else if (uriResource instanceof UriResourceProperty && ((UriResourceProperty) uriResource)
                  .isCollection()) {
                pathString.append(((UriResourceProperty) uriResource).getProperty().getName());
                naviAttributes.add(((JPACollectionAttribute) jpaEntity.getPath(pathString.toString())
                    .getLeaf()).asAssociation());

              } else if (uriResource instanceof UriResourceProperty) {
                pathString.append(((UriResourceProperty) uriResource).getProperty().getName());
                pathString.append(JPAPath.PATH_SEPARATOR);
              }
            } catch (ODataJPAModelException e) {
              throw new ODataJPAQueryException(ODataJPAQueryException.MessageKeys.QUERY_RESULT_CONV_ERROR,
                  HttpStatusCode.INTERNAL_SERVER_ERROR, e);
            }
          }
        }
      }
    }
    return naviAttributes;
  }

  private JPAConvertibleResult returnEmptyResult(final Collection<JPAPath> selectionPath) {
    if (lastInfo.getAssociationPath() != null
        && (lastInfo.getAssociationPath().getLeaf() instanceof JPACollectionAttribute))
      return new JPACollectionQueryResult(jpaEntity, lastInfo.getAssociationPath(), selectionPath);
    return new JPAExpandQueryResult(jpaEntity, selectionPath);
  }

  private JPAConvertibleResult returnResult(final Collection<JPAPath> selectionPath,
      final HashMap<String, List<Tuple>> result) {
    if (lastInfo.getAssociationPath() != null
        && (lastInfo.getAssociationPath().getLeaf() instanceof JPACollectionAttribute))
      return new JPACollectionQueryResult(result, null, jpaEntity, lastInfo.getAssociationPath(), selectionPath);
    return new JPAExpandQueryResult(result, null, jpaEntity, selectionPath);
  }

}