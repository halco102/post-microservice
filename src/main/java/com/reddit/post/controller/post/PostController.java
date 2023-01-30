package com.reddit.post.controller.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reddit.post.dto.post.request.EditPostRequest;
import com.reddit.post.dto.post.request.PostRequestDto;
import com.reddit.post.security.JwtTokenUtil;
import com.reddit.post.service.post.ILikeOrDislikePost;
import com.reddit.post.service.post.IPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final IPost iPost;

    private final ILikeOrDislikePost likeOrDislikePost;

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        return new ResponseEntity<>(iPost.getAllPosts(), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = {  MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> savePost(@RequestPart("requestDto") String requestDto,
                                      @RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                      HttpServletRequest servletRequest) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        PostRequestDto postRequestDto = objectMapper.readValue(requestDto, PostRequestDto.class);

        return new ResponseEntity<>(iPost.savePost(postRequestDto, multipartFile, JwtTokenUtil.parseJwt(servletRequest)), HttpStatus.OK);
    }

    /*    public ResponseEntity<?> savePost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return new ResponseEntity<>(iPost.savePost(requestDto, null, JwtTokenUtil.parseJwt(request)), HttpStatus.OK);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<?> getPotsById(@PathVariable Long id) {
        return new ResponseEntity<>(iPost.getPostByPostId(id), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllPostsByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(iPost.getAllPostsByUserId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editPostById(@PathVariable Long id, @RequestBody @Valid EditPostRequest request, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(iPost.editPostById(id, request, JwtTokenUtil.parseJwt(httpServletRequest)), HttpStatus.OK);
    }

    @PostMapping("/like-dislike/{id}")
    public ResponseEntity<?> likeDislikePost(@PathVariable(name = "id") Long postId, @RequestParam boolean isLike, HttpServletRequest request) {
        return new ResponseEntity<>(likeOrDislikePost.likeOrDislikePost(isLike, postId, JwtTokenUtil.parseJwt(request)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable Long id, HttpServletRequest req) {
        iPost.deletePostById(id, JwtTokenUtil.parseJwt(req));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/like-dislike/user/{id}")
    public ResponseEntity<?> getAllPostLikeDislikeFromUser(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(likeOrDislikePost.likeDislikePostByUser(userId), HttpStatus.OK);
    }

}
