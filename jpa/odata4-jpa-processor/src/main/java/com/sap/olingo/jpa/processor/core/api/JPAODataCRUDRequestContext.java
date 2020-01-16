package com.sap.olingo.jpa.processor.core.api;

import javax.annotation.Nonnull;

public interface JPAODataCRUDRequestContext extends JPAODataRequestContext {
  void setCUDRequestHandler(@Nonnull final JPACUDRequestHandler jpaCUDRequestHandler);
}