package com.sap.olingo.jpa.processor.core.testmodel;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(schema = "\"OLINGO\"", name = "\"Collections\"")
public class Collection {

  @Id
  @Column(name = "\"ID\"")
  private String iD;

  // Collection as part of complex
  @Embedded
  private CollectionPartOfComplex complex;

  // Collection with nested complex
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(schema = "\"OLINGO\"", name = "\"NestedComplex\"",
      joinColumns = @JoinColumn(name = "\"ID\""))
  private List<CollectionNestedComplex> nested; // Must not be assigned to an ArrayList

  public String getID() {
    return iD;
  }

  public void setID(String iD) {
    this.iD = iD;
  }

  public CollectionPartOfComplex getComplex() {
    return complex;
  }

  public void setComplex(CollectionPartOfComplex complex) {
    this.complex = complex;
  }

  public List<CollectionNestedComplex> getNested() {
    return nested;
  }

  public void setNested(List<CollectionNestedComplex> nested) {
    this.nested = nested;
  }

}