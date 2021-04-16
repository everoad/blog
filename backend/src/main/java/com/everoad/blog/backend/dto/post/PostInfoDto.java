package com.everoad.blog.backend.dto.post;

import com.everoad.blog.backend.domain.post.Post;
import com.everoad.blog.backend.dto.member.MemberInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostInfoDto {

  private Long id;
  private String title;
  private String description;
  private MemberInfoDto createdBy;
  private LocalDateTime createdTime;
  private Integer viewCount;

  @Builder
  public PostInfoDto(Long id, String title, String description, MemberInfoDto createdBy, LocalDateTime createdTime, Integer viewCount) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.createdBy = createdBy;
    this.createdTime = createdTime;
    this.viewCount = viewCount;
  }

  public static PostInfoDto create(Post post) {
    return PostInfoDto.builder()
        .id(post.getId())
        .title(post.getTitle())
        .description(post.getDescription())
        .createdBy(MemberInfoDto.create(post.getCreatedBy()))
        .createdTime(post.getCreatedTime())
        .build();
  }

}
