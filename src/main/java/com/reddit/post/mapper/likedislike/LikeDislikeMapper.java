package com.reddit.post.mapper.likedislike;

import com.reddit.post.dto.likedislike.LikeDislikeDto;
import com.reddit.post.model.likes_dislikes.LikeAndDislike;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LikeDislikeMapper {

    LikeDislikeMapper INSTANCE = Mappers.getMapper(LikeDislikeMapper.class);

    @Mappings({
            @Mapping(source = "post.id", target = "postId"),
            @Mapping(source = "like", target = "likeOrDislike"),
            @Mapping(source = "user.id", target = "userId")
    })
    LikeDislikeDto entityToDto(LikeAndDislike likeAndDislike);

}
