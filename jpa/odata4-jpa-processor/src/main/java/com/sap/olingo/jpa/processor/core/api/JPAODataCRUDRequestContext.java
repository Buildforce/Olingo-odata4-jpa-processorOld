package com.sap.olingo.jpa.processor.core.api;

public interface JPAODataCRUDRequestContext extends JPAODataRequestContext {
  void setCUDRequestHandler(final JPACUDRequestHandler jpaCUDRequestHandler);
}