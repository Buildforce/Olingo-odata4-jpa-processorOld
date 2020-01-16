package com.sap.olingo.jpa.metadata.core.edm.mapper.api;

import java.util.List;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmFunctionType;
import com.sap.olingo.jpa.metadata.core.edm.mapper.exception.ODataJPAModelException;

public interface JPAFunction extends JPAOperation {

  /**
   *

   * @return List of import parameter
   * @throws ODataJPAModelException
   */
  List<JPAParameter> getParameter() throws ODataJPAModelException;

  /**
   *

   * @param internalName
   * @return
   * @throws ODataJPAModelException
   */
  JPAParameter getParameter(String internalName) throws ODataJPAModelException;

  /**
   *

   * @return The type of function
   */
  EdmFunctionType getFunctionType();

  boolean isBound() throws ODataJPAModelException;
}