package com.reddit.post.feign.controller;

import com.reddit.post.dto.user.UserSecurityDto;
import message.PostedBy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userClient", url = "${USER_MICROSERVICE}")
public interface UserClient {


    @GetMapping("/postedBy/{id}")
    PostedBy getPostedByDtoByUserId(@PathVariable("id") Long id);

    @GetMapping("/current")
    PostedBy getCurrentlyLoggedUser();

    @GetMapping("/security/find-by-username")
    PostedBy findUserByUsername(@RequestParam("username") String username);

}
