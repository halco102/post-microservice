package com.reddit.post.model.likes_dislikes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmbeddedLikeAndDislike implements Serializable {

    private static final long serialVersionUID = 1954035474703245373L;

    @Column(name = "users_id", nullable = false)
    private Long userId;

    @Column(name = "posts_id", nullable = false)
    private Long postId;


}
