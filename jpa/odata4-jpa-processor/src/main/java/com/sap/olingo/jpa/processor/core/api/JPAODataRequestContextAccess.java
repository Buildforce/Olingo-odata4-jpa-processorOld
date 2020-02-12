package com.sap.olingo.jpa.processor.core.api;

import com.sap.olingo.jpa.processor.core.serializer.JPASerializer;
import org.apache.olingo.server.api.uri.UriInfoResource;

import javax.persistence.EntityManager;
import java.util.Optional;

public interface JPAODataRequestContextAccess {

  EntityManager getEntityManager();

  UriInfoResource getUriInfo();

  JPASerializer getSerializer();

  JPAODataPage getPage();

  Optional<JPAODataClaimProvider> getClaimsProvider();

  Optional<JPAODataGroupProvider> getGroupsProvider();

  JPACUDRequestHandler getCUDRequestHandler();

  JPAServiceDebugger getDebugger();

  JPAODataTransactionFactory getTransactionFactory();

}