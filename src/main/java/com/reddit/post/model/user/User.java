package com.reddit.post.model.user;

import com.reddit.post.model.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
/*    @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")*/
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts;

    public User(Long id, String username, String imageUrl) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
    }
}
