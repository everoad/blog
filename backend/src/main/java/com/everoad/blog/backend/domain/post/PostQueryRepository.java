package com.everoad.blog.backend.domain.post;

import com.everoad.blog.backend.core.lib.Querydsl4RepositorySupport;
import com.everoad.blog.backend.dto.post.PostListDto;
import com.everoad.blog.backend.dto.post.PostSearchDto;
import com.everoad.blog.backend.dto.post.QPostListDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import static com.everoad.blog.backend.domain.post.QPost.post;
import static com.everoad.blog.backend.domain.post.QPostFile.postFile;

@Repository
public class PostQueryRepository extends Querydsl4RepositorySupport {

  public PostQueryRepository() {
    super(Post.class);
  }

  public Slice<PostListDto> findAll(PostSearchDto searchDto, Pageable pageable) {
    return applyPaginationForSlice(pageable, query -> query
        .select(
            new QPostListDto(
                post.id,
                post.title,
                post.description,
                post.viewCount,
                post.createdTime,
                post.category.id,
                post.file.name
            )
        )
        .from(post)
        .leftJoin(postFile).on(post.file.id.eq(postFile.id))
        .where(
            post.display.isTrue(),
            keywordLike(searchDto.getKeyword()),
            categoryEq(searchDto.getCategoryId())
        )
        .orderBy(post.createdTime.desc())
    );
  }


  public Slice<PostListDto> findAllWithDisplayIsFalse(PostSearchDto searchDto, Pageable pageable) {
    return applyPaginationForSlice(pageable, query -> query
        .select(
            new QPostListDto(
                post.id,
                post.title,
                post.description,
                post.viewCount,
                post.createdTime,
                post.category.id,
                post.file.name
            )
        )
        .from(post)
        .where(
            post.display.isFalse(),
            keywordLike(searchDto.getKeyword()),
            categoryEq(searchDto.getCategoryId())
        )
        .orderBy(post.createdTime.desc())
    );
  }


  private BooleanExpression keywordLike(String keyword) {
    return StringUtils.hasText(keyword) ? contains(post.title, keyword).or(contains(post.description, keyword)) : null;
  }

  private BooleanExpression categoryEq(Integer categoryId) {
    return categoryId != null ? post.category.id.eq(categoryId) : null;
  }

}
