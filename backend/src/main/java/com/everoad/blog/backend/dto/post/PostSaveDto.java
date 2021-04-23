package com.everoad.blog.backend.dto.post;

import com.everoad.blog.backend.domain.category.Category;
import com.everoad.blog.backend.domain.post.Post;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveDto {

  @NotBlank
  private String title;
  @NotBlank
  private String description;
  @NotNull
  private Boolean display;
  @NotNull
  private Integer categoryId;

  @Builder
  public PostSaveDto(String title, String description, Boolean display, Integer categoryId) {
    this.title = title;
    this.description = description;
    this.display = display;
    this.categoryId = categoryId;
  }

  public Post toEntity(Category category) {
    return Post.builder()
        .title(title)
        .display(display)
        .description(description)
        .category(category)
        .build();
  }
}
