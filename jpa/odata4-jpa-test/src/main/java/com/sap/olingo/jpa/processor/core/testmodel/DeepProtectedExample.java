package com.sap.olingo.jpa.processor.core.testmodel;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@EdmIgnore
@Entity(name = "ProtectionExample")
@Table(schema = "\"OLINGO\"", name = "\"BusinessPartner\"")
public class DeepProtectedExample {

  @Id
  @Column(name = "\"ID\"")
  private String iD;

  @Version
  @Column(name = "\"ETag\"", nullable = false)
  private long eTag;

  @Column(name = "\"Type\"", length = 1, insertable = false, updatable = false, nullable = false)
  private String type;

  @Embedded
  private AddressDeepTreeProtections postalAddress;

}