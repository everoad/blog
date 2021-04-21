package com.everoad.blog.backend.dto.category;

import com.everoad.blog.backend.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class CategoryInfoDto {

  private Integer id;
  private String name;
  private Integer count;
  private Boolean display;

  @Builder
  public CategoryInfoDto(Integer id, String name, Integer count, Boolean display) {
    this.id = id;
    this.name = name;
    this.count = count;
    this.display = display;
  }

  public static CategoryInfoDto create(Category category) {
    return CategoryInfoDto.builder()
        .id(category.getId())
        .name(category.getName())
        .display(category.getDisplay())
        .build();
  }

  public static CategoryInfoDto create(Category category, int count) {
    return CategoryInfoDto.builder()
        .id(category.getId())
        .name(category.getName())
        .count(count)
        .build();
  }
}
