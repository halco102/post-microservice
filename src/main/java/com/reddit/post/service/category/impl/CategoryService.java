package com.reddit.post.service.category.impl;

import com.reddit.post.dto.category.CategoryDto;
import com.reddit.post.dto.category.CategoryRequestDto;
import com.reddit.post.exception.DuplicateException;
import com.reddit.post.exception.NotFoundException;
import com.reddit.post.mapper.category.CategoryMapper;
import com.reddit.post.model.category.Category;
import com.reddit.post.repository.CategoryRepository;
import com.reddit.post.service.category.ICategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService implements ICategory {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;


    @Override
    public CategoryDto saveCategory(CategoryRequestDto requestDto) {

        var exists = categoryRepository.findCategoryByName(requestDto.getName());

        if (exists.isPresent())
            throw new DuplicateException("The category by name " + requestDto.getName() + " already exists in db");

        var save =  categoryRepository.save(categoryMapper.fromCategoryRequestToEntity(requestDto));

        return categoryMapper.fromEntityToCategoryDto(save);
    }

    @Override
    public Set<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(map -> categoryMapper.fromEntityToCategoryDto(map)).collect(Collectors.toSet());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        var findById = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("The category does not exist"));
        return categoryMapper.fromEntityToCategoryDto(findById);
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        var findCategory = categoryRepository.findCategoryByName(name);

        if (findCategory.isEmpty())
            throw new NotFoundException("Category was not found");

        return categoryMapper.fromEntityToCategoryDto(findCategory.get());
    }

    @Override
    public void deleteCategoryById(Long id) {
        var findById = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("The category does not exist"));

        log.info("Delete category");
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryEntityById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("The category does not exist"));
    }
}
