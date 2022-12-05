package com.reddit.post.model.post;

import com.reddit.post.model.category.Category;
import com.reddit.post.model.likes_dislikes.LikeAndDislike;
import com.reddit.post.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @SequenceGenerator(name = "post_sequence", sequenceName = "post_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "allow_comment")
    private boolean allowComment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "edited_at")
    private LocalDateTime editedAt;

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "posts_categories",
            joinColumns = @JoinColumn(name = "posts_id"), inverseJoinColumns = @JoinColumn(name = "categories_id")
    )
    private Set<Category> categories;

    @OneToMany(mappedBy = "post")
    private Set<LikeAndDislike> postLikeDislike;

/*
    @OneToMany(mappedBy = "post")
    private Set<LikeAndDislike> likeAndDislikes;*/

    public Post(Long id, String title, String description, String imageUrl, boolean allowComment, LocalDateTime createdAt, LocalDateTime editedAt, User user, Set<Category> categories) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.allowComment = allowComment;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
        this.user = user;
        this.categories = categories;
    }
}
