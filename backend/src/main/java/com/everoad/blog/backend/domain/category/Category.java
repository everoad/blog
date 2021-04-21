package com.everoad.blog.backend.domain.category;

import com.everoad.blog.backend.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Getter
@Audited(withModifiedFlag = true)
@AuditOverride(forClass = BaseEntity.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "parent_id", nullable = false)
  private Integer parentId;

  @Builder
  public Category(String name, Integer parentId) {
    this.name = name;
    this.parentId = parentId;
  }

}
