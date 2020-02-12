package com.sap.olingo.jpa.processor.core.query;

import com.sap.olingo.jpa.metadata.core.edm.mapper.api.JPAEntityType;
import org.apache.olingo.server.api.uri.UriInfoResource;

public interface JPAExpandItem extends UriInfoResource {

  JPAEntityType getEntityType();

}