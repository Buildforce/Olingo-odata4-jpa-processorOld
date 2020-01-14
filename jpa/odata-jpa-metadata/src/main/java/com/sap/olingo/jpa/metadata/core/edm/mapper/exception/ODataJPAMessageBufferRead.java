package com.sap.olingo.jpa.metadata.core.edm.mapper.exception;

public interface ODataJPAMessageBufferRead {

  String getText(Object exception, String ID);

  String getText(Object exception, String ID, String... parameters);

}