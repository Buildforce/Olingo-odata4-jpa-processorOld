//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802

// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>

// Any modifications to this file will be lost upon recompilation of the source schema.

// Generated on: 2016.12.01 at 05:53:31 PM CET

//

package com.sap.olingo.jpa.metadata.core.edm.mapper.annotation;

/**
 * <p>Java class for TAppliesTo.
 *

 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * <xs:simpleType name="TAppliesTo">
 * <xs:list>
 * <xs:simpleType>
 * <xs:restriction base="xs:string">
 * <xs:enumeration value="Action" />
 * <xs:enumeration value="ActionImport" />
 * <xs:enumeration value="Annotation" />
 * <xs:enumeration value="Apply" />
 * <xs:enumeration value="Cast" />
 * <xs:enumeration value="ComplexType" />
 * <xs:enumeration value="EntityContainer" />
 * <xs:enumeration value="EntitySet" />
 * <xs:enumeration value="EntityType" />
 * <xs:enumeration value="EnumType" />
 * <xs:enumeration value="Function" />
 * <xs:enumeration value="FunctionImport" />
 * <xs:enumeration value="If" />
 * <xs:enumeration value="IsOf" />
 * <xs:enumeration value="LabeledElement" />
 * <xs:enumeration value="Member" />
 * <xs:enumeration value="NavigationProperty" />
 * <xs:enumeration value="Null" />
 * <xs:enumeration value="OnDelete" />
 * <xs:enumeration value="Parameter" />
 * <xs:enumeration value="Property" />
 * <xs:enumeration value="PropertyValue" />
 * <xs:enumeration value="Record" />
 * <xs:enumeration value="Reference" />
 * <xs:enumeration value="ReferentialConstraint" />
 * <xs:enumeration value="ReturnType" />
 * <xs:enumeration value="Schema" />
 * <xs:enumeration value="Singleton" />
 * <xs:enumeration value="Term" />
 * <xs:enumeration value="TypeDefinition" />
 * <xs:enumeration value="UrlRef" />
 * </xs:restriction>
 * </xs:simpleType>
 * </xs:list>
 * </xs:simpleType>
 * </pre>
 *

 */

public enum AppliesTo {

  ACTION("Action"),
  ACTION_IMPORT("ActionImport"),
  ANNOTATION("Annotation"),
  APPLY("Apply"),
  CAST("Cast"),
  COMPLEX_TYPE("ComplexType"),
  ENTITY_CONTAINER("EntityContainer"),
  ENTITY_SET("EntitySet"),
  ENTITY_TYPE("EntityType"),
  ENUM_TYPE("EnumType"),
  FUNCTION("Function"),
  FUNCTION_IMPORT("FunctionImport"),
  IF("If"),
  IF_OF("IsOf"),
  LABELED_ELEMENT("LabeledElement"),
  MEMBER("Member"),
  NAVIGATION_PROPERTY("NavigationProperty"),
  NULL("Null"),
  ON_DELETE("OnDelete"),
  PARAMETER("Parameter"),
  PROPERTY("Property"),
  PROPERTY_VALUE("PropertyValue"),
  RECORD("Record"),
  REFERENCE("Reference"),
  REFERENTIAL_CONSTRAINT("ReferentialConstraint"),
  RETURN_TYPE("ReturnType"),
  SCHEMA("Schema"),
  SINGLETON("Singleton"),
  TERM("Term"),
  TYPE_DEFINITION("TypeDefinition"),
  URL_REF("UrlRef");

  private final String value;

  private AppliesTo(String v) {
    value = v;
  }

  public String value() {
    return value;
  }
//
//  public static AppliesTo fromValue(String v) {
//    for (AppliesTo c : AppliesTo.values()) {
//      if (c.value.equals(v)) {
//        return c;
//      }
//    }
//    throw new IllegalArgumentException(v);
//  }

}