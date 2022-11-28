package com.reddit.post.mapper.user;

import com.reddit.post.model.user.User;
import message.PostedBy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    PostedBy entityToPostedBy(User user);

    User postedByToEntity(PostedBy postedBy);

}
