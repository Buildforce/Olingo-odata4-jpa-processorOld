package nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.impl;

import nl.buildforce.sequoia.jpa.metadata.api.JPAEdmMetadataPostProcessor;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAServiceDocument;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.exception.ODataJPAModelException;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.extension.IntermediateEntityTypeAccess;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.extension.IntermediateNavigationPropertyAccess;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.extension.IntermediatePropertyAccess;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.extension.IntermediateReferenceList;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.extension.IntermediateReferenceList.IntermediateReferenceAccess;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.edmx.EdmxReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestIntermediateReferences extends TestMappingRoot {
  private IntermediateReferences cut;

  @BeforeEach
  public void setup() {
    cut = new IntermediateReferences();
  }

  @Test
  public void checkAddOnlyURI() throws ODataJPAModelException {
    String uri = "http://docs.oasisopen.org/odata/odata/v4.0/os/vocabularies/Org.OData.Core.V1.xml";
    cut.addReference(uri);
    List<EdmxReference> act = cut.getEdmReferences();
    assertEquals(1, act.size());
    assertEquals(act.get(0).getUri().toString(), uri);
  }

  @Test
  public void checkAddURIandPath() throws ODataJPAModelException {
    String uri = "http://docs.oasisopen.org/odata/odata/v4.0/os/vocabularies/Org.OData.Measures.V1.xml";
    cut.addReference(uri, "annotations/Org.OData.Measures.V1.xml");
    List<EdmxReference> act = cut.getEdmReferences();
    assertEquals(1, act.size());
    assertEquals(uri, act.get(0).getUri().toString());
  }

  @Test
  public void checkConvertedToEdmx() throws ODataJPAModelException {
    JPAServiceDocument serviceDocument;
    serviceDocument = new IntermediateServiceDocument(PUNIT_NAME, emf.getMetamodel(), new PostProcessor(), null);
    assertEquals(1, serviceDocument.getReferences().size());

    EdmxReference ref = serviceDocument.getReferences().get(0);
    assertEquals(1, ref.getIncludes().size());
  }

  @Test
  public void checkGetOneSchema() throws ODataJPAModelException {
    String uri = "http://docs.oasisopen.org/odata/odata/v4.0/os/vocabularies/Org.OData.Measures.V1.xml";
    IntermediateReferenceAccess ref = cut.addReference(uri, "annotations/Org.OData.Measures.V1.xml");
    ref.addInclude("Org.OData.Measures.V1", "");

    assertEquals(1, cut.getSchemas().size());
  }

  @Test
  public void checkGetTwoSchemas() throws ODataJPAModelException {
    String uri = "http://org.example/odata/odata/v4.0/os/vocabularies/Org.Olingo.Test.V1.xml";
    IntermediateReferenceAccess ref = cut.addReference(uri, "annotations/Org.Olingo.Test.V1.xml");
    ref.addInclude("Org.Olingo.Test.V1.xml", "");

    assertEquals(2, cut.getSchemas().size());
  }

  @Test
  public void checkGetComplexType() throws ODataJPAModelException {
    String uri = "http://org.example/odata/odata/v4.0/os/vocabularies/Org.Olingo.Test.V1.xml";
    IntermediateReferenceAccess ref = cut.addReference(uri, "annotations/Org.Olingo.Test.V1.xml");
    ref.addInclude("Org.Olingo.Test.V1.xml", "");

    for (CsdlSchema schema : cut.getSchemas()) {
      if (schema.getNamespace().equals("Org.OData.Capabilities.V1")) {
        assertNotNull(schema.getComplexType("UpdateRestrictionsType"));
        return;
      }
    }
    fail();
  }

  @Test
  public void checkGetTermByNamespace() throws ODataJPAModelException {
    String uri = "http://docs.oasisopen.org/odata/odata/v4.0/os/vocabularies/Org.OData.Measures.V1.xml";
    IntermediateReferenceAccess ref = cut.addReference(uri, "annotations/Org.OData.Measures.V1.xml");
    ref.addInclude("Org.OData.Measures.V1", "");
    FullQualifiedName fqn = new FullQualifiedName("Org.OData.Measures.V1", "ISOCurrency");
    assertNotNull(cut.getTerm(fqn));
  }

  @Test
  public void checkGetTermByAlias() throws ODataJPAModelException {
    String uri = "http://docs.oasisopen.org/odata/odata/v4.0/os/vocabularies/Org.OData.Measures.V1.xml";
    IntermediateReferenceAccess ref = cut.addReference(uri, "annotations/Org.OData.Measures.V1.xml");
    ref.addInclude("Org.OData.Measures.V1", "Measures");
    FullQualifiedName fqn = new FullQualifiedName("Measures", "ISOCurrency");
    assertNotNull(cut.getTerm(fqn));
  }

  @Test
  public void checkReturnNullOnUnknowTerm() throws ODataJPAModelException {
    String uri = "http://docs.oasisopen.org/odata/odata/v4.0/os/vocabularies/Org.OData.Measures.V1.xml";
    IntermediateReferenceAccess ref = cut.addReference(uri, "annotations/Org.OData.Measures.V1.xml");
    ref.addInclude("Org.OData.Measures.V1", "Measures");
    FullQualifiedName fqn = new FullQualifiedName("Measures", "Dummy");
    assertNull(cut.getTerm(fqn));
  }

  @Test
  public void checkReturnNullOnUnknowNamespace() throws ODataJPAModelException {
    String uri = "http://docs.oasisopen.org/odata/odata/v4.0/os/vocabularies/Org.OData.Measures.V1.xml";
    IntermediateReferenceAccess ref = cut.addReference(uri, "annotations/Org.OData.Measures.V1.xml");
    ref.addInclude("Org.OData.Measures.V1", "Measures");
    FullQualifiedName fqn = new FullQualifiedName("Dummy", "ISOCurrency");
    assertNull(cut.getTerm(fqn));
  }

  static class PostProcessor extends JPAEdmMetadataPostProcessor {
    @Override
    public void processNavigationProperty(final IntermediateNavigationPropertyAccess property,
        final String jpaManagedTypeClassName) {

    }

    @Override
    public void processProperty(final IntermediatePropertyAccess property, final String jpaManagedTypeClassName) {

    }

    @Override
    public void processEntityType(IntermediateEntityTypeAccess entity) {}

    @Override
    public void provideReferences(final IntermediateReferenceList references) throws ODataJPAModelException {
      String uri = "http://docs.oasisopen.org/odata/odata/v4.0/os/vocabularies/Org.OData.Measures.V1.xml";
      IntermediateReferenceAccess reference = references.addReference(uri, "annotations/Org.OData.Measures.V1.xml");
      reference.addInclude("Org.OData.Core.V1", "Core");
    }
  }
}