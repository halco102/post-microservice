package com.reddit.post.feign.controller;

import message.PostedBy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@FeignClient(name = "userClient", url = "${USER_MICROSERVICE}")
public interface UserClient {


    @GetMapping("/postedBy/{id}")
    PostedBy getPostedByDtoByUserId(@PathVariable("id") Long id);

    @GetMapping("/current")
    PostedBy getCurrentlyLoggedUser();

    @GetMapping("/security/find-by-username")
    PostedBy findUserByUsername(@RequestParam("username") String username);

    @GetMapping("/like-dislike/{id}/following")
    Set<PostedBy> getFollowingFromUserById(@PathVariable("id") Long userId);

    @GetMapping("/like-dislike/{id}/followers")
    Set<PostedBy> getFollowersFromUserById(@PathVariable("id") Long userId);

    @PostMapping("/like-dislike/follow-user/{id}")
    Set<PostedBy> followUnfollowUser(@PathVariable("id") Long followingId, @RequestHeader("Authorization") String jwt);
}
