package com.sap.olingo.jpa.processor.core.serializer;

import com.sap.olingo.jpa.processor.core.exception.ODataJPASerializerException;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.serializer.SerializerResult;

public interface JPASerializer {

  SerializerResult serialize(final ODataRequest request, final EntityCollection result)
      throws SerializerException, ODataJPASerializerException;

  ContentType getContentType();
}