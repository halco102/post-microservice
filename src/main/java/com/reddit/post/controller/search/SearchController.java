package com.reddit.post.controller.search;

import com.reddit.post.dto.UserDto;
import com.reddit.post.dto.post.PostDto;
import com.reddit.post.service.search.SearchGeneric;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchGeneric<UserDto> searchUsers;

    private final SearchGeneric<PostDto> searchPosts;


    @GetMapping("/user")
    public ResponseEntity<?> searchUserByUsername(@RequestParam String name) {
        return new ResponseEntity<>(searchUsers.search(name), HttpStatus.OK);
    }

    @GetMapping("/post")
    public ResponseEntity<?> searchPostByTitle(@RequestParam String title) {
        return new ResponseEntity<>(searchPosts.search(title), HttpStatus.OK);
    }


}
