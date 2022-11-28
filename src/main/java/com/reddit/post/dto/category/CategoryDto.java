package com.reddit.post.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    private String name;

    private String imageUrl;

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
