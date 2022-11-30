package com.reddit.post.controller.post;

import com.reddit.post.dto.post.PostDto;
import com.reddit.post.dto.post.request.EditPostRequest;
import com.reddit.post.dto.post.request.PostRequestDto;
import com.reddit.post.service.post.IPost;
import com.reddit.post.service.search.SearchGeneric;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final IPost iPost;

    private final SearchGeneric<PostDto> searchPosts;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> editPostById(@PathVariable Long id, @RequestBody @Valid EditPostRequest request) {
        return new ResponseEntity<>(iPost.editPostById(id, request), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPosts(@RequestParam String name) {
        return new ResponseEntity<>(searchPosts.search(name), HttpStatus.OK);
    }

}
