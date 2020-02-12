package com.sap.olingo.jpa.processor.core.converter;

import com.sap.olingo.jpa.metadata.core.edm.mapper.api.JPAAssociationPath;
import com.sap.olingo.jpa.metadata.core.edm.mapper.api.JPAEntityType;
import org.apache.olingo.server.api.ODataApplicationException;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;

public interface JPAExpandResult { // NOSONAR

  String ROOT_RESULT_KEY = "root";

  JPAExpandResult getChild(final JPAAssociationPath associationPath);

  Map<JPAAssociationPath, JPAExpandResult> getChildren();

  Long getCount(final String string);

  JPAEntityType getEntityType();

  List<Tuple> getResult(final String key);

  Map<String, List<Tuple>> getResults();

  boolean hasCount();

  void convert(final JPATupleChildConverter converter) throws ODataApplicationException;

}