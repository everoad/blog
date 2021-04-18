package com.everoad.blog.backend.dto.post;

import com.everoad.blog.backend.core.revision.BaseRevisionDto;
import com.everoad.blog.backend.core.revision.MyRevisionEntity;
import com.everoad.blog.backend.domain.post.Post;
import lombok.Getter;
import org.hibernate.envers.RevisionType;

import java.util.Collection;

@Getter
public class PostRevisionDto extends BaseRevisionDto {

  private final Long postId;
  private final String title;
  private final String description;

  public PostRevisionDto(Post post, MyRevisionEntity entity, RevisionType revisionType, Collection<?> modified) {
    super(entity, revisionType, modified);
    this.postId = post.getId();
    this.title = post.getTitle();
    this.description = post.getDescription();
  }

}
