package com.reddit.post.repository;

import com.reddit.post.dto.likedislike.LikeDislikeDto;
import com.reddit.post.model.likes_dislikes.LikeAndDislike;
import com.reddit.post.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    @Query(value = "SELECT * from posts as p where p.users_id = :userId", nativeQuery = true)
    Set<Post> fetchAllPostsByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * from posts where title like :title% order by created_at desc ", nativeQuery = true)
    Optional<Set<Post>> searchPosts(@Param("title") String title);

    @Query(value = "SELECT new com.reddit.post.dto.likedislike.LikeDislikeDto(pld.post.id, pld.user.id, pld.isLike) " +
            "FROM LikeAndDislike as pld inner join Post as p on p.id = pld.post.id where pld.user.id = :userId")
    Optional<List<LikeDislikeDto>> getAllLikedDislikedPostFromUserId(@Param("userId") Long userId);

    @Query(value = "select * from posts as p " +
            "inner join posts_categories as pc on pc.posts_id = p.id " +
            "inner join categories as c on c.id = pc.categories_id " +
            "where c.\"name\" = :categoryName", nativeQuery = true)
    Optional<List<Post>> getAllPostsByCategoryName(@Param("categoryName") String categoryName);
}
