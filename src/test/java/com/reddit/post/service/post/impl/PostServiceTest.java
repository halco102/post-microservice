package com.reddit.post.service.post.impl;

import com.reddit.post.dto.category.CategoryDto;
import com.reddit.post.dto.post.PostDto;
import com.reddit.post.dto.post.request.PostRequestDto;
import com.reddit.post.exception.BadRequestException;
import com.reddit.post.exception.NotFoundException;
import com.reddit.post.feign.controller.UserClient;
import com.reddit.post.mapper.post.PostMapper;
import com.reddit.post.mapper.user.UserMapper;
import com.reddit.post.model.category.Category;
import com.reddit.post.model.post.Post;
import com.reddit.post.model.user.User;
import com.reddit.post.repository.PostRepository;
import com.reddit.post.service.category.impl.CategoryService;
import com.reddit.post.service.cloudinary.ICloudinary;
import com.reddit.post.service.user.IUser;
import message.PostedBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PostServiceTest {
    @Mock
    PostRepository postRepository;

    @Mock
    PostMapper postMapper;

    @Mock
    UserClient userClient;

    @Mock
    ICloudinary iCloudinary;

    @Mock
    CategoryService categoryService;

    @Mock
    UserMapper userMapper;

    @Mock
    IUser iUser;

    @InjectMocks
    PostService postService;


    private PostRequestDto postRequestDto;

    private PostedBy postedBy;

    private User user;

    private Set<CategoryDto> categoryDtos = new HashSet<>();

    private Set<Category> categories = new HashSet<>();

    private PostDto postDto;

    private List<Post> posts = new ArrayList<>();

    @BeforeEach
    public void beforeEachSetup() {
        categoryDtos.add(new CategoryDto(1L, "cat1", "catImg1"));
        categoryDtos.add(new CategoryDto(2L, "cat2", "catImg2"));
        categoryDtos.add(new CategoryDto(3L, "cat3", "catImg3"));

        categories.add(new Category(1L, "cat1", "catImg1"));
        categories.add(new Category(2L, "cat2", "catImg2"));
        categories.add(new Category(3L, "cat3", "catImg3"));


        postRequestDto = new PostRequestDto("title", "text", "image",
                true, 1L, new HashSet<>());

        postedBy = new PostedBy(1L, "email@email.com", "username", "userImage");

        user = new User(1L, "username", "email@email.com", "userImage", new HashSet<>());

        postDto = new PostDto(1L, "title", "text", "image", true, postedBy, categoryDtos);

        posts.add(new Post(1L, "title1", "desc1", "image1", true, LocalDateTime.MAX, null, user, categories));
        posts.add(new Post(2L, "title2", "desc2", "image2", false, LocalDateTime.MIN, null, user, categories));
        posts.add(new Post(3L, "title3", "desc3", "image3", true, LocalDateTime.MAX, null, user, categories));

    }

    @Test
    void savePostTest() {
        when(userClient.getPostedByDtoByUserId(Mockito.anyLong())).thenReturn(postedBy);
        when(iUser.saveUser(Mockito.any(User.class))).thenReturn(user);

        Post post = new Post(1L, postRequestDto.getTitle(), postRequestDto.getText(), postRequestDto.getImageUrl(), postRequestDto.isAllowComment()
        , LocalDateTime.MAX, null, user, categories);

        when(postMapper.fromPostRequestToEntity(Mockito.any(PostRequestDto.class))).thenReturn(post);

        when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);
        when(postMapper.fromEntityToPostDto(Mockito.any())).thenReturn(postDto);

        var tempCategories = categories.stream().collect(Collectors.toList());
        when(categoryService.getCategoryEntityById(Mockito.anyLong())).thenReturn(tempCategories.get(0), tempCategories.get(1), tempCategories.get(2));

        postRequestDto.setCategoryDtos(categoryDtos);
        var actualValue = postService.savePost(postRequestDto, null);

        assertEquals(actualValue.getId(), postDto.getId());
        assertEquals(actualValue.getDescription(), postDto.getDescription());
        assertEquals(actualValue.getTitle(), postDto.getTitle());
        assertEquals(actualValue.getCategoryDtos().size(), postDto.getCategoryDtos().size());
        assertEquals(actualValue.getPostedBy().getId(), postDto.getPostedBy().getId());
        assertEquals(actualValue.getPostedBy().getUsername(), postDto.getPostedBy().getUsername());
        assertEquals(actualValue.getPostedBy().getEmail(), postDto.getPostedBy().getEmail());
        assertEquals(actualValue.getPostedBy().getImageUrl(), postDto.getPostedBy().getImageUrl());
    }

    @Test
    void savePostThrowNotFoundExceptionWhenUserDoesNotExistViaClient() {
        when(userClient.getPostedByDtoByUserId(Mockito.anyLong())).thenReturn(null);

        assertThrows(NotFoundException.class, () ->
                postService.savePost(postRequestDto, null));
    }

    @Test
    void savePostBadRequestExceptionWhenImageAndMultiPartFileAreNullOrEmpty() {
        when(userClient.getPostedByDtoByUserId(Mockito.anyLong())).thenReturn(postedBy);
        postRequestDto.setImageUrl(null);
        //when image is null and mpf is null
        assertThrows(BadRequestException.class, () -> postService.savePost(postRequestDto, null));

        //when image is empty and mpf is null
        postRequestDto.setImageUrl("");
        assertThrows(BadRequestException.class, () -> postService.savePost(postRequestDto, null));

    }

    @Test
    void throwBadRequestExceptionWhenCategoriesAreEmptyOrNull() {
        when(userClient.getPostedByDtoByUserId(Mockito.anyLong())).thenReturn(postedBy);

        //when categories are empty
        assertThrows(BadRequestException.class, () -> postService.savePost(postRequestDto, null));

        //when set categories is null
        postRequestDto.setCategoryDtos(null);
        assertThrows(BadRequestException.class, () -> postService.savePost(postRequestDto, null));
    }

    @Test
    void getAllPosts() {

        posts.add(new Post(1L, "title1", "desc1", "image1", true, LocalDateTime.MAX, null, user, categories));


        when(postRepository.findAll()).thenReturn(posts);
        when(postMapper.fromEntityToPostDto(Mockito.any()))
                .thenReturn(new PostDto(1L, "title1", "desc1", "image1", true, postedBy, categoryDtos),
                        new PostDto(2L, "title2", "desc2", "image2", true, postedBy, categoryDtos),
                        new PostDto(3L, "title3", "desc3", "image3", true, postedBy, categoryDtos));

        var actualValue = postService.getAllPosts();

        assertEquals(actualValue.size(), posts.size());
        assertEquals(actualValue.get(0).getPostedBy().getId(), posts.get(0).getUser().getId());
    }

    @Test
    void getPostByPostId() {
    }

    @Test
    void getAllPostsByUserId() {
    }

}