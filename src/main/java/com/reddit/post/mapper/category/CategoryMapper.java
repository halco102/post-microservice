package com.reddit.post.mapper.category;

import com.reddit.post.dto.category.CategoryDto;
import com.reddit.post.dto.category.CategoryRequestDto;
import com.reddit.post.model.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto fromEntityToCategoryDto(Category category);

    Category fromCategoryRequestToEntity(CategoryRequestDto requestDto);


}
