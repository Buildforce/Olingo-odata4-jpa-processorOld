# 1.1: Create A Simple Entity
We want to start with a very simple Business Partner entity. As the first step we create a package in our _src/main/java_ folder: _tutorial.model_. Within that package we create class BusinessPartner, which implements the Serializable interface:
```Java
package tutorial.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "BusinessPartner")
@Table(name = "\"BusinessPartner\"", schema = "OLINGO")
public class BusinessPartnerEntity {
    @Id
    @Column(name = "ID", length = 32, nullable = false)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "\"ETag\"")
    private Long eTag;

    public Long geteTag() {
        return eTag;
    }

    public void seteTag(Long eTag) {
        this.eTag = eTag;
    }

    @Basic
    @Column(name = "\"CustomString1\"", length = 250)
    private String customString1;

    public String getCustomString1() { return customString1; }

    public void setCustomString1(String customString1) { this.customString1 = customString1; }

    @Basic
    @Column(name = "\"CustomString2\"", length = 250)
    private String customString2;

    public String getCustomString2() { return customString2; }

    public void setCustomString2(String customString2) { this.customString2 = customString2; }

    @Basic
    @Column(name = "\"CustomNum1\"", precision = 10, scale = 5)
    private BigDecimal customNum1;

    public BigDecimal getCustomNum1() { return customNum1; }

    public void setCustomNum1(BigDecimal customNum1) { this.customNum1 = customNum1; }

    @Basic
    @Column(name = "\"CustomNum2\"", scale = 5)
    private Long customNum2;

    public Long getCustomNum2() { return customNum2; }

    public void setCustomNum2(Long customNum2) { this.customNum2 = customNum2; }

    @OneToMany(mappedBy = "businessPartnerByBusinessPartnerId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<BusinessPartnerRoleEntity> roles;

    @Basic
    @Column(name = "\"Country\"", length = 4)
    private String country;

    public void setCustomNum2(Long customNum2) {
        this.customNum2 = customNum2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessPartnerEntity that = (BusinessPartnerEntity) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(eTag, that.eTag)) return false;
        if (!Objects.equals(customString1, that.customString1)) return false;
        if (!Objects.equals(customString2, that.customString2)) return false;
        if (!Objects.equals(customNum1, that.customNum1)) return false;
        return Objects.equals(customNum2, that.customNum2);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (eTag != null ? eTag.hashCode() : 0);
        result = 31 * result + (customString1 != null ? customString1.hashCode() : 0);
        result = 31 * result + (customString2 != null ? customString2.hashCode() : 0);
        result = 31 * result + (customNum1 != null ? customNum1.hashCode() : 0);
        result = 31 * result + (customNum2 != null ? customNum2.hashCode() : 0);
        return result;
    }

}
```
Next we have to create our _persistence.xml_ file. This will be located under _src/main/resources_ in a new folder called _META-INF_.
```XML
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd"
             version="2.2">

    <persistence-unit name="TutorialPU">
        <class>tutorial.model.BusinessPartnerEntity</class>
     <properties>
        <property name="eclipselink.logging.level.sql" value="FINEST" />
        <property name="eclipselink.logging.parameters" value="true" />
        <property name="eclipselink.logging.timestamp" value="true" />
        <property name="eclipselink.weaving" value="static" />
        <property name="eclipselink.persistence-context.flush-mode" value="commit" />
        <property name="javax.persistence.validation.mode" value="NONE" />
        <property name="javax.persistence.jdbc.url" value="jdbc:hsqldb:mem:com.sample" />
        <property name="javax.persistence.jdbc.driver" value="org.hsqldb.jdbc.JDBCDriver" />
    </properties>

    </persistence-unit>
</persistence>
```
The XML file is as of now dominated by information about the logging and the database connection. Therefore, I like to point to two lines. First the name of the persistence-unit, here _Tutorial_. This is important for us for two reasons:
  1. It is the link between the metadata and the entity manager
  2. It is used as the OData namespace

Only one persistence unit is supported, which means also only one OData schema within a Data Services Document is supported. The other one is the declaration of the JPA entity class _<class>tutorial.model.BusinessPartner</class>_. Over the time we will add more here.

_web.xml_ is obsolete by use of the @WebServlet(urlPatterns="/DemoService.svc/*") annotation in _OdataServlet_ class

```XML
```
Second we create the servlet. For this we create a new package _tutorial.service_ in _/src/main/java_.The class shall have the name _Servlet_, as we have declared it in the _web.xml_ file and inherit from _javax.servlet.http.HttpServlet_. We overwrite the method _service_.
```Java
package tutorial.service;

import java.util.ArrayList;
import java.util.HashMap;

import jakarta.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.olingo.commons.api.ex.ODataException;
import nl.buildforce.sequoia.jpa.metadata.api.JPAEdmProvider;
import nl.buildforce.sequoia.jpa.metadata.api.JPAEntityManagerFactory;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;

@WebServlet(urlPatterns="/DemoService.svc/*")
public class OdataServlet extends HttpServlet {
    protected static final String PUNIT_NAME = "TutorialPU";
    private final EntityManagerFactory emf = JPAEntityManagerFactory.getEntityManagerFactory(PUNIT_NAME, new HashMap<>());

    final OData odata = OData.newInstance();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            JPAEdmProvider metadataProvider = new JPAEdmProvider(PUNIT_NAME, emf, null, null);

            ServiceMetadata edm = odata.createServiceMetadata(metadataProvider, new ArrayList<>());
            ODataHttpHandler handler = odata.createHandler(edm);

            handler.process(req, resp);
        } catch (RuntimeException | ODataException e) {
            throw new ServletException(e);
        }
    }
}
```
Let us have a look at the code. We have created a constant _PUNIT_NAME_ to store the name of our persistence unit. This is used on the hand to create the Entity Manger Factory. JPA Processor provides a factory for this, which is used here, but it is not mandatory to use it. On the other hand JPAEdmProvider is created. This class is responsible for converting the JPA metadata into OData metadata. The other calls are needed to let Olingo answer the request.
Now we can have a look at what we have achieved up to now. For this we want to run our app on a server. To do make a right mouse click on the project and choose _Run As -> Run on Server_ .

![Run on Server](Metadata/RunOnServer.png)

Choose a web server and start it. With the following url you should now get the service document _http://localhost:8080/Tutorial/Tutorial.svc/_, which should look as follows:
```XML
<app:service xmlns:atom="http://www.w3.org/2005/Atom" xmlns:app="http://www.w3.org/2007/app"
             xmlns:metadata="http://docs.oasis-open.org/odata/ns/metadata" metadata:context="$metadata">
    <app:workspace>
        <atom:title>TutorialPU.TutorialPUContainer</atom:title>
        <app:collection href="BusinessPartners" metadata:name="BusinessPartners">
            <atom:title>BusinessPartners</atom:title>
        </app:collection>
    </app:workspace>
</app:service>
```
With _http://localhost:8080/Tutorial/Tutorial.svc/$metadata_ we can have a look at our metadata document. The following picture should give an overview of the metadata mapping:

![JPA - OData Mapping](Metadata/Mapping1.png)

As already mentioned the persistence unit has become the namespace of our OData schema. In addition, it's used to name the container. The JPA Entity name became the OData Entity Type name and its plural is used as the name of the Entity Type Set. Column metadata is converted in to Property metadata, like name, length or precision and scale.

Please go ahead with [Tutorial 1.2 : Use Navigation Properties](1-2-UseNavigationProperties.md)