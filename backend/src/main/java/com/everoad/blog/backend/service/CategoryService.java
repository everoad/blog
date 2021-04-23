package com.everoad.blog.backend.service;

import com.everoad.blog.backend.domain.category.Category;
import com.everoad.blog.backend.domain.category.CategoryRepository;
import com.everoad.blog.backend.domain.post.PostRepository;
import com.everoad.blog.backend.dto.category.CategoryCountOnly;
import com.everoad.blog.backend.dto.category.CategoryInfoDto;
import com.everoad.blog.backend.dto.category.CategorySaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final PostRepository postRepository;


  public List<CategoryInfoDto> selectCategoryListWithPostCount() {
    List<Category> categoryList = categoryRepository.findAll();
    Map<Integer, Category> categoryMap = categoryList.stream()
        .collect(Collectors.toMap(Category::getId, entity -> entity));

    List<CategoryCountOnly> countList = categoryRepository.findCountAllById(categoryMap.keySet());
    return countList.stream()
        .map(one -> CategoryInfoDto.create(categoryMap.get(one.getId()), one.getCount()))
        .collect(Collectors.toList());
  }


  public List<CategoryInfoDto> selectCategoryList() {
    List<Category> categoryList = categoryRepository.findAll();
    return categoryList.stream()
        .map(CategoryInfoDto::create)
        .collect(Collectors.toList());
  }


  public CategoryInfoDto selectCategory(Integer categoryId) {
    Category category = queryCategory(categoryId);
    return CategoryInfoDto.create(category);
  }


  @Transactional
  public void insertCategory(CategorySaveDto saveDto) {
    Category category = saveDto.toEntity();
    categoryRepository.save(category);
  }


  @Transactional
  public void updateCategory(Integer categoryId, CategorySaveDto saveDto) {
    Category category = queryCategory(categoryId);
    category.update(saveDto);
  }


  @Transactional
  public void deleteCategory(Integer categoryId) {
    Category category = queryCategory(categoryId);
    if (postRepository.existsByCategoryId(categoryId)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "카테고리 하위 게시글들이 존재합니다.");
    }
    categoryRepository.delete(category);
  }


  private Category queryCategory(Integer categoryId) {
    return categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리 입니다."));
  }


}
