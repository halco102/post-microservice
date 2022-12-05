package com.reddit.post.model.likes_dislikes;


import com.reddit.post.model.post.Post;
import com.reddit.post.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post_like_dislike")
public class LikeAndDislike {

    @EmbeddedId
    EmbeddedLikeAndDislike embeddedLikeAndDislike = new EmbeddedLikeAndDislike();

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "users_id")
    private User user;

    @MapsId("postId")
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "posts_id")
    private Post post;

    @Column(name = "isLike")
    private boolean isLike;

}
