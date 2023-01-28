package com.reddit.post.service.post;

import com.reddit.post.dto.likedislike.LikeDislikeDto;
import com.reddit.post.dto.post.PostDto;

import java.util.List;

public interface ILikeOrDislikePost {

    PostDto likeOrDislikePost(boolean likeOrDislike, Long postId, String token);

    List<LikeDislikeDto> likeDislikePostByUser(Long userId);
}
