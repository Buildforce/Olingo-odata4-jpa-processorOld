package com.sap.olingo.jpa.metadata.core.edm.mapper.testobjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.olingo.commons.api.edm.geo.Geospatial.Dimension;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmAction;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmFunction.ReturnType;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmGeospatial;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmParameter;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.ODataAction;
import com.sap.olingo.jpa.processor.core.testmodel.AbcClassification;
import com.sap.olingo.jpa.processor.core.testmodel.AccessRights;
import com.sap.olingo.jpa.processor.core.testmodel.BusinessPartnerRole;
import com.sap.olingo.jpa.processor.core.testmodel.ChangeInformation;
import com.sap.olingo.jpa.processor.core.testmodel.Person;
import com.sap.olingo.jpa.processor.core.testmodel.PostalAddressData;

public class ExampleJavaActions implements ODataAction {

  @EdmAction()
  public Integer unboundWithImport(
      @EdmParameter(name = "A") short a, @EdmParameter(name = "B") int b) {
    return a + b;
  }

  @EdmAction(name = "BoundNoImport", isBound = true)
  public void boundNoImport(
      @EdmParameter(name = "Person") Person person,
      @EdmParameter(name = "A", precision = 34, scale = 10) BigDecimal a) {
    // Do nothing
  }

  @EdmAction(returnType = @ReturnType(isNullable = false, precision = 20, scale = 5))
  public BigDecimal unboundReturnFacet(
      @EdmParameter(name = "A") short a, @EdmParameter(name = "B") int b) {
    return new BigDecimal(a).add(new BigDecimal(b));
  }

  @EdmAction(isBound = true, entitySetPath = "Person/Roles")
  public BusinessPartnerRole boundWithEntitySetPath(
      @EdmParameter(name = "Person") Person person) {
    return null;
  }

  @EdmAction()
  public ChangeInformation returnEmbeddable() {
    return new ChangeInformation();
  }

  @EdmAction()
  public Person returnEntity() {
    return new Person();
  }

  @EdmAction
  public AbcClassification returnEnumeration(@EdmParameter(name = "Rights") AccessRights rights) {
    return AbcClassification.B;
  }

  @EdmAction(returnType = @ReturnType(type = String.class))
  public List<String> returnCollection() {
    return new ArrayList<>();
  }

  @EdmAction(returnType = @ReturnType(type = ChangeInformation.class))
  public List<ChangeInformation> returnEmbeddableCollection() {
    return Arrays.asList(new ChangeInformation());
  }

  @EdmAction()
  public List<String> returnCollectionWithoutReturnType() {
    return new ArrayList<>();
  }

  @EdmAction(
          returnType = @ReturnType(maxLength = 60, srid = @EdmGeospatial(dimension = Dimension.GEOGRAPHY, srid = "4326")))
  public String calculateLocation(
      @EdmParameter(name = "String", maxLength = 100, srid = @EdmGeospatial(dimension = Dimension.GEOGRAPHY, srid = "4326")) String a) {
    return "";
  }

  @EdmAction()
  public Integer errorNonPrimitiveParameter(
      @EdmParameter(name = "A") PostalAddressData a) {
    return 1;
  }

  @EdmAction(isBound = true)
  public void boundWithOutBindingParameter(
      @EdmParameter(name = "A", precision = 34, scale = 10) BigDecimal a) {
    // Do nothing
  }

  @EdmAction(isBound = true)
  public void boundWithOutParameter() {
    // Do nothing
  }

  @EdmAction(isBound = true)
  public void boundBindingParameterSecondParameter(
      @EdmParameter(name = "A", precision = 34, scale = 10) BigDecimal a,
      @EdmParameter(name = "Person") Person person) {
    // Do nothing
  }

  @EdmAction(entitySetPath = "Person/Address")
  public PostalAddressData errorUnboundWithEntitySetPath(
      @EdmParameter(name = "Person") Person person) {
    return null;
  }

  @EdmAction(isBound = true, entitySetPath = "Person/Address")
  public Integer errorPrimitiveTypeWithEntitySetPath(
      @EdmParameter(name = "Person") Person person) {
    return null;
  }
}