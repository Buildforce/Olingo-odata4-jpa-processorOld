package com.sap.olingo.jpa.processor.core.filter;

import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.uri.queryoption.expression.ExpressionVisitor;

import javax.persistence.criteria.From;

public interface JPAExpressionVisitor extends ExpressionVisitor<JPAOperator> {

  OData getOdata();

  From<?, ?> getRoot();

}