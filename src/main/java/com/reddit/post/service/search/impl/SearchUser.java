package com.reddit.post.service.search.impl;

import com.reddit.post.dto.UserDto;
import com.reddit.post.mapper.user.UserMapper;
import com.reddit.post.repository.UserRepository;
import com.reddit.post.service.search.SearchGeneric;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import message.PostedBy;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchUser implements SearchGeneric<UserDto> {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public Set<UserDto> search(String content) {

        var searchUsers = userRepository.searchUsersByUsername(content.trim());

        if (searchUsers == null)
            throw new NullPointerException("The search is null");

        return userMapper.temp(searchUsers.get());
    }

}
