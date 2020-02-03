# 1.3: Text Associations
Next we want to add the country our business partner is located at. Please note that this country may or may not be the same, what we will see in the address later. The country shall be stored as an ISO 3166-1 character 3 coded value, so DEU for Germany or GBR for United Kingdom. In addition, to the description, so _Germany_ respectively _United Kingdom_, of the code value should be provided at the Business Partner. The description itself will be stored within the database table _AdministrativeDivisionDescription_.
The table shall not only store the description for countries, but shall be able to store the description of coded representation of a division of countries, which maybe provided by different authorities. Therefore it will get four key fields:

1. CodePublisher: Representing the publisher of a code list e.g. [ISO](https://en.wikipedia.org/wiki/International_Organization_for_Standardization) or [Eurostat](https://en.wikipedia.org/wiki/Eurostat)

1. CodeID: The ID of the code list. Here [3166-1](https://en.wikipedia.org/wiki/ISO_3166-1) or, [NUTS-3](https://de.wikipedia.org/wiki/NUTS)

1. DivisionCode: The coded representation. So e.g. BEL or CHE.

1. LanguageISO: Language coded in [ISO 639-1](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes)

So we need for JPA a key class:
```Java
package tutorial.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AdministrativeDivisionDescriptionEntityPK implements Serializable {

    @Column(name = "\"CodePublisher\"", nullable = false, length = 10)
    private String codePublisher;

    public String getCodePublisher() { return codePublisher; }

    public void setCodePublisher(String codePublisher) { this.codePublisher = codePublisher; }

    @Column(name = "\"CodeID\"", nullable = false, length = 10)
    private String codeId;

    public String getCodeId() { return codeId; }

    public void setCodeId(String codeId) { this.codeId = codeId; }

    @Column(name = "\"DivisionCode\"", nullable = false, length = 10)
    private String divisionCode;

    public String getDivisionCode() { return divisionCode; }

    public void setDivisionCode(String divisionCode) { this.divisionCode = divisionCode; }

    @Column(name = "\"LanguageISO\"", nullable = false, length = 4)
    private String languageIso;

    public String getLanguageIso() { return languageIso; }

    public void setLanguageIso(String languageIso) { this.languageIso = languageIso; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdministrativeDivisionDescriptionEntityPK that = (AdministrativeDivisionDescriptionEntityPK) o;

        if (!Objects.equals(codePublisher, that.codePublisher)) return false;
        if (!Objects.equals(codeId, that.codeId)) return false;
        if (!Objects.equals(divisionCode, that.divisionCode)) return false;
        return Objects.equals(languageIso, that.languageIso);
    }

    @Override
    public int hashCode() {
        int result = codePublisher != null ? codePublisher.hashCode() : 0;
        result = 31 * result + (codeId != null ? codeId.hashCode() : 0);
        result = 31 * result + (divisionCode != null ? divisionCode.hashCode() : 0);
        result = 31 * result + (languageIso != null ? languageIso.hashCode() : 0);
        return result;
    }

}
```
Please note, that we became a little more lazy and declared the class as _@Embeddable_. This will allow us to simplify _AdministrativeDivisionDescription_ as we do not need to declare the key attributes again. Instead of that we annotate the key attribute with _@EmbeddedId_:
```Java
package tutorial.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "\"AdministrativeDivisionDescription\"", schema = "OLINGO")
public class AdministrativeDivisionDescriptionEntity {

    @EmbeddedId
    private AdministrativeDivisionDescriptionEntityPK key;

    @Basic
    @Column(name = "\"Description\"", nullable = false, length = 100)
    private String description;

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdministrativeDivisionDescriptionEntity that = (AdministrativeDivisionDescriptionEntity) o;

        if (!Objects.equals(key, that.key)) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

}
```
Last step before we can add country and country description to the Business Partner is to publish the classes at the _persistence.xml_:
```XML
<?xml version="1.0" encoding="UTF-8"?>
...
	<persistence-unit name="Tutorial">
        <class>tutorial.model.BusinessPartnerEntity</class>
        <class>tutorial.model.BusinessPartnerRoleEntity</class>
        <class>tutorial.model.AdministrativeDivisionDescriptionEntity</class>
		<properties>
			<property name="eclipselink.logging.level.sql" value="FINEST" />
			...
		</properties>
	</persistence-unit>
</persistence>
```

Now we can add the country and its description attribute to BusinessPartner. The description attribute is represented by an association to AdministrativeDivisionDescription. The association has an additional annotation, _@EdmDescriptionAssociation_. This is used to provide some more metadata, which is needed later to generate a LEFT OUTER join. These are:

1. The path to the attribute containing the languageIso separated by "/". Here the attribute within AdministrativeDivisionDescription is _key_ and the attribute within _key_ is _language_.

2. The path to the attribute with the description.

3. An optional list of attributes of the join that shall have a fixed value.

```Java
public class BusinessPartner implements Serializable {

    ...

    @Basic
    @Column(name = "\"Country\"", length = 4)
    private String country;

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    @EdmDescriptionAssociation(languageAttribute = "key/languageIso", descriptionAttribute = "description", valueAssignments = {
            @EdmDescriptionAssociation.valueAssignment(attribute = "key/codePublisher", value = "ISO"),
            @EdmDescriptionAssociation.valueAssignment(attribute = "key/codeId", value = "3166-1")})
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"DivisionCode\"", referencedColumnName = "\"Country\"", insertable = false, updatable = false)
    private Collection<AdministrativeDivisionDescriptionEntity> countryName;
    ...
```
You may wonder why we do not use an OneToOne relation, but we are not able to fully qualify the key of the target table, as we at least can't provide the language. A usage of OneToOne would therefore result in an exception like _The @JoinColumns on the annotated element [field countryName] from the entity class [class tutorial.model.BusinessPartner] is incomplete. When the source entity class uses a composite primary key, a @JoinColumn must be specified for each join column using the @JoinColumns. Both the name and the referencedColumnName elements must be specified in each such @JoinColumn_.

Having done that, we can have a look at the generated metadata: _http://localhost:8080/Tutorial/Tutorial.svc/$metadata_.

![JPA - OData Mapping](Metadata/Mapping3.png)

Next step: [Tutorial 1.4: Subtypes](1-4-Subtypes.md)