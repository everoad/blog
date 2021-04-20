package com.everoad.blog.backend.core.revision;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "revinfo")
@RevisionEntity
@EntityListeners(AuditingEntityListener.class)
public class MyRevisionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @RevisionNumber
  private Long rev;

  @RevisionTimestamp
  private Long timestamp;

  @CreatedBy
  @Column(name = "rev_created_by", updatable = false)
  private String revCreatedBy;

}
