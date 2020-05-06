package com.sap.olingo.jpa.processor.core.filter;

public interface JPAExpressionOperator extends JPAExpression {

  <E extends Enum<E>> Enum<E> getOperator();

}