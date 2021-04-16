package com.everoad.blog.backend.domain.base;

import lombok.Getter;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseTimeEntity {

  @NotAudited
  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;

  @NotAudited
  @LastModifiedDate
  private LocalDateTime modifiedTime;

}
