package com.sap.olingo.jpa.processor.core.query;

import java.util.List;

import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.server.api.uri.UriParameter;

public interface EdmEntitySetInfo {

  EdmEntitySet getEdmEntitySet();

  List<UriParameter> getKeyPredicates();

  String getName();

  String getNavigationPath();

  EdmEntitySet getTargetEdmEntitySet();
}