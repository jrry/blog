package com.github.jrry.blog.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
@EqualsAndHashCode(exclude = {"avatar", "posts", "links"})
@ToString(exclude = {"avatar", "posts", "links"})
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String fullname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private ImageEntity avatar;

    @Column(nullable = false)
    private boolean enabled;

    @Column(length = 25, nullable = false)
    private String role;

    @Column(nullable = false)
    private LocalDateTime lastVisit;

    @Column(nullable = false)
    private LocalDateTime registerDate;

    @OneToMany(mappedBy = "author")
    private Set<ArticleEntity> posts = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<ImageEntity> links = new HashSet<>();
}
