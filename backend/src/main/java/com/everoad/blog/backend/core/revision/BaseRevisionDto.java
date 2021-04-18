package com.everoad.blog.backend.core.revision;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.RevisionType;

import java.util.Collection;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseRevisionDto {

  private Long rev;
  private Long timestamp;
  private String type;
  private String revCreatedBy;
  private Collection<?> modified;

  public BaseRevisionDto(MyRevisionEntity entity, RevisionType revisionType, Collection<?> modified) {
    this.rev = entity.getRev();
    this.timestamp = entity.getTimestamp();
    this.revCreatedBy = entity.getRevCreatedBy();
    this.type = revisionType.name();
    this.modified = modified;
  }

  protected String isChanged(String name) {
    return modified.contains(name) ? "CHANGED" : "-";
  }

}
