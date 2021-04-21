package com.everoad.blog.backend.service;

import com.everoad.blog.backend.domain.category.Category;
import com.everoad.blog.backend.domain.category.CategoryRepository;
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


  public List<CategoryInfoDto> selectCategoryListWithPostCount() {
    List<Category> categoryList = categoryRepository.findAll();
    Map<Integer, Category> categoryMap = categoryList.stream()
        .collect(Collectors.toMap(Category::getId, entity -> entity));

    List<CategoryCountOnly> countList = categoryRepository.findCountAllById(categoryMap.keySet());
    return countList.stream()
        .map(one -> CategoryInfoDto.create(categoryMap.get(one.getId()), one.getCount()))
        .collect(Collectors.toList());
  }

  public CategoryInfoDto selectCategory(Integer categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리 입니다."));
    return CategoryInfoDto.create(category);
  }


  @Transactional
  public void updateCategoryList(List<CategorySaveDto> saveDtoList) {
    // 삭제
    Set<Integer> idSet = saveDtoList.stream()
        .map(CategorySaveDto::getId)
        .filter(Objects::nonNull).collect(Collectors.toSet());
    categoryRepository.deleteAllByIdNotIn(idSet);
    categoryRepository.flush();
    
    // 추가, 수정 그룹
    Map<Boolean, List<CategorySaveDto>> groupMap = saveDtoList.stream()
        .collect(Collectors.groupingBy(dto -> dto.getId() != null));

    // 추가
    if (groupMap.containsKey(false)) {
      List<Category> saveList = groupMap.get(false).stream()
          .map(CategorySaveDto::toEntity).collect(Collectors.toList());
      categoryRepository.saveAll(saveList);
    }

    // 수정
    if (groupMap.containsKey(true)) {
      List<CategorySaveDto> updateList = groupMap.get(true);
      Map<Integer, CategorySaveDto> map = updateList.stream()
          .collect(Collectors.toMap(CategorySaveDto::getId, dto -> dto));
      List<Category> categoryList = categoryRepository.findAllById(map.keySet());
      for (Category category : categoryList) {
        CategorySaveDto categorySaveDto = map.get(category.getId());
        category.update(categorySaveDto);
      }
    }
  }
}
