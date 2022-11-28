package com.reddit.post.mapper.user;

import com.reddit.post.model.user.User;
import message.PostedBy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserMapperTest {

    private User user;

    private PostedBy postedBy;

    @BeforeEach
    public void beforeEachStart() {
        user = new User(1L, "admir", "imageUrl");
        postedBy = new PostedBy(1L, "admir", "imageUrl");
    }

    @Test
    void entityToPostedBy() {

        var value = Mappers.getMapper(UserMapper.class).entityToPostedBy(user);

        Assertions.assertEquals(value.getId(), user.getId());
        Assertions.assertEquals(value.getUsername(), user.getUsername());
        Assertions.assertEquals(value.getImageUrl(), user.getImageUrl());
    }

    @Test
    void postedByToEntity() {

        var value = Mappers.getMapper(UserMapper.class).postedByToEntity(postedBy);

        Assertions.assertEquals(value.getId(), user.getId());
        Assertions.assertEquals(value.getUsername(), user.getUsername());
        Assertions.assertEquals(value.getImageUrl(), user.getImageUrl());
        Assertions.assertNull(value.getPosts());
    }
}