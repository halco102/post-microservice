package com.reddit.post.dto.user;

import com.reddit.post.dto.post.PostDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import message.PostedBy;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserProfile extends UserDto{

    private Set<PostedBy> followers;

    private Set<PostedBy> following;

    public UserProfile(Long id, String email, String username, String imageUrl, Set<PostDto> postDtos, Set<PostedBy> followers, Set<PostedBy> following) {
        super(id, email, username, imageUrl, postDtos);
        this.followers = followers;
        this.following = following;
    }



}
