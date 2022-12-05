package com.reddit.post.mapper.user;

import com.reddit.post.dto.UserDto;
import com.reddit.post.mapper.DoIgnore;
import com.reddit.post.model.user.User;
import message.PostedBy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Qualifier;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    PostedBy entityToPostedBy(User user);

    User postedByToEntity(PostedBy postedBy);

    Set<PostedBy> fromUserSetToSetDto(Set<User> users);

    @DoIgnore
    Set<UserDto> temp(Set<User> users);


    @DoIgnore
    @Mappings({
            @Mapping(source = "posts", target = "postDtos")
    })
    UserDto temp2(User user);

}
