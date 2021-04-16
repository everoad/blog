package com.everoad.blog.backend.dto.post;

import com.everoad.blog.backend.domain.post.Post;
import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveDto {

  private String title;
  private String description;

  @Builder
  public PostSaveDto(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public Post toEntity() {
    return Post.builder()
        .title(title)
        .description(description)
        .build();
  }
}
