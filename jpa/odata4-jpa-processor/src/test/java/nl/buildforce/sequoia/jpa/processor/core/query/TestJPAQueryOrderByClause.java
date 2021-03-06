package nl.buildforce.sequoia.jpa.processor.core.query;

import com.fasterxml.jackson.databind.node.ArrayNode;
import nl.buildforce.sequoia.jpa.processor.core.api.JPAODataGroupsProvider;
import nl.buildforce.sequoia.jpa.processor.core.util.IntegrationTestHelper;
import nl.buildforce.sequoia.jpa.processor.core.util.TestBase;
import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestJPAQueryOrderByClause extends TestBase {

  @Test
  public void testOrderByOneProperty() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf, "Organizations?$orderby=Name1");
    helper.assertStatus(HttpStatusCode.OK.getStatusCode());

    ArrayNode orgs = helper.getValues();
    assertEquals("Eighth Org.", orgs.get(0).get("Name1").asText());
    assertEquals("Third Org.", orgs.get(9).get("Name1").asText());
  }

  @Test
  public void testOrderByOneComplexPropertyAsc() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf, "Organizations?$orderby=Address/Region");
    helper.assertStatus(HttpStatusCode.OK.getStatusCode());

    ArrayNode orgs = helper.getValues();
    assertEquals("US-CA", orgs.get(0).get("Address").get("Region").asText());
    assertEquals("US-UT", orgs.get(9).get("Address").get("Region").asText());
  }

  @Test
  public void testOrderByOneComplexPropertyDesc() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf, "Organizations?$orderby=Address/Region desc");
    if (helper.getStatus() != 200)
      System.out.println(helper.getRawResult());
    helper.assertStatus(HttpStatusCode.OK.getStatusCode());

    ArrayNode orgs = helper.getValues();
    assertEquals("US-UT", orgs.get(0).get("Address").get("Region").asText());
    assertEquals("US-CA", orgs.get(9).get("Address").get("Region").asText());
  }

  @Test
  public void testOrderByTwoPropertiesDescAsc() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "Organizations?$orderby=Address/Region desc,Name1 asc");
    helper.assertStatus(HttpStatusCode.OK.getStatusCode());

    ArrayNode orgs = helper.getValues();
    assertEquals("US-UT", orgs.get(0).get("Address").get("Region").asText());
    assertEquals("US-CA", orgs.get(9).get("Address").get("Region").asText());
    assertEquals("Third Org.", orgs.get(9).get("Name1").asText());
  }

  @Test
  public void testOrderByTwoPropertiesDescDesc() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "Organizations?$orderby=Address/Region desc,Name1 desc");
    helper.assertStatus(HttpStatusCode.OK.getStatusCode());

    ArrayNode orgs = helper.getValues();
    assertEquals("US-UT", orgs.get(0).get("Address").get("Region").asText());
    assertEquals("US-CA", orgs.get(9).get("Address").get("Region").asText());
    assertEquals("First Org.", orgs.get(9).get("Name1").asText());
  }

  @Test
  public void testOrderBy$CountDesc() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf, "Organizations?$orderby=Roles/$count desc");
    helper.assertStatus(HttpStatusCode.OK.getStatusCode());

    ArrayNode orgs = helper.getValues();
    assertEquals("3", orgs.get(0).get("ID").asText());
    assertEquals("2", orgs.get(1).get("ID").asText());
  }

  @Test
  public void testOrderBy$CountAndSelectAsc() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "Organizations?$select=ID,Name1,Name2,Address/CountryName&$orderby=Roles/$count asc");
    helper.assertStatus(HttpStatusCode.OK.getStatusCode());

    ArrayNode orgs = helper.getValues();
    assertEquals("3", orgs.get(9).get("ID").asText());
    assertEquals("2", orgs.get(8).get("ID").asText());
  }

  @Test
  public void testCollectionOrderBy$CountAsc() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "CollectionDeeps?$orderby=FirstLevel/SecondLevel/Comment/$count asc");

    helper.assertStatus(HttpStatusCode.OK.getStatusCode());
    ArrayNode deeps = helper.getValues();
    assertEquals("501", deeps.get(0).get("ID").asText());
    assertEquals("502", deeps.get(1).get("ID").asText());
  }

  @Test
  public void testCollectionOrderBy$CountDesc() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "CollectionDeeps?$orderby=FirstLevel/SecondLevel/Comment/$count desc");

    helper.assertStatus(HttpStatusCode.OK.getStatusCode());
    ArrayNode deeps = helper.getValues();
    assertEquals("502", deeps.get(0).get("ID").asText());
    assertEquals("501", deeps.get(1).get("ID").asText());
  }

  @Test
  public void testOrderBy$CountAsc() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "Organizations?$orderby=Roles/$count asc");
    helper.assertStatus(HttpStatusCode.OK.getStatusCode());

    ArrayNode orgs = helper.getValues();
    assertEquals("3", orgs.get(9).get("ID").asText());
    assertEquals("2", orgs.get(8).get("ID").asText());
  }

  @Test
  public void testOrderBy$CountDescComplexPropertyAcs() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "Organizations?$orderby=Roles/$count desc,Address/Region desc");
    helper.assertStatus(HttpStatusCode.OK.getStatusCode());

    ArrayNode orgs = helper.getValues();
    assertEquals("3", orgs.get(0).get("ID").asText());
    assertEquals("2", orgs.get(1).get("ID").asText());
    assertEquals("US-CA", orgs.get(9).get("Address").get("Region").asText());
    assertEquals("6", orgs.get(9).get("ID").asText());
  }

  @Test
  public void testOrderByAndFilter() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "AdministrativeDivisions?$filter=CodeID eq 'NUTS' or CodeID eq '3166-1'&$orderby=CountryCode desc");

    helper.assertStatus(HttpStatusCode.OK.getStatusCode());

    ArrayNode orgs = helper.getValues();
    assertEquals(4, orgs.size());
    assertEquals("USA", orgs.get(0).get("CountryCode").asText());
  }

  @Test
  public void testOrderByAndTopSkip() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "AdministrativeDivisions?$filter=CodeID eq 'NUTS' or CodeID eq '3166-1'&$orderby=CountryCode desc&$top=1&$skip=2");

    helper.assertStatus(HttpStatusCode.OK.getStatusCode());

    ArrayNode orgs = helper.getValues();
    assertEquals(1, orgs.size());
    assertEquals("CHE", orgs.get(0).get("CountryCode").asText());
  }

  @Test
  public void testOrderByNavigationOneHop() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "Organizations('3')/Roles?$orderby=RoleCategory desc");
    helper.assertStatus(HttpStatusCode.OK.getStatusCode());

    ArrayNode orgs = helper.getValues();
    assertEquals(3, orgs.size());
    assertEquals("C", orgs.get(0).get("RoleCategory").asText());
  }

  @Test
  public void testOrderByGroupedPropertyWithoutGroup() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "BusinessPartnerWithGroupss?$orderby=Country desc");
    helper.assertStatus(403);
  }

  @Test
  public void testOrderByPropertyWithGroupsOneGroup() throws IOException, ODataException {

    final JPAODataGroupsProvider groups = new JPAODataGroupsProvider();
    groups.addGroup("Person");
    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "BusinessPartnerWithGroupss?$orderby=Country desc", groups);
    helper.assertStatus(HttpStatusCode.OK.getStatusCode());
  }

  @Test
  public void testOrderByGroupedComplexPropertyWithoutGroup() throws IOException, ODataException {

    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "BusinessPartnerWithGroupss?$orderby=Address/Country desc");
    helper.assertStatus(403);
  }

  @Test
  public void testOrderByGroupedComplexPropertyWithGroupsOneGroup() throws IOException, ODataException {

    final JPAODataGroupsProvider groups = new JPAODataGroupsProvider();
    groups.addGroup("Company");
    IntegrationTestHelper helper = new IntegrationTestHelper(emf,
        "BusinessPartnerWithGroupss?$orderby=Address/Country desc", groups);
    helper.assertStatus(HttpStatusCode.OK.getStatusCode());
  }
}