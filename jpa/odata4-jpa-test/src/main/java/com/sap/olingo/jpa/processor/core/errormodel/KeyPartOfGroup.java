/**
 *
 */
package com.sap.olingo.jpa.processor.core.errormodel;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmVisibleFor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

/**
 * @author Oliver Grande
 * Created: 29.06.2019
 *
 */
@Entity
public class KeyPartOfGroup {
  @Id
  @EdmVisibleFor("Person")
  @Column(name = "\"ID\"")
  protected String iD;

  @Version
  @Column(name = "\"ETag\"", nullable = false)
  protected long eTag;

}