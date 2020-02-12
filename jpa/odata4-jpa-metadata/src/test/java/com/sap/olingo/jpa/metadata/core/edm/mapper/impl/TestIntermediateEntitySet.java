package com.sap.olingo.jpa.metadata.core.edm.mapper.impl;

import com.sap.olingo.jpa.metadata.api.JPAEdmMetadataPostProcessor;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmEnumeration;
import com.sap.olingo.jpa.metadata.core.edm.mapper.api.JPAEntityType;
import com.sap.olingo.jpa.metadata.core.edm.mapper.exception.ODataJPAModelException;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.*;
import com.sap.olingo.jpa.metadata.core.edm.mapper.exception.ODataJPAModelException;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.IntermediateEntitySetAccess;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.IntermediateEntityTypeAccess;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.IntermediateNavigationPropertyAccess;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.IntermediatePropertyAccess;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.IntermediateReferenceList;
import com.sap.olingo.jpa.processor.core.testmodel.AbcClassification;
import org.apache.olingo.commons.api.edm.provider.CsdlAnnotation;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression.ConstantExpressionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import javax.persistence.metamodel.EntityType;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestIntermediateEntitySet extends TestMappingRoot {
  private IntermediateSchema schema;
  private Set<EntityType<?>> etList;
  private JPADefaultEdmNameBuilder nameBuilder;

  @BeforeEach
  public void setup() throws ODataJPAModelException {
    IntermediateModelElement.setPostProcessor(new DefaultEdmPostProcessor());
    final Reflections r = mock(Reflections.class);
    when(r.getTypesAnnotatedWith(EdmEnumeration.class)).thenReturn(new HashSet<>(Collections.singletonList(AbcClassification.class)));

    etList = emf.getMetamodel().getEntities();
    nameBuilder = new JPADefaultEdmNameBuilder(PUNIT_NAME);
    schema = new IntermediateSchema(nameBuilder, emf.getMetamodel(), r);
  }

  @Test
  public void checkAnnotationSet() throws ODataJPAModelException {
    IntermediateModelElement.setPostProcessor(new PostProcessor());
    IntermediateEntityType et = new IntermediateEntityType(nameBuilder, getEntityType(
        "AdministrativeDivisionDescription"), schema);
    IntermediateEntitySet set = new IntermediateEntitySet(nameBuilder, et);
    List<CsdlAnnotation> act = set.getEdmItem().getAnnotations();
    assertEquals(1, act.size());
    assertEquals("Capabilities.TopSupported", act.get(0).getTerm());
  }

  @Test
  public void checkODataEntityTypeDiffers() throws ODataJPAModelException {
    IntermediateEntityType et = new IntermediateEntityType(new JPADefaultEdmNameBuilder(PUNIT_NAME), getEntityType(
        "BestOrganization"), schema);
    IntermediateEntitySet set = new IntermediateEntitySet(nameBuilder, et);

    JPAEntityType odataEt = set.getODataEntityType();
    assertEquals("BusinessPartner", odataEt.getExternalName());
  }

  @Test
  public void checkODataEntityTypeSame() throws ODataJPAModelException {
    IntermediateEntityType et = new IntermediateEntityType(new JPADefaultEdmNameBuilder(PUNIT_NAME), getEntityType(
        "Organization"), schema);
    IntermediateEntitySet set = new IntermediateEntitySet(nameBuilder, et);

    JPAEntityType odataEt = set.getODataEntityType();
    assertEquals("Organization", odataEt.getExternalName());
  }

  @Test
  public void checkEdmItemContainsODataEntityType() throws ODataJPAModelException {
    final IntermediateEntityType et = new IntermediateEntityType(new JPADefaultEdmNameBuilder(PUNIT_NAME),
        getEntityType("BestOrganization"), schema);
    final IntermediateEntitySet set = new IntermediateEntitySet(nameBuilder, et);
    final CsdlEntitySet act = set.getEdmItem();
    assertEquals(et.buildFQN("BusinessPartner").getFullQualifiedNameAsString(), act.getType());
  }

  @Test
  public void checkPostProcessorExternalNameChanged() throws ODataJPAModelException {
    IntermediateModelElement.setPostProcessor(new PostProcessor());
    IntermediateEntityType et = new IntermediateEntityType(nameBuilder, getEntityType("BusinessPartner"), schema);
    IntermediateEntitySet set = new IntermediateEntitySet(nameBuilder, et);
    set.getEdmItem(); // Trigger build of EdmEntitySet

    assertEquals("BusinessPartnerList", set.getExternalName(), "Wrong name");
  }

  private static class PostProcessor extends JPAEdmMetadataPostProcessor {

    @Override
    public void processProperty(final IntermediatePropertyAccess property, String jpaManagedTypeClassName) {
      if (jpaManagedTypeClassName.equals(
          "com.sap.olingo.jpa.processor.core.testmodel.BusinessPartner")) {
        if (property.getInternalName().equals("communicationData")) {
          property.setIgnore(true);
        }
      }
    }

    @Override
    public void processNavigationProperty(final IntermediateNavigationPropertyAccess property,
        String jpaManagedTypeClassName) {}

    @Override
    public void processEntityType(final IntermediateEntityTypeAccess entity) {}

    @Override
    public void provideReferences(final IntermediateReferenceList references) {}

    @Override
    public void processEntitySet(final IntermediateEntitySetAccess entitySet) {

      CsdlConstantExpression mimeType = new CsdlConstantExpression(ConstantExpressionType.Bool, "false");
      CsdlAnnotation annotation = new CsdlAnnotation();
      annotation.setExpression(mimeType);
      annotation.setTerm("Capabilities.TopSupported");
      List<CsdlAnnotation> annotations = new ArrayList<>();
      annotations.add(annotation);
      entitySet.addAnnotations(annotations);

      if ("BusinessPartners".equals(entitySet.getExternalName())) {
        entitySet.setExternalName("BusinessPartnerList");
      }
    }
  }

  private EntityType<?> getEntityType(final String typeName) {
    for (EntityType<?> entityType : etList) {
      if (entityType.getJavaType().getSimpleName().equals(typeName)) {
        return entityType;
      }
    }
    return null;
  }
}