# 1.13 Collection Properties
If you like to express a composition relationship between an entity type and another type, OData provides the option to create collections of complex or primitive types.

As a small example we want to allow our users to comment companies. So a comment belongs exactly to one company and shall be deleted if the company gets deleted. As JPA provides an analog concept with the annotation `@ElementCollection` and `@CollectionTable` we use those to realize the new requirement. We extend Company as follows:
```Java
public class CompanyEntity extends BusinessPartnerEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "\"AbcClass\"")
    private ABCClassification abcClass;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(schema = "OLINGO", name = "\"Comment\"",
            joinColumns = @JoinColumn(name = "\"BusinessPartnerID\""))
    @Column(name = "\"Text\"")
    private List<String> comment = new ArrayList<>();

    @Basic
    @Column(name = "\"NameLine1\"", length = 250)
    private String nameLine1;

  ...

}
```
The resulting metadata will look as follows:
```XML
	<EntityType Name="Company" BaseType="TutorialPU.BusinessPartner">
		<Property Name="AbcClass" Type="TutorialPU.ABCClassification"/>
		<Property Name="NameLine1" Type="Edm.String" MaxLength="250"/>
		<Property Name="NameLine2" Type="Edm.String" MaxLength="250"/>
		<Property Name="Comment" Type="Collection(Edm.String)" MaxLength="255"/>
	</EntityType>
```

If we want to be able to filter on the collection attribute (e.g. with `http://localhost:8080/Tutorial/Tutorial.svc/Companies?$filter=Comment/$count%20gt%200`), for technical reasons, we also need a POJO. In order not be part of the API, we annotate it with `@EdmIgnore` and register the class in the _persistence.xml_ file:
```Java
package tutorial.model;

import java.sql.Clob;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;

@EdmIgnore
@Entity
@Table(name = "\"Comment\"", schema = "OLINGO")
public class CommentEntity {
    @Id
    @Column(name = "\"BusinessPartnerID\"", nullable = false, length = 32)
    private String businessPartnerId;

    public String getBusinessPartnerId() { return businessPartnerId; }

    public void setBusinessPartnerId(String businessPartnerId) { this.businessPartnerId = businessPartnerId; }

    /*@Id*/
    @Column(name = "\"Order\"", nullable = false)
    private int order;

    public int getOrder() { return order; }

    public void setOrder(int order) { this.order = order; }

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "\"Text\"")
    @Lob
    private Clob text;

    public Clob getText() { return text; }

    public void setText(Clob text) { this.text = text; }

    public CommentEntity() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentEntity that = (CommentEntity) o;

        if (order != that.order) return false;
        if (!Objects.equals(businessPartnerId, that.businessPartnerId)) return false;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        int result = businessPartnerId != null ? businessPartnerId.hashCode() : 0;
        result = 31 * result + order;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

}
```

Not supported are nested collection properties as well as navigation properties being part of a collection.