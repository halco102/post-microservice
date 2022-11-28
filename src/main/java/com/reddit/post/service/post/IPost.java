package com.reddit.post.service.post;

import com.reddit.post.dto.post.PostDto;
import com.reddit.post.dto.post.request.PostRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface IPost {

    List<PostDto> getAllPosts();

    PostDto getPostByPostId(Long id);

    PostDto savePost(PostRequestDto requestDto, MultipartFile multipartFile);

    Set<PostDto> getAllPostsByUserId(Long id);

}
