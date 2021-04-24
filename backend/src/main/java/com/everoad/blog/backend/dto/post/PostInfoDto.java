package com.everoad.blog.backend.dto.post;

import com.everoad.blog.backend.domain.post.Post;
import com.everoad.blog.backend.domain.post.PostFile;
import com.everoad.blog.backend.dto.member.MemberInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostInfoDto {

  private Long id;
  private String title;
  private String description;
  private String createdBy;
  private Integer categoryId;
  private String categoryName;
  private LocalDateTime createdTime;
  private Integer viewCount;
  private Boolean display;
  private PostFileDto file;

  @Builder
  public PostInfoDto(Long id, String title, String description, String createdBy, LocalDateTime createdTime,
                     Integer viewCount, Integer categoryId, String categoryName, Boolean display, PostFile file) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.createdBy = createdBy;
    this.createdTime = createdTime;
    this.viewCount = viewCount;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
    this.display = display;
    if (file != null) {
      this.file = PostFileDto.create(file);
    }
  }

  public static PostInfoDto create(Post post) {
    return PostInfoDto.builder()
        .id(post.getId())
        .title(post.getTitle())
        .description(post.getDescription())
        .createdBy(post.getCreatedBy())
        .createdTime(post.getCreatedTime())
        .viewCount(post.getViewCount())
        .categoryId(post.getCategory().getId())
        .categoryName(post.getCategory().getName())
        .display(post.getDisplay())
        .file(post.getFile())
        .build();
  }

}
