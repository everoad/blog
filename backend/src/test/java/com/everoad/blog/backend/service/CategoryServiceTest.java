package com.everoad.blog.backend.service;

import com.everoad.blog.backend.dto.category.CategoryInfoDto;
import com.everoad.blog.backend.dto.category.CategorySaveDto;
import com.everoad.blog.backend.util.GenerateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ActiveProfiles({"test"})
@SpringBootTest
class CategoryServiceTest {

  @Autowired
  CategoryService categoryService;

  @WithUserDetails("tester")
  @Test
  public void 카테고리_등록() throws Exception {
  }
}