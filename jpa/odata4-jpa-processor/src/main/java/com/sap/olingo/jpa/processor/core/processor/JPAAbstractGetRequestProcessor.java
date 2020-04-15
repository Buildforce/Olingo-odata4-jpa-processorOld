package com.sap.olingo.jpa.processor.core.processor;

import com.sap.olingo.jpa.metadata.core.edm.mapper.exception.ODataJPAException;
import com.sap.olingo.jpa.processor.core.api.JPAODataCRUDContextAccess;
import com.sap.olingo.jpa.processor.core.api.JPAODataRequestContextAccess;
import org.apache.olingo.server.api.OData;

abstract class JPAAbstractGetRequestProcessor extends JPAAbstractRequestProcessor implements JPARequestProcessor {

  JPAAbstractGetRequestProcessor(OData odata, JPAODataCRUDContextAccess context,
      JPAODataRequestContextAccess requestContext) throws ODataJPAException {
    super(odata, context, requestContext);
  }
}