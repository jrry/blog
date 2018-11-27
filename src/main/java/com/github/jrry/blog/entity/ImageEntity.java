package com.github.jrry.blog.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "images")
@Getter @Setter
@EqualsAndHashCode(exclude = {"articles", "avatars", "owner"})
@ToString(exclude = {"articles", "avatars"})
public class ImageEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String alt;

    private String source;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant created;

    @OneToMany(mappedBy = "image")
    private Set<ArticleEntity> articles = new HashSet<>();

    @OneToMany(mappedBy = "avatar")
    private Set<UserEntity> avatars = new HashSet<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
}
