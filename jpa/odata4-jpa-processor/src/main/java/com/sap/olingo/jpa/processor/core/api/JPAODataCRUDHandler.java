package com.sap.olingo.jpa.processor.core.api;

import com.sap.olingo.jpa.processor.core.exception.ODataJPAFilterException;

import javax.sql.DataSource;

public final class JPAODataCRUDHandler extends JPAODataGetHandler {
  /*
   * In general it is foreseen that each request gets its own CUD handler. With the introduction of the request context
   * setting the CUD handler has been mode there.
   */
  /*@Deprecated
  public JPAODataCRUDHandler(String pUnit) throws ODataJPAFilterException {
    super(pUnit);
    getJPAODataContext().setCUDRequestHandler(new JPADefaultCUDRequestHandler());
  }*/

  /*@Deprecated
  public JPAODataCRUDHandler(final String pUnit, final DataSource ds) throws ODataJPAFilterException {
    super(pUnit, ds);
    getJPAODataContext().setCUDRequestHandler(new JPADefaultCUDRequestHandler());
  }*/

  public JPAODataCRUDHandler(final JPAODataCRUDContextAccess serviceContext) {
    super(serviceContext);
  }

  /*@Override
  public JPAODataCRUDContext getJPAODataContext() {
    return (JPAODataCRUDContext) super.getJPAODataContext();
  }*/

  @Override
  public JPAODataCRUDRequestContext getJPAODataRequestContext() {
    return (JPAODataCRUDRequestContext) super.getJPAODataRequestContext();
  }

  static class JPADefaultCUDRequestHandler extends JPAAbstractCUDRequestHandler {

  }

}