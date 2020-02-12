package com.sap.olingo.jpa.processor.core.database;

import com.sap.olingo.jpa.metadata.core.edm.mapper.api.JPADataBaseFunction;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriResource;

import javax.persistence.EntityManager;
import java.util.List;

public interface JPAODataDatabaseTableFunction {

  <T> List<T> executeFunctionQuery(final List<UriResource> uriResourceParts, final JPADataBaseFunction jpaFunction,
      final EntityManager em) throws ODataApplicationException;
}