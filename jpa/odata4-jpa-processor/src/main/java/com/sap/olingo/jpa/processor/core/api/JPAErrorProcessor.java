package com.sap.olingo.jpa.processor.core.api;

import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataServerError;

public interface JPAErrorProcessor {
  void processError(final ODataRequest request, final ODataServerError serverError);
}