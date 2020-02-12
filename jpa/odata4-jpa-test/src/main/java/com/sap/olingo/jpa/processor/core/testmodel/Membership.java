package com.sap.olingo.jpa.processor.core.testmodel;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;

import javax.persistence.*;

@EdmIgnore
@Entity
@Table(schema = "\"OLINGO\"", name = "\"Membership\"")
@IdClass(MembershipKey.class)
public class Membership {
  @Id
  @Column(name = "\"PersonID\"", length = 32)
  private String personID;
  @Id
  @Column(name = "\"TeamID\"", length = 32)
  private String teamID;
}