package com.reddit.post.service.post;

import com.reddit.post.dto.post.PostDto;
import com.reddit.post.dto.post.request.EditPostRequest;
import com.reddit.post.dto.post.request.PostRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface IPost {

    List<PostDto> getAllPosts();

    PostDto getPostByPostId(Long id);

    PostDto savePost(PostRequestDto requestDto, MultipartFile multipartFile, String jwt);

    Set<PostDto> getAllPostsByUserId(Long id);

    void deletePostById(Long id, String jwt);

    PostDto editPostById(Long id, EditPostRequest request, String jwt);

    List<PostDto> getAllPostsByCategoryName(String categoryName);

}
