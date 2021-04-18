package com.everoad.blog.backend.dto.post;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostListDto {

  private Long id;
  private String title;
  private Integer viewCount;
  private LocalDateTime createdTime;

  @QueryProjection
  public PostListDto(Long id, String title, Integer viewCount, LocalDateTime createdTime) {
    this.id = id;
    this.title = title;
    this.viewCount = viewCount;
    this.createdTime = createdTime;
  }

}
