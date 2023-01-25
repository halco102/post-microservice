package com.reddit.post.controller.user;

import com.reddit.post.service.user.IUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/userprofile")
public class UserProfile {

    private final IUser iUser;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfileById(@PathVariable Long id) {
        return new ResponseEntity<>(iUser.getUserProfileById(id), HttpStatus.OK);
    }

}
