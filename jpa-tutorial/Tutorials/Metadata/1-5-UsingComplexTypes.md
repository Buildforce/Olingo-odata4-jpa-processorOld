# 1.5: Use Complex Types
The first version of our business partner was very simple and did not contain information about the address or the information about the user that created or changed the business partner. This is what we want to do now.

First we want to introduce the address. The assumption is that the fields of the address shall be reusable, so be used later also in another entity, which is the reason for defining it in a separate class:
```Java
package tutorial.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PostalDataEntity {
    @Basic
    @Column(name ="\"Address.StreetName\"", length = 200)
    private String streetName;

    public String getStreetName() { return streetName; }

    public void setStreetName(String addressStreetName) { this.streetName = addressStreetName; }

    @Basic
    @Column(name ="\"Address.StreetNumber\"", length = 60)
    private String houseNumber;

    public String getHouseNumber() { return houseNumber; }

    public void setHouseNumber(String addressStreetNumber) { this.houseNumber = addressStreetNumber; }

    @Basic
    @Column(name ="\"Address.PostOfficeBox\"", length = 60)
    private String pOBox;

    public String getPOBox() { return pOBox; }

    public void setPOBox(String addressPostOfficeBox) { this.pOBox = addressPostOfficeBox; }

    @Basic
    @Column(name ="\"Address.City\"", length = 100)
    private String cityName;

    public String getCityName() { return cityName; }

    public void setCityName(String addressCity) { this.cityName = addressCity; }

    @Basic
    @Column(name ="\"Address.PostalCode\"", length = 60)
    private String postalCode;

    public String getPostalCode() { return postalCode; }

    public void setPostalCode(String addressPostalCode) { this.postalCode = addressPostalCode; }

    @Basic
    @Column(name ="ADDRESS_REGIONCODEPUBLISHER", nullable = false, length = 10)
    private String regionCodePublisher = "ISO";

    public String getRegionCodePublisher() { return regionCodePublisher; }

    public void setRegionCodePublisher(String addressRegionCodePublisher)
    { this.regionCodePublisher = addressRegionCodePublisher; }

    @Basic
    @Column(name ="ADDRESS_REGIONCODEID", nullable = false, length = 10)
    private String regionCodeId = "3166-2";
    public String getRegionCodeId() { return regionCodeId; }

    public void setRegionCodeId(String addressRegionCodeId) { this.regionCodeId = addressRegionCodeId; }

    @Basic
    @Column(name ="ADDRESS_REGION", length = 100)
    private String region;

    public String getRegion() { return region; }

    public void setRegion(String addressRegion) { this.region = addressRegion; }

    @Basic
    @Column(name ="\"Address.Country\"", length = 100)
    private String country;
    public String getCountry() { return country; }

    public void setCountry(String addressCountry) { this.country = addressCountry; }

    @EdmDescriptionAssociation(languageAttribute = "key/languageIso", descriptionAttribute = "description")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "\"CodePublisher\"", referencedColumnName = "ADDRESS_REGIONCODEPUBLISHER", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "\"CodeID\"", referencedColumnName = "ADDRESS_REGIONCODEID", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "\"DivisionCode\"", referencedColumnName = "ADDRESS_REGION", nullable = false, insertable = false, updatable = false)
    })
    private Collection<AdministrativeDivisionDescriptionEntity> regionDescriptions;

    @Override
    public String toString() {
        return "PostalAddressData [streetName=" + streetName + ", houseNumber=" + houseNumber + ", pOBox=" + pOBox
                + ", postalCode=" + postalCode + ", cityName=" + cityName + ", country=" + country + ", region=" + region + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostalDataEntity that = (PostalDataEntity) o;

        if (!Objects.equals(streetName, that.streetName)) return false;
        if (!Objects.equals(houseNumber, that.houseNumber)) return false;
        if (!Objects.equals(pOBox, that.pOBox)) return false;
        if (!Objects.equals(cityName, that.cityName)) return false;
        if (!Objects.equals(postalCode, that.postalCode)) return false;
        if (!Objects.equals(regionCodePublisher, that.regionCodePublisher)) return false;
        if (!Objects.equals(regionCodeId, that.regionCodeId)) return false;
        if (!Objects.equals(region, that.region)) return false;
        return Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        int result = streetName != null ? streetName.hashCode() : 0;
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        result = 31 * result + (pOBox != null ? pOBox.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (regionCodePublisher != null ? regionCodePublisher.hashCode() : 0);
        result = 31 * result + (regionCodeId != null ? regionCodeId.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

}
```
Now we can add the PostalAddressData to the BusinessPartner:

