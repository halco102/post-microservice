package com.reddit.post.mapper.post;

import com.reddit.post.dto.category.CategoryDto;
import com.reddit.post.dto.post.request.PostRequestDto;
import com.reddit.post.mapper.category.CategoryMapper;
import com.reddit.post.mapper.user.UserMapper;
import com.reddit.post.model.category.Category;
import com.reddit.post.model.post.Post;
import com.reddit.post.model.user.User;
import message.PostedBy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
class PostMapperTest {

    @Mock
    CategoryMapper categoryMapper;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    PostMapperImpl postMapper;

    private User user;
    private List<Post> posts = new ArrayList<>();
    private Set<Category> category = new HashSet<>();


    @BeforeEach
    public void beforeEachSetup() {

        user = new User(1L, "username", "imageUser");

        Mockito.when(userMapper.entityToPostedBy(Mockito.any(User.class))).thenReturn(new PostedBy(1L, "username", "imageUser"));

        category.add(new Category(10L, "cat1", "imgCat1"));
        category.add(new Category(20L, "cat2", "imgCat2"));
        category.add(new Category(30L, "cat3", "imgCat3"));

        Mockito.when(categoryMapper.fromEntityToCategoryDto(Mockito.any(Category.class)))
                .thenReturn(new CategoryDto(10L, "cat1", "imgCat1"),
                        new CategoryDto(20L, "cat2", "imgCat2"),
                        new CategoryDto(30L, "cat3","imgCat3"));

        posts.add(new Post(1L, "title1", "desc1", "image1", true, LocalDateTime.MIN, LocalDateTime.MAX, user, category));
        posts.add(new Post(2L, "title2", "desc2", "image2", false, LocalDateTime.MIN, LocalDateTime.MAX, user, category));
        posts.add(new Post(3L, "title3", "desc3", "image3", true, LocalDateTime.MIN, LocalDateTime.MAX, user, category));
        posts.add(new Post(4L, "title4", "desc4", "image4", false, LocalDateTime.MIN, LocalDateTime.MAX, user, category));
        posts.add(new Post(5L, "title5", "desc5", "image5", true, LocalDateTime.MIN, LocalDateTime.MAX, user, category));

    }

    @Test
    void fromEntityToPostDto() {
        var testExample = posts.get(0);
        var value = postMapper.fromEntityToPostDto(testExample);

        Assertions.assertEquals(value.getId(), testExample.getId());
        Assertions.assertEquals(value.getTitle(), testExample.getTitle());
        Assertions.assertEquals(value.getDescription(), testExample.getDescription());
        Assertions.assertEquals(value.getImageUrl(), testExample.getImageUrl());
        Assertions.assertEquals(value.isAllowComment(), testExample.isAllowComment());

        Assertions.assertNotEquals(0, value.getCategoryDtos().size());
        Assertions.assertEquals(value.getCategoryDtos().size(), testExample.getCategories().size());

        Assertions.assertNotNull(value.getPostedBy());
        Assertions.assertEquals(value.getPostedBy().getId(), testExample.getUser().getId());
        Assertions.assertEquals(value.getPostedBy().getUsername(), testExample.getUser().getUsername());
        Assertions.assertEquals(value.getPostedBy().getImageUrl(), testExample.getUser().getImageUrl());

    }

    @Test
    void listPostDtoFromListPost() {
    }

    @Test
    void fromPostRequestToEntity() {

        var postRequest = new PostRequestDto("title", "text", "imageUrl",
                true, 1L, new HashSet<>(Arrays.asList(
                        new CategoryDto(10L, "cat1", "imgCat1"),
                new CategoryDto(20L, "cat2", "imgCat2")
        )));

        var value = postMapper
                .fromPostRequestToEntity(postRequest);

        Assertions.assertNull(value.getId());
        Assertions.assertEquals(value.getImageUrl(), postRequest.getImageUrl());
        Assertions.assertEquals(value.getTitle(), postRequest.getTitle());
        Assertions.assertEquals(value.getDescription(), postRequest.getText());
        Assertions.assertEquals(value.isAllowComment(), postRequest.isAllowComment());

        Assertions.assertNotNull(value.getCategories());
        Assertions.assertEquals(value.getCategories().size(), postRequest.getCategoryDtos().size());

    }

    @Test
    void collectionEntityToDtos() {
        var value = postMapper.collectionEntityToDtos(posts.stream().collect(Collectors.toSet()));
        Assertions.assertEquals(value.size(), posts.size());
    }
}