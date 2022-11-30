package com.reddit.post.service.search.impl;

import com.reddit.post.dto.category.CategoryDto;
import com.reddit.post.dto.post.PostDto;
import com.reddit.post.mapper.post.PostMapper;
import com.reddit.post.model.category.Category;
import com.reddit.post.model.post.Post;
import com.reddit.post.model.user.User;
import com.reddit.post.repository.PostRepository;
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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SearchPostTest {

    @Mock
    PostRepository postRepository;

    @Mock
    PostMapper postMapper;

    @InjectMocks
    SearchPost searchPost;

    private Set<Post> posts = new HashSet<>();

    private Set<PostDto> postDtos = new HashSet<>();

    private Set<Category> categories = new HashSet<>();

    private Set<CategoryDto> categoryDtos = new HashSet<>();
    private User user;

    private PostedBy postedBy;

    @BeforeEach
    public void beforeSetup() {
        categoryDtos.add(new CategoryDto(1L, "cat1", "catImg1"));
        categoryDtos.add(new CategoryDto(2L, "cat2", "catImg2"));
        categoryDtos.add(new CategoryDto(3L, "cat3", "catImg3"));

        categories.add(new Category(1L, "cat1", "catImg1"));
        categories.add(new Category(2L, "cat2", "catImg2"));
        categories.add(new Category(3L, "cat3", "catImg3"));

        posts.add(new Post(1L, "title1", "desc1", "image1", true, LocalDateTime.MAX, null, user, categories));
        posts.add(new Post(2L, "title2", "desc2", "image2", false, LocalDateTime.MIN, null, user, categories));
        posts.add(new Post(3L, "title3", "desc3", "image3", true, LocalDateTime.MAX, null, user, categories));

        postedBy = new PostedBy(1L, "email@email.com", "username", "userImage");

        user = new User(1L, "username", "email@email.com", "userImage", new HashSet<>());

        postDtos.add(new PostDto(1L, "title1", "desc1", "image1", true, postedBy, categoryDtos));
        postDtos.add(new PostDto(2L, "title2", "desc2", "image2", true, postedBy, categoryDtos));
        postDtos.add(new PostDto(3L, "title3", "desc3", "image3", true, postedBy, categoryDtos));

    }

    @Test
    void search() {

        when(postRepository.searchPosts(Mockito.anyString())).thenReturn(Optional.of(posts));
        when(postMapper.collectionEntityToDtos(Mockito.anySet())).thenReturn(postDtos);

        var actual = searchPost.search("temp").stream().collect(Collectors.toList());
        var postDtoToList = postDtos.stream().collect(Collectors.toList());

        Assertions.assertEquals(actual.size(), postDtos.size());

        for (int i = 0; i < actual.size(); i++) {
            Assertions.assertEquals(actual.get(i).getId(), postDtoToList.get(i).getId());
            Assertions.assertEquals(actual.get(i).getTitle(), postDtoToList.get(i).getTitle());
            Assertions.assertEquals(actual.get(i).getDescription(), postDtoToList.get(i).getDescription());
            Assertions.assertEquals(actual.get(i).getCategoryDtos().size(), postDtoToList.get(i).getCategoryDtos().size());
            Assertions.assertEquals(actual.get(i).getPostedBy().getId(), postDtoToList.get(i).getPostedBy().getId());
            Assertions.assertEquals(actual.get(i).getPostedBy().getUsername(), postDtoToList.get(i).getPostedBy().getUsername());
            Assertions.assertEquals(actual.get(i).getPostedBy().getEmail(), postDtoToList.get(i).getPostedBy().getEmail());
            Assertions.assertEquals(actual.get(i).getPostedBy().getImageUrl(), postDtoToList.get(i).getPostedBy().getImageUrl());

        }
    }


}