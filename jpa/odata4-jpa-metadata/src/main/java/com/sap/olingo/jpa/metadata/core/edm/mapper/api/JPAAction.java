package com.sap.olingo.jpa.metadata.core.edm.mapper.api;

import com.sap.olingo.jpa.metadata.core.edm.mapper.exception.ODataJPAModelException;

import java.lang.reflect.Parameter;

public interface JPAAction extends JPAOperation, JPAJavaOperation {

  JPAParameter getParameter(Parameter declaredParameter) throws ODataJPAModelException;

}