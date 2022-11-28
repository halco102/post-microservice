package com.reddit.post.feign.controller;

import message.PostedBy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "postController", url = "localhost:8086/api/v1/user")
public interface UserClient {


    @GetMapping("postedBy/{id}")
    PostedBy getPostedByDtoByUserId(@PathVariable("id") Long id);


}
