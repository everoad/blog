package com.everoad.blog.backend.dto.post;

import com.everoad.blog.backend.domain.post.Post;
import com.everoad.blog.backend.domain.post.PostFile;
import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFileDto {

  private Integer id;
  private String originalName;
  private String name;
  private String type;
  private Long size;

  @Builder
  public PostFileDto(Integer id, String originalName, String name, String type, Long size) {
    this.id = id;
    this.originalName = originalName;
    this.name = name;
    this.type = type;
    this.size = size;
  }

  public PostFile toEntity(Post post) {
    return PostFile.builder()
        .originalName(originalName)
        .name(name)
        .type(type)
        .size(size)
        .post(post)
        .build();
  }

  public static PostFileDto create(PostFile file) {
    return PostFileDto.builder()
        .id(file.getId())
        .originalName(file.getOriginalName())
        .name(file.getName())
        .size(file.getSize())
        .type(file.getType())
        .build();
  }
}
