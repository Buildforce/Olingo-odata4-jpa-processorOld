package com.sap.olingo.jpa.processor.core.filter;

import javax.persistence.criteria.Expression;

import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.queryoption.expression.UnaryOperatorKind;

public interface JPAUnaryBooleanOperator extends JPAExpressionOperator {

  @Override
  Expression<Boolean> get() throws ODataApplicationException;

  Expression<Boolean> getLeft() throws ODataApplicationException;

  @SuppressWarnings("unchecked")
  @Override
  UnaryOperatorKind getOperator();

}