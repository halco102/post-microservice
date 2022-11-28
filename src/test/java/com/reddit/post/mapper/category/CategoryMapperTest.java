package com.reddit.post.mapper.category;

import com.reddit.post.dto.category.CategoryDto;
import com.reddit.post.dto.category.CategoryRequestDto;
import com.reddit.post.model.category.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class CategoryMapperTest {

    private Category category;

    private CategoryDto categoryDto;

    private CategoryRequestDto categoryRequestDto;

    @BeforeEach
    public void beforeEachSetup() {
        category = new Category(1L, "cat1", "img1");
        categoryDto = new CategoryDto(1L, "cat1", "img1");
        categoryRequestDto = new CategoryRequestDto("name", "image");
    }

    @Test
    void fromEntityToCategoryDto() {

        var value = Mappers.getMapper(CategoryMapper.class).fromEntityToCategoryDto(category);

        Assertions.assertEquals(value.getId(), categoryDto.getId());
        Assertions.assertEquals(value.getImageUrl(), categoryDto.getImageUrl());
        Assertions.assertEquals(value.getName(), categoryDto.getName());
    }

    @Test
    void fromCategoryRequestToEntity() {

        var value = Mappers.getMapper(CategoryMapper.class).fromCategoryRequestToEntity(categoryRequestDto);

        Assertions.assertNull(value.getId());
        Assertions.assertEquals(value.getName(), categoryRequestDto.getName());
        Assertions.assertEquals(value.getImageUrl(), categoryRequestDto.getImageUrl());
        Assertions.assertNull(value.getPosts());
    }
}