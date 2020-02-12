package com.sap.olingo.jpa.processor.core.filter;

import org.apache.olingo.server.api.ODataApplicationException;

import javax.persistence.criteria.Expression;

public interface JPABooleanOperator extends JPAExpressionOperator {

  Expression<Boolean> getLeft() throws ODataApplicationException;

  Expression<Boolean> getRight() throws ODataApplicationException;

}