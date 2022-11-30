package com.reddit.post.service.search.impl;

import com.reddit.post.dto.post.PostDto;
import com.reddit.post.mapper.post.PostMapper;
import com.reddit.post.repository.PostRepository;
import com.reddit.post.service.search.SearchGeneric;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchPost implements SearchGeneric<PostDto> {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    @Override
    public Set<PostDto> search(String content) {

        var fetchAllPosts = postRepository.searchPosts(content);

        if (fetchAllPosts == null)
            throw new NullPointerException("The search is null");

        return postMapper.collectionEntityToDtos(fetchAllPosts.get());
    }

}
