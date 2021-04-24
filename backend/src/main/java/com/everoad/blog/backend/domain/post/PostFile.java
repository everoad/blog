package com.everoad.blog.backend.domain.post;

import com.everoad.blog.backend.domain.base.BaseTimeEntity;
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
@AuditOverride(forClass = BaseTimeEntity.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFile extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_file_id")
  private Integer id;

  private String originalName;

  private String name;

  private Long size;

  private String type;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @Builder
  public PostFile(String originalName, String name, Long size, String type, Post post) {
    this.originalName = originalName;
    this.name = name;
    this.size = size;
    this.type = type;
    this.post = post;
  }

}
