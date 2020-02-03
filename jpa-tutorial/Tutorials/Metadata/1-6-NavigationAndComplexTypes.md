# 1.6: Navigation Properties And Complex Types
You may have noticed, that the address has a region. We want to give the opportunity to get more details about the region.
So we need to create navigation property to an entity that has this details. The entity, AdministrativeDivision, can contain e.g. the population and the area or a link to a super-ordinate
region and is the last one we create. AdministrativeDivision, as AdministrativeDivisionDescription, has a key build out of three attributes, so as usual we need a separate class for the key:
```Java
package tutorial.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AdministrativeDivisionEntityPK implements Serializable {

    @Column(name = "\"CodePublisher\"", length = 10)
    @Id
    private String codePublisher;

    public String getCodePublisher() { return codePublisher; }

    public void setCodePublisher(String codePublisher) { this.codePublisher = codePublisher; }

    @Column(name = "\"CodeID\"", length = 10)
    @Id
    private String codeId;

    public String getCodeId() { return codeId; }

    public void setCodeId(String codeId) { this.codeId = codeId; }

    @Column(name = "\"DivisionCode\"", length = 10)
    @Id
    private String divisionCode;

    public String getDivisionCode() { return divisionCode; }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdministrativeDivisionEntityPK that = (AdministrativeDivisionEntityPK) o;

        if (!Objects.equals(codePublisher, that.codePublisher)) return false;
        if (!Objects.equals(codeId, that.codeId)) return false;
        return Objects.equals(divisionCode, that.divisionCode);
    }

    @Override
    public int hashCode() {
        int result = codePublisher != null ? codePublisher.hashCode() : 0;
        result = 31 * result + (codeId != null ? codeId.hashCode() : 0);
        result = 31 * result + (divisionCode != null ? divisionCode.hashCode() : 0);
        return result;
    }

}
```
With that we can create AdministrativeDivision:
```Java
package tutorial.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "AdministrativeDivision")
@Table(name = "\"AdministrativeDivision\"", schema = "OLINGO")
@IdClass(AdministrativeDivisionEntityPK.class)
public class AdministrativeDivisionEntity {
    @Id
    @Column(name = "\"CodePublisher\"", length = 10)
    private String codePublisher;

    public String getCodePublisher() { return codePublisher; }

    public void setCodePublisher(String codePublisher) { this.codePublisher = codePublisher; }

    @Id
    @Column(name = "\"CodeID\"", length = 10)
    private String codeId;

    public String getCodeId() { return codeId; }

    public void setCodeId(String codeId) { this.codeId = codeId; }

    @Id
    @Column(name = "\"DivisionCode\"", length = 10)
    private String divisionCode;

    public String getDivisionCode() { return divisionCode; }

    public void setDivisionCode(String divisionCode) { this.divisionCode = divisionCode; }

    @Basic
    @Column(name = "\"CountryISOCode\"", length = 4)
    private String countryIsoCode;

    public String getCountryIsoCode() { return countryIsoCode; }

    public void setCountryIsoCode(String countryIsoCode) { this.countryIsoCode = countryIsoCode; }

    @Basic
    @Column(name = "\"ParentCodeID\"", length = 10, insertable = false, updatable = false)
    private String parentCodeId;

    public String getParentCodeId() { return parentCodeId; }

    public void setParentCodeId(String parentCodeId) { this.parentCodeId = parentCodeId; }

    @Basic
    @Column(name = "\"ParentDivisionCode\"", length = 10, insertable = false, updatable = false)
    private String parentDivisionCode;

    public String getParentDivisionCode() { return parentDivisionCode; }

    public void setParentDivisionCode(String parentDivisionCode) { this.parentDivisionCode = parentDivisionCode; }

    @Basic
    @Column(name = "\"AlternativeCode\"", length = 10)
    private String alternativeCode;

    public String getAlternativeCode() { return alternativeCode; }

    public void setAlternativeCode(String alternativeCode) { this.alternativeCode = alternativeCode; }

    @Basic
    @Column(name = "\"Area\"")
    private Integer area;

    public Integer getArea() { return area; }

    public void setArea(Integer area) { this.area = area; }

    @Basic
    @Column(name = "\"Population\"")
    private Long population;

    public Long getPopulation() { return population; }

    public void setPopulation(Long population) { this.population = population; }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(referencedColumnName = "\"CodePublisher\"", name = "\"CodePublisher\"", nullable = false, insertable = false, updatable = false),
            @JoinColumn(referencedColumnName = "\"CodeID\"", name = "\"ParentCodeID\"", nullable = false),
            @JoinColumn(referencedColumnName = "\"DivisionCode\"", name = "\"ParentDivisionCode\"", nullable = false)
    })
    private AdministrativeDivisionEntity parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<AdministrativeDivisionEntity> children;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdministrativeDivisionEntity that = (AdministrativeDivisionEntity) o;

        if (!Objects.equals(codePublisher, that.codePublisher)) return false;
        if (!Objects.equals(codeId, that.codeId)) return false;
        if (!Objects.equals(divisionCode, that.divisionCode)) return false;
        if (!Objects.equals(countryIsoCode, that.countryIsoCode)) return false;
        if (!Objects.equals(parentCodeId, that.parentCodeId)) return false;
        if (!Objects.equals(parentDivisionCode, that.parentDivisionCode)) return false;
        if (!Objects.equals(alternativeCode, that.alternativeCode)) return false;
        if (!Objects.equals(area, that.area)) return false;
        return Objects.equals(population, that.population);
    }

    @Override
    public int hashCode() {
        int result = codePublisher != null ? codePublisher.hashCode() : 0;
        result = 31 * result + (codeId != null ? codeId.hashCode() : 0);
        result = 31 * result + (divisionCode != null ? divisionCode.hashCode() : 0);
        result = 31 * result + (countryIsoCode != null ? countryIsoCode.hashCode() : 0);
        result = 31 * result + (parentCodeId != null ? parentCodeId.hashCode() : 0);
        result = 31 * result + (parentDivisionCode != null ? parentDivisionCode.hashCode() : 0);
        result = 31 * result + (alternativeCode != null ? alternativeCode.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (population != null ? population.hashCode() : 0);
        return result;
    }

}
```
Please note, that with the current design it is not possible to create a link between regions of different code publisher.
Having done that, we can create an association from the address to an administrative division:
```Java
@Embeddable
public class PostalDataEntity {
	...
    @Basic
    @Column(name ="ADDRESS_REGION", length = 100)
    private String region;

    public String getRegion() { return region; }

    public void setRegion(String addressRegion) { this.region = addressRegion; }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "ADDRESS_REGIONCODEPUBLISHER", referencedColumnName = "\"CodePublisher\"", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "ADDRESS_REGIONCODEID", referencedColumnName = "\"CodeID\"", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "ADDRESS_REGION", referencedColumnName = "\"DivisionCode\"", nullable = false, insertable = false, updatable = false) })
    private AdministrativeDivisionEntity administrativeDivision;
```
If we would now have a look at the metadata, we will notice the navigation property at the complex type PostalAddressData.
```XML
<ComplexType Name="PostalDataEntity">
  <Property Name="Country" Type="Edm.String" MaxLength="100"/>
  <Property Name="StreetName" Type="Edm.String" MaxLength="200"/>
  <Property Name="POBox" Type="Edm.String" MaxLength="60"/>
  <Property Name="CityName" Type="Edm.String" MaxLength="100"/>
  <Property Name="PostalCode" Type="Edm.String" MaxLength="60"/>
  <Property Name="HouseNumber" Type="Edm.String" MaxLength="60"/>
  <Property Name="RegionCodePublisher" Type="Edm.String" Nullable="false" DefaultValue="ISO" MaxLength="10"/>
  <Property Name="Region" Type="Edm.String" MaxLength="100"/>
  <Property Name="RegionDescriptions" Type="Edm.String" MaxLength="100"/>
  <Property Name="RegionCodeId" Type="Edm.String" Nullable="false" DefaultValue="3166-2" MaxLength="10"/>
  <NavigationProperty Name="AdministrativeDivision" Type="TutorialPU.AdministrativeDivision">
    <ReferentialConstraint Property="RegionCodePublisher" ReferencedProperty="CodePublisher"/>
    <ReferentialConstraint Property="RegionCodeId" ReferencedProperty="CodeId"/>
    <ReferentialConstraint Property="Region" ReferencedProperty="DivisionCode"/>
  </NavigationProperty>
</ComplexType>
```
In case we have nested embedded types like we have with ChangeInformation we have the same problem we had with the attribute names and have to solve in the same way.
To show that we want to assume that the user is a Person:
```Java
@Embeddable
public class ChangeInformationEntity {
    @Basic
    @Column(nullable = false, length = 32)
    private String by;

    public String getBy() { return by; }

    public void setBy(String by) { this.by = by; }

    @ManyToOne
    @JoinColumn(name = "\"by\"", referencedColumnName = "ID", insertable = false, updatable = false)
    PersonEntity user;

	...
}
```
Next we have to rename it, so that AdministrativeInformation looks as follows:
```Java
package tutorial.model;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
public class AdministrativeInformationEntity {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "by", column = @Column(name = "\"CreatedBy\"")),
            @AttributeOverride(name = "at", column = @Column(name = "\"CreatedAt\""))})
    @AssociationOverride(name = "user", joinColumns = @JoinColumn(referencedColumnName = "ID", name = "\"CreatedBy\"", insertable = false, updatable = false))
    private ChangeInformationEntity created;

	...

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "by", column = @Column(name = "\"UpdatedBy\"")),
            @AttributeOverride(name = "at", column = @Column(name = "\"UpdatedAt\""))})
    @AssociationOverride(name = "user", joinColumns = @JoinColumn(referencedColumnName = "\"ID\"", name = "\"UpdatedBy\"", insertable = false, updatable = false))
    private ChangeInformationEntity updated;

	...

}
```
As it is not so easy to find that the navigation properties are really defined at the Person and the Company we should have a look at the mapping picture:

![JPA - OData Mapping](Metadata/Mapping6.png)

Next step: [Tutorial 1.7: Suppressing Elements](1-7-SuppressingElements.md)