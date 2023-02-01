package com.reddit.post.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reddit.post.dto.category.CategoryDto;
import com.reddit.post.dto.likedislike.LikeDislikeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import message.PostedBy;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    private String title;

    private String description;

    private String imageUrl;

/*    private LocalDateTime createdAt;

    private LocalDateTime editedAt;*/

    private boolean allowComment;

    private PostedBy postedBy;

    private Set<CategoryDto> categoryDtos;

    @JsonProperty("postLikedDislike")
    private Set<LikeDislikeDto> postLikedDislike;

    public PostDto(Long id, String title, String description, String imageUrl, PostedBy postedBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.postedBy = postedBy;
    }
}
