# 1.4: Subtypes
Currently, we have one entity representing all our business partner, but indeed they are either companies or persons. We want to be able to distinguish between both,

even so we still use only one database table. As the first step we create two new classes:

First the Person:

```Java
package tutorial.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;

@Entity(name = "Person")
@DiscriminatorValue(value = "1")
@Table(schema = "OLINGO", name = "\"BusinessPartner\"")
public class PersonEntity extends BusinessPartnerEntity {

    @Basic
    @Column(name = "\"NameLine1\"", length = 250)
    private String nameLine1;
    public String getNameLine1() {
        return nameLine1;
    }

    public void setNameLine1(String nameLine1) {
        this.nameLine1 = nameLine1;
    }

    @Basic
    @Column(name = "\"NameLine2\"", length = 250)
    private String nameLine2;
    public String getNameLine2() {
        return nameLine2;
    }

    public void setNameLine2(String nameLine2) {
        this.nameLine2 = nameLine2;
    }

    @Basic
    @Column(name = "\"BirthDay\"")
    private Date birthDay;
    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonEntity personEntity = (PersonEntity) o;

        if (!Objects.equals(nameLine1, personEntity.nameLine1)) return false;
        if (!Objects.equals(nameLine2, personEntity.nameLine2)) return false;
        return Objects.equals(birthDay, personEntity.birthDay);
    }

    @Override
    public int hashCode() {
        int result = nameLine1 != null ? nameLine1.hashCode() : 0;
        result = 31 * result + (nameLine2 != null ? nameLine2.hashCode() : 0);
        result = 31 * result + (birthDay != null ? birthDay.hashCode() : 0);
        return result;
    }

}
```

Second the Company
```Java
package tutorial.model;

import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = "Company")
@DiscriminatorValue(value = "2")
@Table(schema = "OLINGO", name = "\"BusinessPartner\"")
public class CompanyEntity extends BusinessPartnerEntity {

    @Basic
    @Column(name = "\"NameLine1\"", length = 250)
    private String nameLine1;

    public String getNameLine1() {
        return nameLine1;
    }

    public void setNameLine1(String nameLine1) {
        this.nameLine1 = nameLine1;
    }

    @Basic
    @Column(name = "\"NameLine2\"", length = 250)
    private String nameLine2;

    public String getNameLine2() {
        return nameLine2;
    }

    public void setNameLine2(String nameLine2) {
        this.nameLine2 = nameLine2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyEntity that = (CompanyEntity) o;

        if (!Objects.equals(nameLine1, that.nameLine1)) return false;
        return Objects.equals(nameLine2, that.nameLine2);
    }

    @Override
    public int hashCode() {
        int result = nameLine1 != null ? nameLine1.hashCode() : 0;
        result = 31 * result + (nameLine2 != null ? nameLine2.hashCode() : 0);
        return result;
    }

}
```
In the example a person shall have a birthday and different property names than a company. So the attributes are in both classes.
In addition, we want to ensure that no one retrieves business partner. To achieve this we make the Business Partner abstract:

```Java
@Inheritance
@DiscriminatorColumn(name = "\"Type\"")
@Entity(name = "BusinessPartner")
@Table(name = "\"BusinessPartner\"", schema = "OLINGO")
public abstract class BusinessPartnerEntity {
	...
```
Like before we have to add the new entities to the persistence.xml and can have a look at the result _http://localhost:8080/Tutorial/Tutorial.svc/$metadata_.
![JPA - OData Mapping](Metadata/Mapping5.png)

Next step: [Tutorial 1.5: Use Complex Types](1-5-UsingComplexTypes.md)