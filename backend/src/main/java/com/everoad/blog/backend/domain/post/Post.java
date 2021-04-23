package com.everoad.blog.backend.domain.post;

import com.everoad.blog.backend.domain.base.BaseEntity;
import com.everoad.blog.backend.domain.category.Category;
import com.everoad.blog.backend.dto.post.PostSaveDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@Audited(withModifiedFlag = true)
@AuditOverride(forClass = BaseEntity.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private Long id;

  @Column(name = "title", nullable = false, length = 100)
  private String title;

  @Lob
  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "display", nullable = false)
  private Boolean display;

  @NotAudited
  @Column(name = "view_count", nullable = false)
  private Integer viewCount;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @Builder
  public Post(String title, String description, Boolean display, Category category) {
    Assert.hasText(title, "title is not empty");
    Assert.hasText(description, "description is not empty");
    Assert.notNull(display, "display is not null");
    this.title = title;
    this.description = description;
    this.display = display;
    this.category = category;
    this.viewCount = 0;
  }

  public void updateInfo(PostSaveDto saveDto, Category category) {
    this.title = saveDto.getTitle();
    this.description = saveDto.getDescription();
    this.category = category;
  }

  public void addViewCount() {
    this.viewCount = this.viewCount + 1;
  }

}
