package com.sap.olingo.jpa.processor.core.database;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import org.apache.olingo.server.api.ODataApplicationException;

import com.sap.olingo.jpa.processor.core.filter.JPAAggregationOperation;
import com.sap.olingo.jpa.processor.core.filter.JPAArithmeticOperator;
import com.sap.olingo.jpa.processor.core.filter.JPABooleanOperator;
import com.sap.olingo.jpa.processor.core.filter.JPAComparisonOperator;
import com.sap.olingo.jpa.processor.core.filter.JPAMethodCall;
import com.sap.olingo.jpa.processor.core.filter.JPAUnaryBooleanOperator;

public interface JPAODataDatabaseOperations {

  void setCriterialBuilder(final CriteriaBuilder cb);

  <T extends Number> Expression<T> convert(final JPAArithmeticOperator jpaOperator)
      throws ODataApplicationException;

  Expression<Boolean> convert(final JPABooleanOperator jpaOperator) throws ODataApplicationException;

  <T extends Comparable<T>> Expression<Boolean> convert(final JPAComparisonOperator<T> jpaOperator)
      throws ODataApplicationException;

  <T> Expression<T> convert(final JPAMethodCall jpaFunction) throws ODataApplicationException;

  Expression<Boolean> convert(final JPAUnaryBooleanOperator jpaOperator) throws ODataApplicationException;

  Expression<Long> convert(final JPAAggregationOperation jpaOperator) throws ODataApplicationException;
}