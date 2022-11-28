package com.reddit.post.dto.post.request;

import com.reddit.post.dto.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    @NotBlank
    @NotNull
    private String title;

    @NotNull
    private String text;

    @NotBlank
    private String imageUrl;

    private boolean allowComment;

    @NotNull
    private Long userId;

    @NotNull
    private Set<CategoryDto> categoryDtos;

}