package com.reddit.post.dto.likedislike;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDislikeDto {

    private Long postId;

    private Long userId;

    private boolean likeOrDislike;

}
