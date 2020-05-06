package com.sap.olingo.jpa.processor.core.converter;

import com.sap.olingo.jpa.metadata.core.edm.mapper.api.JPAAssociationPath;

import java.util.Collection;

public interface JPACollectionResult extends JPAExpandResult {

  /**
   * Returns the result of a query for a collection property, which is either a list of attributes e.g. Integer for
   * primitive properties or a list of ComplexValues
   * @param key
   * @return
   */
  Collection<Object> getPropertyCollection(final String key);

  JPAAssociationPath getAssociation();

}