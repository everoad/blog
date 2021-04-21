package com.everoad.blog.backend.domain.category;


import com.everoad.blog.backend.dto.category.CategoryCountOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

  @Query("select c.id as id, count(p.id) as count from Category c " +
      "left join Post p on c.id = p.category.id group by c.id order by c.name asc")
  List<CategoryCountOnly> findCountAllById(Set<Integer> idSet);


  @Modifying
  @Query("delete from Category c where c.id not in :ids")
  void deleteAllByIdNotIn(@Param("ids") Set<Integer> ids);

}
