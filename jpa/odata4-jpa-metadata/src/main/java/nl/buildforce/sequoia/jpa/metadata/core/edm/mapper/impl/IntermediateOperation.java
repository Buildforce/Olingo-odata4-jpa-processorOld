package nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.impl;

import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAEdmNameBuilder;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.exception.ODataJPAModelException;

abstract class IntermediateOperation extends IntermediateModelElement {

  IntermediateOperation(JPAEdmNameBuilder nameBuilder, String internalName) {
    super(nameBuilder, internalName);
  }

  abstract boolean hasImport();

  abstract boolean isBound() throws ODataJPAModelException;

}