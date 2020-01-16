package com.sap.olingo.jpa.processor.core.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;

import org.apache.olingo.server.api.debug.DebugSupport;

public interface JPAODataRequestContext {

  void setClaimsProvider(@Nullable final JPAODataClaimProvider provider);

  void setGroupsProvider(@Nullable final JPAODataGroupProvider provider);

  void setEntityManager(@Nonnull final EntityManager em);

  void setDebugSupport(@Nullable final DebugSupport debugSupport);

  void setTransactionFactory(@Nullable final JPAODataTransactionFactory transactionFactory);

}