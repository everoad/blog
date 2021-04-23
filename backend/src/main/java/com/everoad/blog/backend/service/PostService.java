package com.everoad.blog.backend.service;

import com.everoad.blog.backend.domain.category.Category;
import com.everoad.blog.backend.domain.category.CategoryRepository;
import com.everoad.blog.backend.domain.post.Post;
import com.everoad.blog.backend.domain.post.PostQueryRepository;
import com.everoad.blog.backend.domain.post.PostRepository;
import com.everoad.blog.backend.dto.post.PostInfoDto;
import com.everoad.blog.backend.dto.post.PostListDto;
import com.everoad.blog.backend.dto.post.PostSaveDto;
import com.everoad.blog.backend.dto.post.PostSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

  private final PostQueryRepository postQueryRepository;
  private final PostRepository postRepository;

  private final CategoryRepository categoryRepository;

  public Slice<PostListDto> selectPostList(PostSearchDto searchDto, Pageable pageable) {
    return postQueryRepository.findAll(searchDto, pageable);
  }

  @Transactional
  public PostInfoDto selectPost(Long postId) {
    Post post = queryPost(postId);
    post.addViewCount();
    return PostInfoDto.create(post);
  }

  @Transactional
  public Long insertPost(PostSaveDto saveDto) {
    Category category = queryCategory(saveDto.getCategoryId());
    Post post = saveDto.toEntity(category);
    postRepository.save(post);
    return post.getId();
  }


  @Transactional
  public void updatePost(Long postId, PostSaveDto saveDto) {
    Post post = queryPost(postId);
    post.updateInfo(saveDto);
  }

  @Transactional
  public void deletePost(Long postId) {
    Post post = queryPost(postId);
    postRepository.delete(post);
  }

  public Post queryPost(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  private Category queryCategory(Integer categoryId) {
    return categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리 입니다."));
  }

}
