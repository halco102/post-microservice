package com.reddit.post.service.user;

import com.reddit.post.dto.user.UserProfile;
import com.reddit.post.model.user.User;
import message.PostedBy;

public interface IUser {

    User saveUser(User user);

    PostedBy getUserById(Long id);

    User getUserEntityById(Long id);

    UserProfile getUserProfileById(Long id);
}
