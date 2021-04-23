package com.everoad.blog.backend.controller;

import com.everoad.blog.backend.core.dto.ApiResponse;
import com.everoad.blog.backend.core.lib.Const;
import com.everoad.blog.backend.dto.post.PostInfoDto;
import com.everoad.blog.backend.dto.post.PostListDto;
import com.everoad.blog.backend.dto.post.PostSaveDto;
import com.everoad.blog.backend.dto.post.PostSearchDto;
import com.everoad.blog.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/posts", produces = Const.APPLICATION_JSON_VALUE)
public class PostController {

  private final PostService postService;

  @GetMapping
  public ApiResponse<Slice<PostListDto>> getPostList(PostSearchDto searchDto, Pageable pageable) {
    Slice<PostListDto> slice = postService.selectPostList(searchDto, pageable);
    return new ApiResponse<>(slice);
  }



  @GetMapping("/{postId}")
  public ApiResponse<PostInfoDto> getPost(@PathVariable Long postId) {
    PostInfoDto dto = postService.selectPost(postId);
    return new ApiResponse<>(dto);
  }

  @PostMapping
  public ApiResponse<Long> addPost(@Valid @RequestBody PostSaveDto saveDto) {
    Long postId = postService.insertPost(saveDto);
    return new ApiResponse<>(postId);
  }

  @PutMapping("/{postId}")
  public ApiResponse<?> editPost(@PathVariable Long postId, @Valid @RequestBody PostSaveDto saveDto) {
    postService.updatePost(postId, saveDto);
    return new ApiResponse<>();
  }

  @DeleteMapping("/{postId}")
  public ApiResponse<?> removePost(@PathVariable Long postId) {
    postService.deletePost(postId);
    return new ApiResponse<>();
  }

}
