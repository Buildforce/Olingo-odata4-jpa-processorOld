package com.sap.olingo.jpa.processor.core.processor;

import org.apache.olingo.server.api.uri.UriInfo;

import com.sap.olingo.jpa.processor.core.api.JPAODataPage;
import com.sap.olingo.jpa.processor.core.exception.JPAIllegalAccessException;
import com.sap.olingo.jpa.processor.core.serializer.JPASerializer;

interface JPARequestContext {

  /**
   *

   * @param uriInfo
   * @throws JPAIllegalAccessException In case UriInfo already exists e.g. because a page was provided
   */
  void setUriInfo(final UriInfo uriInfo) throws JPAIllegalAccessException;

  void setJPASerializer(final JPASerializer serializer);

  /**
   * In case a page is provided UriInfo has to be taken from there
   * @param page
   * @throws JPAIllegalAccessException In case UriInfo already exists
   */
  void setJPAODataPage(final JPAODataPage page) throws JPAIllegalAccessException;
}