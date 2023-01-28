package com.reddit.post.service.post.impl;

import com.reddit.post.dto.likedislike.LikeDislikeDto;
import com.reddit.post.dto.post.PostDto;
import com.reddit.post.exception.BadRequestException;
import com.reddit.post.exception.NotFoundException;
import com.reddit.post.feign.controller.UserClient;
import com.reddit.post.mapper.post.PostMapper;
import com.reddit.post.model.likes_dislikes.EmbeddedLikeAndDislike;
import com.reddit.post.model.likes_dislikes.LikeAndDislike;
import com.reddit.post.model.post.Post;
import com.reddit.post.model.user.User;
import com.reddit.post.repository.PostRepository;
import com.reddit.post.security.JwtTokenUtil;
import com.reddit.post.service.post.ILikeOrDislikePost;
import com.reddit.post.service.user.IUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeDislikePostService implements ILikeOrDislikePost {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final UserClient userClient;

    private final IUser iUser;

    private final JwtTokenUtil jwtTokenUtil;


    @Override
    public PostDto likeOrDislikePost(boolean likeOrDislike, Long id, String token) {

        var fetchPostById = postRepository.findById(id).orElseThrow(() -> new NotFoundException("The post was not found"));

        var currentUser = userClient.findUserByUsername(jwtTokenUtil.getUsernameByJwt(token));

        var fetchUserFromPostDb = iUser.getUserEntityById(currentUser.getId());

        if (currentUser == null)
            throw new NotFoundException("The user does not exist");
        else if (currentUser.getId() != fetchUserFromPostDb.getId())
            throw new BadRequestException("User do not seem to be same internal error");

        return getPostToAddLikeDislike(fetchPostById, fetchUserFromPostDb, likeOrDislike);
    }

    private PostDto getPostToAddLikeDislike(Post post, User user, boolean likeDislike) {

        var temp = post.getPostLikeDislike().stream().filter(filter -> {
            if (filter.getUser().getId() == user.getId() && filter.getPost().getId() == post.getId())
                return true;
            else
                return false;
        }).findAny();

        if (temp.isEmpty())
            return addFirstLikeDislike(post, user, likeDislike);
        else {
            if (temp.get().isLike() == likeDislike)
                return deleteIfSameLikeDislikeAction(post, user.getId(), likeDislike);
            else
                return changeLikeDislike(post, user.getId(), likeDislike);
        }

    }

    private PostDto addFirstLikeDislike(Post post, User user, boolean likeDislike) {

        post.getPostLikeDislike().add(
                new LikeAndDislike(
                        new EmbeddedLikeAndDislike(user.getId(), post.getId()),
                        user,
                        post,
                        likeDislike
                )
        );

        var savePost = postRepository.save(post);

        return postMapper.fromEntityToPostDto(savePost);
    }

    private PostDto deleteIfSameLikeDislikeAction(Post post, Long userId, boolean likeDislike) {
        var findLikeDislikeEntity = post.getPostLikeDislike().stream().filter(item -> item.getUser().getId() == userId).findFirst();

        if (findLikeDislikeEntity.isEmpty())
            return null;

        post.getPostLikeDislike().removeIf(exists -> {
            if (exists.getUser().getId() == userId && exists.getPost().getId() == post.getId() && exists.isLike() == likeDislike)
                return true;
            else
                return false;
        });


        var savePost = postRepository.save(post);

        return postMapper.fromEntityToPostDto(savePost);
    }

    private PostDto changeLikeDislike(Post post, Long userId, boolean likeDislike) {
        var findLikeDislikeEntity = post.getPostLikeDislike().stream().filter(item -> item.getUser().getId() == userId).findFirst();

        findLikeDislikeEntity.get().setLike(likeDislike);

        var savePost = postRepository.save(post);

        return postMapper.fromEntityToPostDto(savePost);
    }

    @Override
    public List<LikeDislikeDto> likeDislikePostByUser(Long userId) {
        var test = postRepository.getAllLikedDislikedPostFromUserId(userId).orElseGet(() -> new ArrayList<>());
        return postRepository.getAllLikedDislikedPostFromUserId(userId).orElseGet(() -> new ArrayList<>());
    }
}
