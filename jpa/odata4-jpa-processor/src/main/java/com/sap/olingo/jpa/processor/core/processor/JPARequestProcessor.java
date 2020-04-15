package com.sap.olingo.jpa.processor.core.processor;

import com.sap.olingo.jpa.metadata.core.edm.mapper.exception.ODataJPAException;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.ODataLibraryException;
import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataResponse;

public interface JPARequestProcessor {

  <K extends Comparable<K>> void retrieveData(ODataRequest request, ODataResponse response,
                                              ContentType responseFormat) throws ODataJPAException, ODataApplicationException, ODataLibraryException;
}