package com.everoad.blog.backend.dto.category;

import com.everoad.blog.backend.domain.category.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategorySaveDto {

  private Integer id;
  private String name;
  private Boolean display;

  @Builder
  public CategorySaveDto(Integer id, String name, Boolean display) {
    this.id = id;
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
