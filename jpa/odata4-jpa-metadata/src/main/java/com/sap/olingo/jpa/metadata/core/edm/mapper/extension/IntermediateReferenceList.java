package com.sap.olingo.jpa.metadata.core.edm.mapper.extension;

import com.sap.olingo.jpa.metadata.core.edm.mapper.exception.ODataJPAModelException;

import java.net.URI;

public interface IntermediateReferenceList {
  IntermediateReferenceAccess addReference(final String uri) throws ODataJPAModelException;

  IntermediateReferenceAccess addReference(final String uri, final String path) throws ODataJPAModelException;

  interface IntermediateReferenceAccess {
    URI getURI();

    String getPath();

    void addInclude(final String namespace, final String alias);

    void addInclude(final String namespace);
  }
}