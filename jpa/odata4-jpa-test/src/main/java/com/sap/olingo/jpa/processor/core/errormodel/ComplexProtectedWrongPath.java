package com.sap.olingo.jpa.processor.core.errormodel;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmProtectedBy;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(schema = "\"OLINGO\"", name = "\"BusinessPartner\"")
public class ComplexProtectedWrongPath {

  @Id
  @Column(name = "\"ID\"")
  protected String iD;

  @Version
  @Column(name = "\"ETag\"", nullable = false)
  protected long eTag;

  @Embedded
  @EdmProtectedBy(name = "UserId", path = "created/wrong")
  private final AdministrativeInformation administrativeInformation = new AdministrativeInformation();

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((iD == null) ? 0 : iD.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    ComplexProtectedWrongPath other = (ComplexProtectedWrongPath) obj;
    if (iD == null) {
        return other.iD == null;
    } else return iD.equals(other.iD);
  }

  public String getiD() {
    return iD;
  }

  public long geteTag() {
    return eTag;
  }

}