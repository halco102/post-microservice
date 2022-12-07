package com.reddit.post.dto.user;

import com.reddit.post.dto.post.PostDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import message.PostedBy;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto extends PostedBy {

    private Set<PostDto> postDtos;

    public UserDto(Long id, String email, String username, String imageUrl, Set<PostDto> postDtos) {
        super(id, email, username, imageUrl);
        this.postDtos = postDtos;
    }

}
