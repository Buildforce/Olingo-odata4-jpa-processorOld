package com.sap.olingo.jpa.processor.core.processor;

import java.util.Map;

import com.sap.olingo.jpa.metadata.core.edm.mapper.api.JPAEntityType;
import com.sap.olingo.jpa.processor.core.exception.ODataJPAProcessorException;

public interface JPARequestLink {
  /**
   * Provides an instance of the target entity metadata
   * @return
   */
  JPAEntityType getEntityType();

  /**
   * Map of related keys
   * @return
   * @throws ODataJPAProcessorException
   */
  Map<String, Object> getRelatedKeys() throws ODataJPAProcessorException;

  Map<String, Object> getValues() throws ODataJPAProcessorException;
}