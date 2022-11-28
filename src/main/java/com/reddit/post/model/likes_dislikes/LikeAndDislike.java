/*
package com.reddit.post.model.likes_dislikes;


import com.reddit.post.model.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts_like_dislike")
public class LikeAndDislike {

    @EmbeddedId
    EmbeddedLikeAndDislike embeddedLikeAndDislike = new EmbeddedLikeAndDislike();

    @MapsId("userId")
    private Long userId;

    @MapsId("postId")
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "posts_id")
    private Post post;

}
*/
