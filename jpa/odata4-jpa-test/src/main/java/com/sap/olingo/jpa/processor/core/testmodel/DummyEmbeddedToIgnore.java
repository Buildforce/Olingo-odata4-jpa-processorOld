package com.sap.olingo.jpa.processor.core.testmodel;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import java.sql.Blob;
import java.sql.Clob;

@EdmIgnore
@Embeddable
public class DummyEmbeddedToIgnore {

  @Lob
  @Column(name = "\"Command\"")
  @Basic(fetch = FetchType.LAZY)
  private Clob command;

  @Lob
  @Column(name = "\"LargeBytes\"")
  @Basic(fetch = FetchType.LAZY)
  private Blob largeBytes;
}