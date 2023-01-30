package com.reddit.post.mapper.post;

import com.reddit.post.dto.post.PostDto;
import com.reddit.post.dto.post.request.PostRequestDto;
import com.reddit.post.mapper.category.CategoryMapper;
import com.reddit.post.mapper.likedislike.LikeDislikeMapper;
import com.reddit.post.mapper.user.UserMapper;
import com.reddit.post.model.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class, LikeDislikeMapper.class})
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mappings({
            @Mapping(target = "categoryDtos", source = "categories"),
            @Mapping(target = "postedBy", source = "user"),
            @Mapping(target = "postLikedDislike", source = "postLikeDislike")
    })
    PostDto fromEntityToPostDto(Post post);

    @Mappings({
            @Mapping(source = "text", target = "description"),
            @Mapping(target = "categories", source = "categories"),
            @Mapping(target = "allowComment", source = "allowComments")
    })
    Post fromPostRequestToEntity(PostRequestDto requestDto);

    Set<PostDto> collectionEntityToDtos(Set<Post> posts);

}
