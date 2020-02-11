package com.sap.olingo.jpa.processor.core.api;

import javax.persistence.EntityManager;

import org.apache.olingo.server.api.debug.DebugSupport;

public interface JPAODataRequestContext {

  void setClaimsProvider(final JPAODataClaimProvider provider);

  void setGroupsProvider(final JPAODataGroupProvider provider);

  void setEntityManager(final EntityManager em);

  void setDebugSupport(final DebugSupport debugSupport);

  void setTransactionFactory(final JPAODataTransactionFactory transactionFactory);

}