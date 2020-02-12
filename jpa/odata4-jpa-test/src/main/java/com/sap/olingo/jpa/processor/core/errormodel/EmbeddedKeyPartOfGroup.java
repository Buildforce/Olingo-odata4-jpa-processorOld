/**
 *

 */
package com.sap.olingo.jpa.processor.core.errormodel;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmVisibleFor;
import com.sap.olingo.jpa.processor.core.testmodel.CountryKey;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

/**
 * @author Oliver Grande
 * Created: 29.06.2019
 *
 */
@Entity
public class EmbeddedKeyPartOfGroup {

  @EdmVisibleFor("Person")
  @EmbeddedId
  private CountryKey key;

  @Version
  @Column(name = "\"ETag\"", nullable = false)
  protected long eTag;

}