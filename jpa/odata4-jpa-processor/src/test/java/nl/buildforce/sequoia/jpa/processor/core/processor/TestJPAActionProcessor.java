package nl.buildforce.sequoia.jpa.processor.core.processor;

import nl.buildforce.sequoia.jpa.metadata.api.JPAEdmProvider;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAAction;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAAttribute;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAEntityType;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAOperationResultParameter;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAParameter;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAPath;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAServiceDocument;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.api.JPAStructuredType;
import nl.buildforce.sequoia.jpa.metadata.core.edm.mapper.exception.ODataJPAModelException;
import nl.buildforce.sequoia.jpa.processor.core.api.JPAODataCRUDContextAccess;
import nl.buildforce.sequoia.jpa.processor.core.api.JPAODataRequestContextAccess;
import nl.buildforce.sequoia.jpa.processor.core.serializer.JPAOperationSerializer;
import nl.buildforce.sequoia.jpa.processor.core.testmodel.AdministrativeDivision;
import nl.buildforce.sequoia.jpa.processor.core.testmodel.CommunicationData;
import nl.buildforce.sequoia.jpa.processor.core.testobjects.FileAccess;
import nl.buildforce.sequoia.jpa.processor.core.testobjects.TestJavaActionNoParameter;
import nl.buildforce.sequoia.jpa.processor.core.testobjects.TestJavaActions;
import org.apache.olingo.commons.api.data.Annotatable;
import org.apache.olingo.commons.api.data.Parameter;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmAction;
import org.apache.olingo.commons.api.edm.EdmComplexType;
import org.apache.olingo.commons.api.edm.EdmPrimitiveType;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeException;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.EdmReturnType;
import org.apache.olingo.commons.api.edm.EdmType;
import org.apache.olingo.commons.api.edm.constants.EdmTypeKind;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlReturnType;
import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataResponse;
import org.apache.olingo.server.api.deserializer.DeserializerResult;
import org.apache.olingo.server.api.deserializer.ODataDeserializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceAction;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestJPAActionProcessor {

  private JPAActionRequestProcessor cut;
  private ContentType requestFormat;
  @Mock
  private ODataRequest request;
  @Mock
  private ODataResponse response;
  @Mock
  private OData odata;
  @Mock
  private ODataDeserializer deserializer;
  @Mock
  private JPAOperationSerializer serializer;
  @Mock
  private JPAODataCRUDContextAccess sessionContext;
  @Mock
  private JPAODataRequestContextAccess requestContext;
  @Mock
  private JPAServiceDocument sd;
  @Mock
  private UriInfo uriInfo;
  private List<UriResource> uriResources;
  @Mock
  private UriResourceAction resource;
  @Mock
  private EdmAction edmAction;
  @Mock
  private JPAAction action;
  private Map<String, Parameter> actionParameter;
  @Mock
  private CsdlReturnType returnType;
  @Mock
  private UriResourceEntitySet bindingEntity;

  @BeforeEach
  public void setup() throws ODataException {
    MockitoAnnotations.initMocks(this);

    uriResources = new ArrayList<>();
    uriResources.add(resource);
    actionParameter = new HashMap<>();

    EntityManager em = mock(EntityManager.class);
    CriteriaBuilder cb = mock(CriteriaBuilder.class);
    JPAEdmProvider edmProvider = mock(JPAEdmProvider.class);
    DeserializerResult dResult = mock(DeserializerResult.class);
    SerializerResult serializerResult = mock(SerializerResult.class);

    when(requestContext.getEntityManager()).thenReturn(em);
    when(em.getCriteriaBuilder()).thenReturn(cb);
    when(sessionContext.getEdmProvider()).thenReturn(edmProvider);
    when(edmProvider.getServiceDocument()).thenReturn(sd);
    when(requestContext.getUriInfo()).thenReturn(uriInfo);
    when(requestContext.getSerializer()).thenReturn(serializer);
    when(serializer.serialize(any(Annotatable.class), any(EdmType.class), any(ODataRequest.class)))
        .thenReturn(serializerResult);
    when(serializer.getContentType()).thenReturn(ContentType.APPLICATION_JSON);

    when(uriInfo.getUriResourceParts()).thenReturn(uriResources);
    when(resource.getAction()).thenReturn(edmAction);
    when(edmAction.isBound()).thenReturn(Boolean.FALSE);
    when(sd.getAction(edmAction)).thenReturn(action);
    when(odata.createDeserializer((ContentType) any())).thenReturn(deserializer);

    when(deserializer.actionParameters(request.getBody(), resource.getAction())).thenReturn(dResult);
    when(dResult.getActionParameters()).thenReturn(actionParameter);

    requestFormat = ContentType.APPLICATION_JSON;

    cut = new JPAActionRequestProcessor(odata, sessionContext, requestContext);
  }

  @Test
  public void testCallsConstructorWithoutParameter() throws
          IllegalArgumentException, NoSuchMethodException,
      SecurityException, ODataApplicationException {
    TestJavaActionNoParameter.resetCalls();

    setConstructorAndMethod("unboundReturnPrimitiveNoParameter");

    cut.performAction(request, response, requestFormat);

    assertEquals(1, TestJavaActionNoParameter.constructorCalls);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testCallsConstructorWithParameter() throws
          IllegalArgumentException, NoSuchMethodException,
      SecurityException, ODataApplicationException {
    TestJavaActions.constructorCalls = 0;

    @SuppressWarnings("rawtypes")
    Constructor c = TestJavaActions.class.getConstructors()[0];
    Method m = TestJavaActions.class.getMethod("unboundWithOutParameter");
    when(action.getConstructor()).thenReturn(c);
    when(action.getMethod()).thenReturn(m);
    when(action.getReturnType()).thenReturn(null);
    cut.performAction(request, response, requestFormat);

    assertEquals(1, TestJavaActions.constructorCalls);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testCallsActionVoidNoParameterReturnNoContent() throws NoSuchMethodException,
      SecurityException, ODataApplicationException {

    @SuppressWarnings("rawtypes")
    Constructor c = TestJavaActions.class.getConstructors()[0];
    Method m = TestJavaActions.class.getMethod("unboundWithOutParameter");
    when(action.getConstructor()).thenReturn(c);
    when(action.getMethod()).thenReturn(m);
    when(action.getReturnType()).thenReturn(null);

    cut.performAction(request, response, requestFormat);
    verify(response, times(1)).setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testCallsActionPrimitiveNoParameterReturnValue() throws NoSuchMethodException,
      SecurityException, SerializerException, ODataApplicationException {

    @SuppressWarnings("rawtypes")
    Constructor c = TestJavaActions.class.getConstructors()[0];
    Method m = TestJavaActions.class.getMethod("unboundReturnFacetNoParameter");
    when(action.getConstructor()).thenReturn(c);
    when(action.getMethod()).thenReturn(m);
    final JPAOperationResultParameter rParam = mock(JPAOperationResultParameter.class);
    when(action.getResultParameter()).thenReturn(rParam);

    EdmReturnType rt = mock(EdmReturnType.class);
    EdmType type = mock(EdmType.class);
    when(edmAction.getReturnType()).thenReturn(rt);
    when(rt.getType()).thenReturn(type);
    when(type.getKind()).thenReturn(EdmTypeKind.PRIMITIVE);

    cut.performAction(request, response, requestFormat);
    verify(response, times(1)).setStatusCode(200);
    verify(serializer, times(1)).serialize(any(Annotatable.class), eq(type), any(ODataRequest.class));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testCallsActionEntityNoParameterReturnValue() throws NoSuchMethodException,
      SecurityException, SerializerException, ODataApplicationException {

    @SuppressWarnings("rawtypes")
    Constructor c = TestJavaActions.class.getConstructors()[0];
    Method m = TestJavaActions.class.getMethod("returnEmbeddable");
    when(action.getConstructor()).thenReturn(c);
    when(action.getMethod()).thenReturn(m);
    final JPAOperationResultParameter rParam = mock(JPAOperationResultParameter.class);
    when(action.getResultParameter()).thenReturn(rParam);

    EdmReturnType rt = mock(EdmReturnType.class);
    EdmComplexType type = mock(EdmComplexType.class);
    when(edmAction.getReturnType()).thenReturn(rt);
    when(rt.getType()).thenReturn(type);
    when(type.getKind()).thenReturn(EdmTypeKind.COMPLEX);

    JPAStructuredType st = mock(JPAStructuredType.class);
    when(sd.getComplexType((EdmComplexType) any())).thenReturn(st);
    when(st.getTypeClass()).thenAnswer((Answer<Class<?>>) invocation -> CommunicationData.class);

    cut.performAction(request, response, requestFormat);
    verify(response, times(1)).setStatusCode(200);
    verify(serializer, times(1)).serialize(any(Annotatable.class), eq(type), any(ODataRequest.class));
  }

  @Test
  public void testCallsActionVoidOneParameterReturnNoContent() throws NoSuchMethodException,
      SecurityException, ODataJPAModelException, NumberFormatException, ODataApplicationException {
    TestJavaActionNoParameter.resetCalls();

    Method m = setConstructorAndMethod("unboundVoidOneParameter", Short.class);

    addParameter(m, (short) 10, "A", 0);

    cut.performAction(request, response, requestFormat);
    verify(response, times(1)).setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    assertEquals(Short.valueOf((short) 10), TestJavaActionNoParameter.param1);
  }

  @Test
  public void testCallsActionVoidOneEnumerationParameterReturnNoContent() throws
          NoSuchMethodException, SecurityException, ODataJPAModelException, NumberFormatException,
      ODataApplicationException {

    TestJavaActionNoParameter.resetCalls();

    Method m = setConstructorAndMethod("unboundVoidOneEnumerationParameter", FileAccess.class);

    addParameter(m, FileAccess.Create, "AccessRights", 0);

    cut.performAction(request, response, requestFormat);
    verify(response, times(1)).setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    assertEquals(FileAccess.Create, TestJavaActionNoParameter.enumeration);
  }

  @Test
  public void testCallsActionVoidTwoParameterReturnNoContent() throws NoSuchMethodException,
      SecurityException, ODataJPAModelException, NumberFormatException, ODataApplicationException {
    TestJavaActionNoParameter.resetCalls();

    Method m = setConstructorAndMethod("unboundVoidTwoParameter", Short.class, Integer.class);

    addParameter(m, (short) 10, "A", 0);
    addParameter(m, 200000, "B", 1);

    cut.performAction(request, response, requestFormat);
    verify(response, times(1)).setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    assertEquals(Short.valueOf((short) 10), TestJavaActionNoParameter.param1);
  }

  @Test
  public void testCallsActionVoidOneParameterNullableGivenNullReturnNoContent() throws
          NoSuchMethodException, SecurityException, ODataJPAModelException, NumberFormatException,
      ODataApplicationException {
    TestJavaActionNoParameter.resetCalls();

    Method m = setConstructorAndMethod("unboundVoidOneParameter", Short.class);

    addParameter(m, null, "A", 0);

    cut.performAction(request, response, requestFormat);
    verify(response, times(1)).setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    assertNull(TestJavaActionNoParameter.param1);
  }

  @Test
  public void testCallsActionVoidOnlyBindingParameter() throws
          NoSuchMethodException, SecurityException, ODataJPAModelException, NumberFormatException,
      EdmPrimitiveTypeException, ODataApplicationException {
    TestJavaActionNoParameter.resetCalls();

    Method m = setConstructorAndMethod("boundOnlyBinding", AdministrativeDivision.class);
    when(edmAction.isBound()).thenReturn(Boolean.TRUE);
    uriResources.add(0, bindingEntity);

    setBindingParameter(m);

    cut.performAction(request, response, requestFormat);
    verify(response, times(1)).setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    assertNotNull(TestJavaActionNoParameter.bindingParam);
    assertEquals("LAU2", TestJavaActionNoParameter.bindingParam.getCodeID());
  }

  @Test
  public void testCallsActionVoidBindingParameterPlusTwoBothNull() throws
          NoSuchMethodException, SecurityException, ODataJPAModelException, NumberFormatException,
      EdmPrimitiveTypeException, ODataApplicationException {
    TestJavaActionNoParameter.resetCalls();

    Method m = setConstructorAndMethod("boundBindingPlus", AdministrativeDivision.class, Short.class, Integer.class);
    when(edmAction.isBound()).thenReturn(Boolean.TRUE);
    uriResources.add(0, bindingEntity);

    setBindingParameter(m);

    JPAParameter jpaParam = mock(JPAParameter.class);
    when(action.getParameter(m.getParameters()[1])).thenReturn(jpaParam);
    when(jpaParam.getName()).thenReturn("A");

    jpaParam = mock(JPAParameter.class);
    when(action.getParameter(m.getParameters()[2])).thenReturn(jpaParam);
    when(jpaParam.getName()).thenReturn("B");

    cut.performAction(request, response, requestFormat);
    verify(response, times(1)).setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    assertNotNull(TestJavaActionNoParameter.bindingParam);
    assertEquals("LAU2", TestJavaActionNoParameter.bindingParam.getCodeID());
    assertNull(TestJavaActionNoParameter.param1);
    assertNull(TestJavaActionNoParameter.param2);
  }

  @Test
  public void testCallsActionVoidBindingParameterPlusTwoFirstNull() throws
          NoSuchMethodException, SecurityException, ODataJPAModelException, NumberFormatException,
      EdmPrimitiveTypeException, ODataApplicationException {
    TestJavaActionNoParameter.resetCalls();

    Method m = setConstructorAndMethod("boundBindingPlus", AdministrativeDivision.class, Short.class, Integer.class);
    when(edmAction.isBound()).thenReturn(Boolean.TRUE);
    uriResources.add(0, bindingEntity);

    setBindingParameter(m);

    JPAParameter jpaParam = mock(JPAParameter.class);
    when(action.getParameter(m.getParameters()[1])).thenReturn(jpaParam);
    when(jpaParam.getName()).thenReturn("A");

    addParameter(m, 20, "B", 2);

    cut.performAction(request, response, requestFormat);
    verify(response, times(1)).setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    assertNotNull(TestJavaActionNoParameter.bindingParam);
    assertEquals("LAU2", TestJavaActionNoParameter.bindingParam.getCodeID());
    assertNull(TestJavaActionNoParameter.param1);
    assertEquals(Integer.valueOf(20), TestJavaActionNoParameter.param2);
  }

  private void setBindingParameter(Method m) throws ODataJPAModelException, EdmPrimitiveTypeException {
    final JPAParameter bindingParam = addParameter(m, null, "Root", 0);
    when(bindingParam.getType()).thenAnswer((Answer<Class<?>>) invocation -> AdministrativeDivision.class);
    final List<UriParameter> keys = new ArrayList<>();
    final UriParameter key1 = mock(UriParameter.class);
    when(bindingEntity.getKeyPredicates()).thenReturn(keys);
    when(key1.getName()).thenReturn("CodeID");
    when(key1.getText()).thenReturn("LAU2");
    keys.add(key1);

    final JPAEntityType et = mock(JPAEntityType.class);
    final JPAPath codePath = mock(JPAPath.class);
    final JPAAttribute code = mock(JPAAttribute.class);
    final EdmPrimitiveType edmString = mock(EdmPrimitiveType.class);
    final CsdlProperty edmProperty = mock(CsdlProperty.class);
    when(sd.getEntity((EdmType) any())).thenReturn(et);
    when(et.getPath("CodeID")).thenReturn(codePath);
    when(et.getAttribute("codeID")).thenReturn(code);
    when(codePath.getLeaf()).thenReturn(code);
    when(code.getInternalName()).thenReturn("codeID");
    when(code.getType()).thenAnswer((Answer<Class<?>>) invocation -> String.class);

    when(code.getProperty()).thenReturn(edmProperty);
    when(odata.createPrimitiveTypeInstance(EdmPrimitiveTypeKind.String)).thenReturn(edmString);
    when(edmString.fromUriLiteral("LAU2")).thenReturn("LAU2");
    when(edmString.valueOfString("LAU2", false, 0, 0, 0, true, code.getType())).thenAnswer(
            (Answer<String>) invocation -> "LAU2");
  }

  private JPAParameter addParameter(final Method m, final Object value, final String name, int index)
      throws ODataJPAModelException {

    Parameter param = mock(Parameter.class);
    when(param.getValue()).thenReturn(value);
    when(param.getName()).thenReturn(name);
    when(param.getValueType()).thenReturn(ValueType.PRIMITIVE);
    actionParameter.put(name, param);

    JPAParameter jpaParam = mock(JPAParameter.class);
    when(action.getParameter(m.getParameters()[index])).thenReturn(jpaParam);
    when(jpaParam.getName()).thenReturn(name);
    return jpaParam;
  }

  @SuppressWarnings("unchecked")
  private Method setConstructorAndMethod(String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
    @SuppressWarnings("rawtypes")
    Constructor c = TestJavaActionNoParameter.class.getConstructors()[0];
    Method m = TestJavaActionNoParameter.class.getMethod(methodName, parameterTypes);
    when(action.getConstructor()).thenReturn(c);
    when(action.getMethod()).thenReturn(m);
    when(action.getReturnType()).thenReturn(null);
    return m;
  }

}