package com.everoad.blog.backend.dto.category;

import com.everoad.blog.backend.domain.category.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategorySaveDto {

  private String name;
  private Boolean display;

  @Builder
  public CategorySaveDto(String name, Boolean display) {
    this.name = name;
    this.display = display;
  }

  public Category toEntity() {
    return Category.builder()
        .name(name)
        .display(display)
        .build();
  }

}
