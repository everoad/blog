package com.everoad.blog.backend.service;

import com.everoad.blog.backend.domain.post.Post;
import com.everoad.blog.backend.domain.post.PostRepository;
import com.everoad.blog.backend.dto.post.PostInfoDto;
import com.everoad.blog.backend.dto.post.PostSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  @Transactional
  public PostInfoDto selectPost(Long postId) {
    Post post = getPost(postId);
    post.addViewCount();
    return PostInfoDto.create(post);
  }

  @Transactional
  public Long insertPost(PostSaveDto saveDto) {
    Post post = saveDto.toEntity();
    postRepository.save(post);
    return post.getId();
  }

  @Transactional
  public void updatePost(Long postId, PostSaveDto saveDto) {
    Post post = getPost(postId);
    post.updateInfo(saveDto);
  }

  @Transactional
  public void deletePost(Long postId) {
    Post post = getPost(postId);
    postRepository.delete(post);
  }

  private Post getPost(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
  }

}
