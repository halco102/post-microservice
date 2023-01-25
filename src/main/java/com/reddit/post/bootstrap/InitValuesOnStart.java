package com.reddit.post.bootstrap;

import com.reddit.post.model.category.Category;
import com.reddit.post.model.post.Post;
import com.reddit.post.model.user.User;
import com.reddit.post.repository.CategoryRepository;
import com.reddit.post.repository.PostRepository;
import com.reddit.post.service.post.impl.PostService;
import com.reddit.post.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class InitValuesOnStart implements CommandLineRunner {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    private List<Category> categories = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {

        User userPojo = new User();

        //has to be same as UDM for testing
        userPojo.setId(25L);
        userPojo.setUsername("halco1002");
        userPojo.setEmail("ado.halilovic@outlook.com");
        userPojo.setPostLikeDislike(new HashSet<>());
        userPojo.setPosts(new HashSet<>());


        var find = userService.getUserById(25L);
        if (find == null)
            userService.saveUser(userPojo);


        var categoriesModel = categoryRepository.findAll();

        if (!categoriesModel.isEmpty())
            return;

        //create categories for test
        categories.add(new Category(null, "Animals", "https://res.cloudinary.com/dzatojfyn/image/upload/v1657522579/animals_iz3h69.png"));
        categories.add(new Category(null, "Funny", "https://res.cloudinary.com/dzatojfyn/image/upload/v1670778136/clipart1862995_ccjytb.png"));
        categories.add(new Category(null, "Cars", "https://res.cloudinary.com/dzatojfyn/image/upload/v1670778628/flat-vintage-car-icon-by-Vexels_u1jjkb.png"));
        categories.add(new Category(null, "Games", "https://res.cloudinary.com/dzatojfyn/image/upload/v1657522555/games_kldrvd.png"));
        categories.add(new Category(null, "Gifs", "https://res.cloudinary.com/dzatojfyn/image/upload/v1657522546/gif_demv6n.png"));
        categories.add(new Category(null, "Programming", "https://res.cloudinary.com/dzatojfyn/image/upload/v1670788467/kisspng-computer-programming-programmer-icon-design-softwa-coder-png-transparent-image-5a756d89c09351.0972296815176451937888_d8myqq.png"));

        categories.forEach(item -> categoryRepository.save(item));

        //create posts for test
        Post post = new Post(null, "Title1", "Desc1",
                "https://res.cloudinary.com/dzatojfyn/image/upload/v1657713208/rp9ktmfjkgpkbddsl4kt.jpg",
                true, LocalDateTime.now(),
                null,
                userPojo,
                new HashSet<>(categories),
                new HashSet<>());


        var fetchAllPosts = postService.getAllPosts();

        if (fetchAllPosts.isEmpty())
            postRepository.save(post);


    }
}
