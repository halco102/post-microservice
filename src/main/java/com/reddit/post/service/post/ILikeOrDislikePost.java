package com.reddit.post.service.post;

import com.reddit.post.dto.post.PostDto;

public interface ILikeOrDislikePost {

    PostDto likeOrDislikePost(boolean likeOrDislike, Long postId);

}
