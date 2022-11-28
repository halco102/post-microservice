package com.reddit.post.repository;

import com.reddit.post.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    @Query(value = "SELECT * from posts as p where p.users_id = :userId", nativeQuery = true)
    Set<Post> fetchAllPostsByUserId(@Param("userId") Long userId);

}
