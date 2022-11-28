package com.reddit.post.repository;

import com.reddit.post.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * from categories where name = :name", nativeQuery = true)
    Optional<Category> findCategoryByName(@Param("name") String name);

}
