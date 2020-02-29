package com.sap.olingo.jpa.metadata.core.edm.mapper.testobjects;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmFunction;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmFunction.ReturnType;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.ODataFunction;

public class ExampleJavaTwoFunctions implements ODataFunction {

  @EdmFunction(returnType = @ReturnType)
  public Integer multi(int a, int b) {
    return a * b;
  }

  @EdmFunction(returnType = @ReturnType)
  public Integer divide(int a, int b) {
    return a / b;
  }
}