package com.reddit.post.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotNull
    @NotBlank
    private String imageUrl;

}
