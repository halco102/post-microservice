package com.reddit.post.dto.post.request;

import com.reddit.post.dto.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditPostRequest {

    @NotNull
    @NotBlank
    private String title;

    private String description;

    private boolean allowComment;

    @NotNull
    @NotEmpty
    private Set<Long> categoryDtos;

}
