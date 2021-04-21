package com.everoad.blog.backend.domain.category;

import com.everoad.blog.backend.domain.base.BaseEntity;
import com.everoad.blog.backend.dto.category.CategorySaveDto;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import javax.persistence.*;

@ToString
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

  @Column(name = "display", nullable = false)
  private Boolean display;

  @Builder
  public Category(String name, Boolean display) {
    Assert.hasText(name, "name is not empty");
    Assert.notNull(display, "display is not null");
    this.name = name;
    this.display = display;
  }

  public void update(CategorySaveDto saveDto) {
    this.name = saveDto.getName();
    this.display = saveDto.getDisplay();
  }

}
