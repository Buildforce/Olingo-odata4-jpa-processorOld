package com.sap.olingo.jpa.processor.core.filter;

import org.apache.olingo.server.api.ODataApplicationException;

import jakarta.persistence.criteria.Expression;

public interface JPAExpression extends JPAOperator {

  @Override
  Expression<Boolean> get() throws ODataApplicationException;

}