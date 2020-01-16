package com.sap.olingo.jpa.metadata.core.edm.mapper.api;

import java.util.List;
import java.util.Set;

import javax.persistence.AttributeConverter;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmItem;

import com.sap.olingo.jpa.metadata.core.edm.mapper.exception.ODataJPAModelException;

public interface JPAAttribute extends JPAElement {

  <X, Y extends Object> AttributeConverter<X, Y> getConverter();

  EdmPrimitiveTypeKind getEdmType() throws ODataJPAModelException;

  CsdlAbstractEdmItem getProperty() throws ODataJPAModelException;

  JPAStructuredType getStructuredType() throws ODataJPAModelException;

  /**
   * Returns a list of names of the claims that shall be matched with this property
   * @return
   */
  Set<String> getProtectionClaimNames();

  /**
   * Provides a List of path to the protected attributed
   * @return
   * @throws ODataJPAModelException
   */
  List<String> getProtectionPath(String claimName) throws ODataJPAModelException;

  Class<?> getType();

  boolean isAssociation();

  /**
   * True is a to n association is involved
   * @return
   */
  boolean isCollection();

  boolean isComplex();

  boolean isEnum();

  boolean isEtag();

  boolean isKey();

  boolean isSearchable();

  boolean hasProtection();

}