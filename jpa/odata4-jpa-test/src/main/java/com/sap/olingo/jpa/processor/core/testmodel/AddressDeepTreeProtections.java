package com.sap.olingo.jpa.processor.core.testmodel;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

//Only for Unit Tests
@EdmIgnore
@Embeddable
public class AddressDeepTreeProtections {

  @Column(name = "\"AddressType\"")
  private String type;

  @Embedded
  private InhouseAddressWithThreeProtections inhouseAddress;

}