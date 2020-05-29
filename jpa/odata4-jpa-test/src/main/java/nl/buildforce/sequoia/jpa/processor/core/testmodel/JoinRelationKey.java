package nl.buildforce.sequoia.jpa.processor.core.testmodel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class JoinRelationKey implements Serializable {

  /**
   *

   */
  private static final long serialVersionUID = 5206755977104102088L;

  @Column(name = "\"SourceID\"")
  private Integer sourceID;

  @Column(name = "\"TargetID\"")
  private Integer targetID;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((sourceID == null) ? 0 : sourceID.hashCode());
    result = prime * result + ((targetID == null) ? 0 : targetID.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    JoinRelationKey other = (JoinRelationKey) obj;
    if (sourceID == null) {
      if (other.sourceID != null) return false;
    } else if (!sourceID.equals(other.sourceID)) return false;
    if (targetID == null) {
        return other.targetID == null;
    } else return targetID.equals(other.targetID);
  }

}