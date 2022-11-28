package com.reddit.post.service.user;

import com.reddit.post.exception.BadRequestException;
import com.reddit.post.exception.NotFoundException;
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

}