```Java
public abstract class BusinessPartnerEntity {
    ...
    @OneToMany(mappedBy = "businessPartnerByBusinessPartnerId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<BusinessPartnerRoleEntity> roles;

    @Embedded
    private final PostalDataEntity address = new PostalDataEntity();
    ...
```
~~After adding tutorial.model.PostalAddressData to the persistence.xml~~ we can have already a look at the first step: _http://localhost:8080/Tutorial/Tutorial.svc/$metadata_.

![JPA - OData Mapping](Metadata/Mapping4.png)

Second we want to introduce AdministrativeInformation, which is a bit more complicated as it has two groups of identical fields. The person that made a change and a time stamp giving the point in time when the change was made. This leads to nested embedded types. Let us start with the inner type:

```Java
package tutorial.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class ChangeInformationEntity {
    @Basic
    @Column(nullable = false, length = 32)
    private String by;

    public String getBy() { return by; }

    public void setBy(String by) { this.by = by; }

    @Basic
    @Column(precision = 6)
    private Timestamp at;

    public Timestamp getAt() { return at; }

    public void setAt(Timestamp at) { this.at = at; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChangeInformationEntity that = (ChangeInformationEntity) o;

        if (!Objects.equals(by, that.by)) return false;
        return Objects.equals(at, that.at);
    }

    @Override
    public int hashCode() {
        int result = by != null ? by.hashCode() : 0;
        result = 31 * result + (at != null ? at.hashCode() : 0);
        return result;
    }

}
```
This is now used within AdministrativeInformation:
```Java
package tutorial.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class AdministrativeInformationEntity {

    @Embedded
    private ChangeInformationEntity created;

    @Embedded
    private ChangeInformationEntity updated;

}
```
We can now add the AdministrativeInformation to the Business Partner:
```Java
public abstract class BusinessPartnerEntity {
    ...
    @Embedded
    private final PostalDataEntity address = new PostalDataEntity();

    @Embedded
    private final AdministrativeInformationEntity administrativeInformation = new AdministrativeInformationEntity();
```
And, after adding both classes to the persistence.xml, we can have a look at the result: _http://localhost:8080/Tutorial/Tutorial.svc/$metadata_

Before we go ahead, we want to do a small preparation step for the second set of tutorials. As of now we can see the AdministrativeInformation in our metadata document, but we won't be able to retrieve them. You may have noticed, that there a no column names given in ChangeInformation. We can't do that as we use ChangeInformation twice.
Instead of that we have to "rename" the fields within AdministrativeInformation:
```Java
...
@Embeddable
public class AdministrativeInformationEntity {

    @Embedded
    @AttributeOverrides({

            @AttributeOverride(name = "by", column = @Column(name = "\"CreatedBy\"")),
            @AttributeOverride(name = "at", column = @Column(name = "\"CreatedAt\""))})
    private ChangeInformationEntity created;

    @Embedded
    @AttributeOverrides({

            @AttributeOverride(name = "by", column = @Column(name = "\"UpdatedBy\"")),
            @AttributeOverride(name = "at", column = @Column(name = "\"UpdatedAt\""))})
    private ChangeInformationEntity updated;
...
```

Next step: [Tutorial 1.6: Navigation Properties And Complex Types](1-6-NavigationAndComplexTypes.md)