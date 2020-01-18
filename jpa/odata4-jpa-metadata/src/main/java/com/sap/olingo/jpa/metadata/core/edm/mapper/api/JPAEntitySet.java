package com.sap.olingo.jpa.metadata.core.edm.mapper.api;

public interface JPAEntitySet extends JPAElement {

  JPAEntityType getODataEntityType();

  JPAEntityType getEntityType();

}