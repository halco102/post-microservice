package com.reddit.post.repository;

import com.reddit.post.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    Optional<User> fetchUserByUsername(@Param("username") String username);


    @Query(value = "SELECT * from users where username like :username%", nativeQuery = true)
    Optional<Set<User>> searchUsersByUsername(@Param("username") String username);

}
