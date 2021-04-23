package com.everoad.blog.backend.dto.post;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostListDto {

  private Long id;
  private String title;
  private String description;
  private Integer viewCount;
  private LocalDateTime createdTime;
  private Integer categoryId;

  @QueryProjection
  public PostListDto(Long id, String title, String description, Integer viewCount, LocalDateTime createdTime, Integer categoryId) {
    this.id = id;
    this.title = title;
    this.viewCount = viewCount;
    this.description = description;
    this.createdTime = createdTime;
    this.categoryId = categoryId;
  }

}
