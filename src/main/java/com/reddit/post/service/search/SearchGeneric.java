package com.reddit.post.service.search;

import java.util.Set;

public interface SearchGeneric <T>{

    Set<T> search(String content);

}
