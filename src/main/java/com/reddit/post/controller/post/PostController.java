package com.reddit.post.controller.post;

import com.reddit.post.dto.post.request.PostRequestDto;
import com.reddit.post.service.post.IPost;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final IPost iPost;

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        return new ResponseEntity<>(iPost.getAllPosts(), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = {  MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> savePost(@RequestBody PostRequestDto requestDto) {
        return new ResponseEntity<>(iPost.savePost(requestDto, null), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPotsById(@PathVariable Long id) {
        return new ResponseEntity<>(iPost.getPostByPostId(id), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllPostsByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(iPost.getAllPostsByUserId(id), HttpStatus.OK);
    }


}
