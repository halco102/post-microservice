/*
package com.reddit.post.mapper.user;

import com.reddit.post.model.user.User;
import message.PostedBy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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


    @Test
    void fromUserSetToSetDtoTest() {

        Set<User> users = new HashSet<>();

        users.add(new User(1L, "user1", "email@email.com", "image1", new HashSet<>()));
        users.add(new User(2L, "user2", "email2@email.com", "image2", new HashSet<>()));
        users.add(new User(3L, "user3", "email3@email.com", "image3", new HashSet<>()));


        var value = Mappers.getMapper(UserMapper.class).fromUserSetToSetDto(users);

        var toListUsers = users.stream().collect(Collectors.toList());
        var toListValues = value.stream().collect(Collectors.toList());

        for (int i = 0; i < toListValues.size(); i++) {
            Assertions.assertEquals(toListValues.get(i).getId(), toListUsers.get(i).getId());
            Assertions.assertEquals(toListValues.get(i).getEmail(), toListUsers.get(i).getEmail());
            Assertions.assertEquals(toListValues.get(i).getUsername(), toListUsers.get(i).getUsername());
            Assertions.assertEquals(toListValues.get(i).getImageUrl(), toListUsers.get(i).getImageUrl());
        }
    }
}
*/
