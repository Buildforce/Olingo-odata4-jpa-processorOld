package ${package}.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@IdClass(ValueObjectTemplateKey.class)
@Entity(name = "${value-object-table}")
@Table(schema = "\"${schema}\"", name = "\"${value-object-table}\"")
public class ValueObjectTemplate {
  @Id
  @Column(name = "\"ID\"", length = 32)
  private String id;

  @Id
  @Column(name = "\"Entity\"", length = 32)
  private String entityId;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "\"Entity\"", insertable = false, updatable = false)
  private EntityTemplate entity;

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(final String entityId) {
    this.entityId = entityId;
  }

  public EntityTemplate getEntity() {
    return entity;
  }

  public void setEntity(final EntityTemplate entity) {
    this.entityId = entity.getId();
    this.entity = entity;
  }

}