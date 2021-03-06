package nl.buildforce.sequoia.jpa.processor.core.query;

import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAAssociationPath;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPACollectionAttribute;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAEntityType;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAPath;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAServiceDocument;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.exception.ODataJPAModelException;
import nl.buildforce.sequoia.jpa.processor.core.exception.ODataJPAQueryException;
import nl.buildforce.sequoia.jpa.processor.core.exception.ODataJPAUtilException;
import org.apache.olingo.commons.api.edm.EdmBindingTarget;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmType;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriInfoResource;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceComplexProperty;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.UriResourceKind;
import org.apache.olingo.server.api.uri.UriResourceLambdaVariable;
import org.apache.olingo.server.api.uri.UriResourceNavigation;
import org.apache.olingo.server.api.uri.UriResourcePartTyped;
import org.apache.olingo.server.api.uri.UriResourceProperty;
import org.apache.olingo.server.api.uri.UriResourceValue;
import org.apache.olingo.server.api.uri.queryoption.ExpandItem;
import org.apache.olingo.server.api.uri.queryoption.ExpandOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Util {

  private Util() {
    // suppress instance creation
  }

  public static final String VALUE_RESOURCE = "$VALUE";

  public static EdmEntitySet determineTargetEntitySet(final List<UriResource> resources) {
    return determineTargetEntitySetAndKeys(resources).getEdmEntitySet();
  }

  public static EdmEntitySetInfo determineTargetEntitySetAndKeys(final List<UriResource> resources) {
    EdmEntitySet targetEdmEntitySet = null;
    List<UriParameter> targetKeyPredicates = new ArrayList<>();
    StringBuilder naviPropertyName = new StringBuilder();

    for (final UriResource resourceItem : resources)
      if (resourceItem.getKind() == UriResourceKind.entitySet) {
        targetEdmEntitySet = ((UriResourceEntitySet) resourceItem).getEntitySet();
        targetKeyPredicates = ((UriResourceEntitySet) resourceItem).getKeyPredicates();
      } else if (resourceItem.getKind() == UriResourceKind.complexProperty) {
        naviPropertyName.append(((UriResourceComplexProperty) resourceItem).getProperty().getName());
        naviPropertyName.append(JPAPath.PATH_SEPARATOR);
      } else if (resourceItem.getKind() == UriResourceKind.navigationProperty) {
        naviPropertyName.append(((UriResourceNavigation) resourceItem).getProperty().getName());
        targetKeyPredicates = ((UriResourceNavigation) resourceItem).getKeyPredicates();
        final EdmBindingTarget edmBindingTarget = targetEdmEntitySet.getRelatedBindingTarget(naviPropertyName.toString());
        if (edmBindingTarget instanceof EdmEntitySet) targetEdmEntitySet = (EdmEntitySet) edmBindingTarget;
        naviPropertyName = new StringBuilder();
      }
    return new EdmEntitySetResult(targetEdmEntitySet, targetKeyPredicates, naviPropertyName.toString());
  }

  public static EdmEntitySetInfo determineModifyEntitySetAndKeys(final List<UriResource> resources) {
    EdmEntitySet targetEdmEntitySet = null;
    List<UriParameter> targetKeyPredicates = new ArrayList<>();
    StringBuilder naviPropertyName = new StringBuilder();

    for (final UriResource resourceItem : resources) {
      if (resourceItem.getKind() == UriResourceKind.entitySet) {
        targetEdmEntitySet = ((UriResourceEntitySet) resourceItem).getEntitySet();
        targetKeyPredicates = ((UriResourceEntitySet) resourceItem).getKeyPredicates();
      } else
      if (resourceItem.getKind() == UriResourceKind.complexProperty) {
        naviPropertyName.append(((UriResourceComplexProperty) resourceItem).getProperty().getName());
        naviPropertyName.append(JPAPath.PATH_SEPARATOR);
      } else
      if (resourceItem.getKind() == UriResourceKind.navigationProperty) {
        naviPropertyName.append(((UriResourceNavigation) resourceItem).getProperty().getName());
        final List<UriParameter> keyPredicates = ((UriResourceNavigation) resourceItem).getKeyPredicates();
        if (!keyPredicates.isEmpty()) {
          targetKeyPredicates = keyPredicates;
          final EdmBindingTarget edmBindingTarget = targetEdmEntitySet.getRelatedBindingTarget(naviPropertyName
              .toString());
          if (edmBindingTarget instanceof EdmEntitySet)
            targetEdmEntitySet = (EdmEntitySet) edmBindingTarget;
          naviPropertyName = new StringBuilder();
        }
      }
    }
    return new EdmEntitySetResult(targetEdmEntitySet, targetKeyPredicates, naviPropertyName.toString());
  }

  /**
   * Finds an entity type from a navigation property
   */
  public static EdmEntityType determineTargetEntityType(final List<UriResource> resources) {
    EdmEntityType targetEdmEntity = null;

    for (final UriResource resourceItem : resources) {
      if (resourceItem.getKind() == UriResourceKind.navigationProperty) {
        // first try the simple way like in the example
        targetEdmEntity = (EdmEntityType) ((UriResourceNavigation) resourceItem).getType();
      }
    }
    return targetEdmEntity;
  }

  /**
   * Used for Serializer
   */
  public static UriResourceProperty determineStartNavigationPath(final List<UriResource> resources) {

    final int index = determineStartNavigationIndex(resources);
    if (index >= 0 && resources != null)
      return (UriResourceProperty) resources.get(index);
    return null;
  }

  /**
   * Finds the index of the first property after the last entity set or navigation resource. This is the resource that
   * will be returned in case a complex or primitive type is requested.<p>
   * Example1 : /Organizations -> -1<br>
   * Example2 : /Organizations('3')/AdministrativeInformation -> 1<br>
   * Example3 : /Organizations('3')/Roles -> -1<br>
   * Example4 : /Organizations('3')/Roles/RoleCategory -> 2<br>
   * Example4 : /Organizations('3')/AdministrativeInformation/Created/User/LastName -> 4
   */
  public static int determineStartNavigationIndex(final List<UriResource> resources) {

    if (resources != null) {
      for (int i = resources.size() - 1; i >= 0; i--) {
        final UriResource resourceItem = resources.get(i);
        if (resourceItem instanceof UriResourceEntitySet || resourceItem instanceof UriResourceNavigation)
          return i + 1;
      }
    }
    return -1;
  }

  public static String determinePropertyNavigationPath(final List<UriResource> resources) {
    final StringBuilder pathName = new StringBuilder();
    if (resources != null) {
      for (int i = resources.size() - 1; i >= 0; i--) {
        final UriResource resourceItem = resources.get(i);
        if (resourceItem instanceof UriResourceEntitySet || resourceItem instanceof UriResourceNavigation
            || resourceItem instanceof UriResourceLambdaVariable)
          break;
        if (resourceItem instanceof UriResourceValue) {
          pathName.insert(0, VALUE_RESOURCE);
          pathName.insert(0, JPAPath.PATH_SEPARATOR);
        } else if (resourceItem instanceof UriResourceProperty) {
          final UriResourceProperty property = (UriResourceProperty) resourceItem;
          pathName.insert(0, property.getProperty().getName());
          pathName.insert(0, JPAPath.PATH_SEPARATOR);
        }
      }
      if (pathName.length() > 0)
        pathName.deleteCharAt(0);
    }
    return pathName.toString();
  }

  public static String determinePropertyNavigationPrefix(final List<UriResource> resources) {
    return Util.determinePropertyNavigationPath(resources).split("/\\" + Util.VALUE_RESOURCE)[0];
  }

  public static JPAAssociationPath determineAssociation(final JPAServiceDocument sd, final EdmType naviStart,
      final StringBuilder associationName) throws ODataApplicationException {
    JPAEntityType naviStartType;

    try {
      naviStartType = sd.getEntity(naviStart);
      return naviStartType.getAssociationPath(associationName.toString());
    } catch (ODataJPAModelException e) {
      throw new ODataJPAUtilException(ODataJPAUtilException.MessageKeys.UNKNOWN_NAVI_PROPERTY,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public static Map<JPAExpandItem, JPAAssociationPath> determineAssociations(final JPAServiceDocument sd,
      final List<UriResource> startResourceList, final ExpandOption expandOption) throws ODataApplicationException {

    final Map<JPAExpandItem, JPAAssociationPath> pathList =
        new HashMap<>();
    final StringBuilder associationNamePrefix = new StringBuilder();

    UriResource startResourceItem = null;
    if (startResourceList != null && expandOption != null) {
      // Example1 : /Organizations('3')/AdministrativeInformation?$expand=Created/User
      // Example2 : /Organizations('3')/AdministrativeInformation?$expand=*
      // Association name needs AdministrativeInformation as prefix
      for (int i = startResourceList.size() - 1; i >= 0; i--) {
        startResourceItem = startResourceList.get(i);
        if (startResourceItem instanceof UriResourceEntitySet || startResourceItem instanceof UriResourceNavigation) {
          break;
        }
        associationNamePrefix.insert(0, JPAAssociationPath.PATH_SEPARATOR);
        associationNamePrefix.insert(0, ((UriResourceProperty) startResourceItem).getProperty().getName());
      }
      // Example1 : ?$expand=Created/User (Property/NavigationProperty)
      // Example2 : ?$expand=Parent/CodeID (NavigationProperty/Property)
      // Example3 : ?$expand=Parent,Children (NavigationProperty, NavigationProperty)
      // Example4 : ?$expand=*
      // Example5 : ?$expand=*/$ref,Parent
      // Example6 : ?$expand=Parent($levels=2)
      StringBuilder associationName;
      for (final ExpandItem item : expandOption.getExpandItems()) {
        if (item.isStar()) {
          final EdmEntitySet edmEntitySet = determineTargetEntitySet(startResourceList);
          try {
            final JPAEntityType jpaEntityType = sd.getEntity(edmEntitySet.getName());
            final List<JPAAssociationPath> associationPaths = jpaEntityType.getAssociationPathList();
            for (final JPAAssociationPath path : associationPaths) {
              pathList.put(new JPAExpandItemWrapper(item, (JPAEntityType) path.getTargetType()), path);
            }
          } catch (ODataJPAModelException e) {
            throw new ODataJPAUtilException(ODataJPAUtilException.MessageKeys.UNKNOWN_ENTITY_TYPE,
                HttpStatusCode.BAD_REQUEST);
          }
        } else {
          final List<UriResource> targetResourceList = item.getResourcePath().getUriResourceParts();
          associationName = new StringBuilder();
          associationName.append(associationNamePrefix);
          UriResource targetResourceItem;
          for (UriResource uriResource : targetResourceList) {
            targetResourceItem = uriResource;
            if (targetResourceItem.getKind() != UriResourceKind.navigationProperty) {
              associationName.append(((UriResourceProperty) targetResourceItem).getProperty().getName());
              associationName.append(JPAAssociationPath.PATH_SEPARATOR);
            } else {
              associationName.append(((UriResourceNavigation) targetResourceItem).getProperty().getName());
              break;
            }
          }
          if (item.getLevelsOption() != null)
            pathList.put(new JPAExpandLevelWrapper(sd, expandOption), Util.determineAssociation(sd,
                ((UriResourcePartTyped) startResourceItem).getType(), associationName));
          else
            pathList.put(new JPAExpandItemWrapper(sd, item), Util.determineAssociation(sd,
                ((UriResourcePartTyped) startResourceItem).getType(), associationName));
        }
      }
    }
    return pathList;
  }

  /**
   * Converts the OData navigation list into a intermediate one. Direction is top - down usage e.g. join query.<p>
   * The method only supports queries that start with an entity set.
   * @param sd
   * @param resourceParts
   * @return
   * @throws ODataApplicationException
   */
  public static List<JPANavigationPropertyInfo> determineNavigationPath(final JPAServiceDocument sd,
      final List<UriResource> resourceParts, final UriInfoResource uriInfo) throws ODataApplicationException {

    final List<JPANavigationPropertyInfo> pathList = new ArrayList<>();

    StringBuilder associationName = null;
    UriResourcePartTyped source = null;
    for (UriResource resourcePart : resourceParts) {
      if (resourcePart instanceof UriResourceNavigation
          || resourcePart instanceof UriResourceEntitySet) {
        if (source != null) {
          if (resourcePart instanceof UriResourceProperty)
            extendNavigationPath(associationName, ((UriResourceProperty) resourcePart).getProperty().getName());
          else
            extendNavigationPath(associationName, ((UriResourceNavigation) resourcePart).getProperty().getName());
          pathList.add(new JPANavigationPropertyInfo(sd, source, determineAssociationPath(sd, source, associationName),
              null));
        }
        source = (UriResourcePartTyped) resourcePart;
        associationName = new StringBuilder();
      } else {
        if ((resourcePart instanceof UriResourceComplexProperty
            || resourcePart instanceof UriResourceProperty && ((UriResourceProperty) resourcePart).isCollection())
            && associationName != null) {
          extendNavigationPath(associationName, ((UriResourceProperty) resourcePart).getProperty().getName());
        }
      }
    }
    if (source != null)
      pathList.add(new JPANavigationPropertyInfo(sd, source,
          determineAssociationPath(sd, source, associationName), uriInfo));
    return pathList;
  }

  private static void extendNavigationPath(final StringBuilder associationName, final String pathSegment)
      throws ODataJPAQueryException {
    if (associationName == null)
      throw new ODataJPAQueryException(ODataJPAQueryException.MessageKeys.NOT_SUPPORTED_RESOURCE_TYPE,
          HttpStatusCode.NOT_IMPLEMENTED, "");
    if (associationName.length() > 0)
      associationName.append(JPAPath.PATH_SEPARATOR);
    associationName.append(pathSegment);
  }

  public static boolean hasNavigation(final List<UriResource> uriResourceParts) {
    if (uriResourceParts != null) {
      for (int i = uriResourceParts.size() - 1; i >= 0; i--) {
        if (uriResourceParts.get(i) instanceof UriResourceNavigation)
          return true;
      }
    }
    return false;
  }

  public static JPAAssociationPath determineAssociationPath(final JPAServiceDocument sd,
      final UriResourcePartTyped naviStart, final StringBuilder associationName) throws ODataApplicationException {

    JPAEntityType naviStartType = null;
    try {
      if (naviStart instanceof UriResourceEntitySet)
        if (((UriResourceEntitySet) naviStart).getTypeFilterOnEntry() != null)
          naviStartType = sd.getEntity(((UriResourceEntitySet) naviStart).getTypeFilterOnEntry());
        else
          naviStartType = sd.getEntity(naviStart.getType());
      else if (naviStart instanceof UriResourceNavigation)
        if (((UriResourceNavigation) naviStart).getTypeFilterOnEntry() != null)
        naviStartType = sd.getEntity(((UriResourceNavigation) naviStart).getTypeFilterOnEntry());
        else
        naviStartType = sd.getEntity(((UriResourceNavigation) naviStart).getProperty().getType());
      JPAAssociationPath path = naviStartType == null ? null : naviStartType.getAssociationPath(associationName
          .toString());
      if (path == null && naviStartType != null) {
        final JPACollectionAttribute collection = naviStartType.getCollectionAttribute(associationName.toString());
        if (collection != null)
          path = collection.asAssociation();
      }
      return path;
    } catch (ODataJPAModelException e) {
      throw new ODataJPAUtilException(ODataJPAUtilException.MessageKeys.UNKNOWN_NAVI_PROPERTY,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public static List<UriParameter> determineKeyPredicates(final UriResource uriResourceItem)
      throws ODataApplicationException {

    if (uriResourceItem instanceof UriResourceEntitySet)
      return ((UriResourceEntitySet) uriResourceItem).getKeyPredicates();
    else if (uriResourceItem instanceof UriResourceNavigation)
      return ((UriResourceNavigation) uriResourceItem).getKeyPredicates();
    else
      throw new ODataJPAQueryException(ODataJPAQueryException.MessageKeys.NOT_SUPPORTED_RESOURCE_TYPE,
          HttpStatusCode.BAD_REQUEST,
          uriResourceItem.getKind().name());
  }
}