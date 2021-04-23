package com.everoad.blog.backend.service;

import com.everoad.blog.backend.dto.post.PostInfoDto;
import com.everoad.blog.backend.dto.post.PostSaveDto;
import com.everoad.blog.backend.util.GenerateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"test"})
@SpringBootTest
class PostServiceTest {

  @Autowired
  PostService postService;

  @WithUserDetails("tester")
  @Test
  public void 게시글_등록() throws Exception {
    //given

  }
}