package com.reddit.post.service.post.impl;

import com.reddit.post.dto.post.PostDto;
import com.reddit.post.exception.BadRequestException;
import com.reddit.post.exception.NotFoundException;
import com.reddit.post.feign.controller.UserClient;
import com.reddit.post.mapper.post.PostMapper;
import com.reddit.post.repository.PostRepository;
import com.reddit.post.service.post.ILikeOrDislikePost;
import com.reddit.post.service.user.IUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeDislikePostService implements ILikeOrDislikePost {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final UserClient userClient;

    private final IUser iUser;


    @Override
    public PostDto likeOrDislikePost(boolean likeOrDislike, Long id) {

        var fetchPostById = postRepository.findById(id).orElseThrow(() -> new NotFoundException("The post was not found"));

        var currentUser = userClient.getCurrentlyLoggedUser();

        if (currentUser == null)
            throw new NotFoundException("The user does not exist");
        else if (currentUser != iUser.getUserById(id))
            throw new BadRequestException("User do not seem to be same internal error");


        return null;
    }



}
