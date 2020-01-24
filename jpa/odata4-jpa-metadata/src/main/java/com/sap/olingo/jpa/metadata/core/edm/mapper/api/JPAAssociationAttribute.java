package com.sap.olingo.jpa.metadata.core.edm.mapper.api;

import com.sap.olingo.jpa.metadata.core.edm.mapper.exception.ODataJPAModelException;

//TODO remove extension
public interface JPAAssociationAttribute extends JPAAttribute {

  JPAStructuredType getTargetEntity() throws ODataJPAModelException;

  JPAAssociationAttribute getPartner();

  JPAAssociationPath getPath() throws ODataJPAModelException;

}