package com.reddit.post.service.category;

import com.reddit.post.dto.category.CategoryDto;
import com.reddit.post.dto.category.CategoryRequestDto;
import com.reddit.post.model.category.Category;

import java.util.Set;

public interface ICategory {

    CategoryDto saveCategory(CategoryRequestDto requestDto);

    Set<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(Long id);

    CategoryDto getCategoryByName(String name);

    void deleteCategoryById(Long id);

    Category getCategoryEntityById(Long id);
}
