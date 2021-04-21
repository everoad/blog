package com.everoad.blog.backend.util;

import com.everoad.blog.backend.dto.category.CategorySaveDto;
import com.everoad.blog.backend.dto.member.MemberSaveDto;
import com.everoad.blog.backend.dto.post.PostInfoDto;
import com.everoad.blog.backend.dto.post.PostSaveDto;

public class GenerateUtils {

  public static MemberSaveDto generateMemberDto(int i) {
    return MemberSaveDto.builder()
        .id("tester" + i)
        .name("name" + i)
        .password("1234")
        .build();
  }

  public static PostSaveDto generatePostDto(int i) {
    return PostSaveDto.builder()
        .title("title" + i)
        .description("description" + i)
        .build();
  }

  public static CategorySaveDto generateCategoryDto(int i) {
    return CategorySaveDto.builder()
        .name("category name" + i)
        .display(true)
        .build();

  }
}
