package com.sap.olingo.jpa.processor.core.api;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.apache.olingo.server.api.uri.UriInfoResource;

import com.sap.olingo.jpa.processor.core.serializer.JPASerializer;

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