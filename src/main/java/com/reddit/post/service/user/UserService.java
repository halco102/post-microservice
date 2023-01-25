package com.reddit.post.service.user;

import com.reddit.post.dto.user.UserProfile;
import com.reddit.post.exception.BadRequestException;
import com.reddit.post.exception.NotFoundException;
import com.reddit.post.feign.controller.UserClient;
import com.reddit.post.mapper.post.PostMapper;
import com.reddit.post.mapper.user.UserMapper;
import com.reddit.post.model.user.User;
import com.reddit.post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import message.PostedBy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUser{

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserClient userClient;

    private final PostMapper postMapper;
    @Override
    public User saveUser(User user) {

        if (user == null)
            throw new BadRequestException("User is null");

        var findUser = userRepository.findById(user.getId());

        if (findUser.isPresent())
            return findUser.get();

        return userRepository.save(user);
    }

    @KafkaListener(topics = "USER_UPDATE_EVENT", containerFactory = "kafkaListenerContainerFactory")
    private void updateUser(@Payload PostedBy postedBy) {

        var fetchUserByUsername = userRepository.findById(postedBy.getId()).orElseThrow(() -> new NotFoundException("User was not found"));

        fetchUserByUsername.setUsername(postedBy.getUsername());
        fetchUserByUsername.setImageUrl(postedBy.getImageUrl());
        fetchUserByUsername.setUsername(postedBy.getUsername());

        userRepository.save(fetchUserByUsername);

        log.info("User successfully updated");
    }

    @KafkaListener(topics = "USER_DELETE_EVENT", containerFactory = "kafkaListenerContainerFactory")
    private void deleteUserById(@Payload Long id) {
        var fetchUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("The user was not found"));
        userRepository.deleteById(fetchUser.getId());
        log.info("Post service -> delete user by id");
    }

    @Override
    public PostedBy getUserById(Long id) {
        var fetchUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User does not exist"));
        return userMapper.entityToPostedBy(fetchUser);
    }

    @Override
    public User getUserEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("The user does not exist"));
    }

    @Override
    public UserProfile getUserProfileById(Long id) {

        var fetchUser = userRepository.findById(id);

        if (fetchUser.isEmpty())
            throw new NotFoundException("The user does not exist");

        var fetchFollowersFromUser = userClient.getFollowersFromUserById(id);
        var fetchFollowingFromUser = userClient.getFollowingFromUserById(id);

        var toDto = userMapper.userEntityToUserProfile(fetchUser.get());
        toDto.setFollowers(fetchFollowersFromUser);
        toDto.setFollowing(fetchFollowingFromUser);
        toDto.setPostDtos(postMapper.collectionEntityToDtos(fetchUser.get().getPosts()));

        return toDto;
    }

}
