package nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.impl;

import nl.buildforce.sequoia.jpa.metadata.core.edm.annotation.EdmFunction.ReturnType;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAOperation;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAOperationResultParameter;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
// import org.apache.olingo.commons.api.edm.geo.SRID;

class IntermediateOperationResultParameter implements JPAOperationResultParameter {
  /**
   *
   */
  private final JPAOperation jpaOperation;
  private final ReturnType jpaReturnType;
  private final Class<?> type;
  private final boolean isCollection;

  public IntermediateOperationResultParameter(final JPAOperation jpaOperation, final ReturnType jpaReturnType) {
    this.jpaOperation = jpaOperation;
    this.jpaReturnType = jpaReturnType;
    this.type = jpaReturnType.type();
    this.isCollection = jpaReturnType.isCollection();
  }

  public IntermediateOperationResultParameter(final JPAOperation jpaOperation, final ReturnType jpaReturnType,
      final Class<?> returnType,
      boolean isCollection) {
    this.jpaOperation = jpaOperation;
    this.jpaReturnType = jpaReturnType;
    this.isCollection = isCollection;
    if (isCollection)
      this.type = jpaReturnType.type();
    else
      this.type = returnType;
  }

  public IntermediateOperationResultParameter(final JPAOperation jpaOperation, final ReturnType jpaReturnType,
      final Class<?> returnType) {
    this.jpaOperation = jpaOperation;
    this.jpaReturnType = jpaReturnType;
    this.isCollection = jpaReturnType.isCollection();
    this.type = returnType;
  }

  @Override
  public Class<?> getType() {
    return type;
  }

  @Override
  public Integer getMaxLength() {
    return jpaReturnType.maxLength();
  }

  @Override
  public Integer getPrecision() {
    return jpaReturnType.precision();
  }

  @Override
  public Integer getScale() {
    return jpaReturnType.scale();
  }

  @Override
  public FullQualifiedName getTypeFQN() {
    return jpaOperation.getReturnType().getTypeFQN();
  }

  @Override
  public boolean isCollection() {
    return isCollection;
  }

  /*@Override
  public SRID getSrid() {
    // TODO Auto-generated method stub
    return null;
  }*/

}