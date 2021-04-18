package com.everoad.blog.backend.domain.post;

import com.everoad.blog.backend.core.lib.Querydsl4RepositorySupport;
import com.everoad.blog.backend.dto.post.PostListDto;
import com.everoad.blog.backend.dto.post.PostSearchDto;
import com.everoad.blog.backend.dto.post.QPostListDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import static com.everoad.blog.backend.domain.post.QPost.post;

@Repository
public class PostQueryRepository extends Querydsl4RepositorySupport {

  public PostQueryRepository() {
    super(Post.class);
  }

  public Page<PostListDto> findAll(PostSearchDto searchDto, Pageable pageable) {
    return applyPagination(pageable, query -> query
        .select(
            new QPostListDto(
                post.id,
                post.title,
                post.viewCount,
                post.createdTime
            )
        )
        .from(post)
        .where(
            keywordLike(searchDto.getKeyword())
        )
        .orderBy(post.createdTime.desc())
    );
  }

  private BooleanExpression keywordLike(String keyword) {
    return StringUtils.hasText(keyword) ? contains(post.title, keyword).or(contains(post.description, keyword)) : null;
  }

}
