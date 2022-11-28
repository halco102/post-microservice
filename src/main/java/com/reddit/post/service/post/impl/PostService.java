package com.reddit.post.service.post.impl;

import com.reddit.post.dto.post.PostDto;
import com.reddit.post.dto.post.request.PostRequestDto;
import com.reddit.post.exception.BadRequestException;
import com.reddit.post.exception.NotFoundException;
import com.reddit.post.feign.controller.UserClient;
import com.reddit.post.mapper.post.PostMapper;
import com.reddit.post.mapper.user.UserMapper;
import com.reddit.post.model.category.Category;
import com.reddit.post.repository.PostRepository;
import com.reddit.post.service.category.impl.CategoryService;
import com.reddit.post.service.cloudinary.ICloudinary;
import com.reddit.post.service.post.IPost;
import com.reddit.post.service.user.IUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService implements IPost {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final UserClient userClient;

    private final ICloudinary cloudinary;

    private final CategoryService categoryService;

    private final UserMapper userMapper;

    private final IUser iUser;


    @Override
    @Transactional
    public PostDto savePost(PostRequestDto requestDto, MultipartFile multipartFile) {

        //feign
        var checkIfUserExists = userClient.getPostedByDtoByUserId(requestDto.getUserId());

        var user = iUser.saveUser(userMapper.postedByToEntity(checkIfUserExists));
        Set<Category> categories = new HashSet<>();

        if (checkIfUserExists == null) {
            throw new  NotFoundException("The user was not found");
        }

        if ((requestDto.getImageUrl() == null || requestDto.getImageUrl().isBlank()) && multipartFile == null)
            throw new BadRequestException("Bad request on post save");

        if (multipartFile != null)
            requestDto.setImageUrl(cloudinary.getUrlFromUploadedMedia(multipartFile));

        if (requestDto.getCategoryDtos() == null || requestDto.getCategoryDtos().isEmpty())
            throw new BadRequestException("Categories are empty or null");

        requestDto.getCategoryDtos().stream().forEach(item -> {
                categories.add(categoryService.getCategoryEntityById(item.getId()));
        });

        var toEntity = postMapper.fromPostRequestToEntity(requestDto);

        toEntity.setCreatedAt(LocalDateTime.now());
        toEntity.setCategories(categories);

        toEntity.setUser(user);


        var toSave = postRepository.save(toEntity);

        var toDto = postMapper.fromEntityToPostDto(toSave);


        return toDto;
    }

    @Override
    public List<PostDto> getAllPosts() {
        var fetchAllPosts = postRepository.findAll();
        return fetchAllPosts.stream().map(map -> postMapper.fromEntityToPostDto(map)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostByPostId(Long id) {
        var fetchPostById = postRepository.findById(id);

        if (fetchPostById.isEmpty()) {
            throw new NotFoundException("Post does not exist");
        }

        var toDto = postMapper.fromEntityToPostDto(fetchPostById.get());

        return toDto;
    }

    @Override
    public Set<PostDto> getAllPostsByUserId(Long id) {
        var getAllPostsByUserId = postRepository.fetchAllPostsByUserId(id);

        return postMapper.collectionEntityToDtos(getAllPostsByUserId);
    }


}
