package com.github.jrry.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter @Setter
@EqualsAndHashCode(exclude = "posts")
@ToString(exclude = "posts")
@NoArgsConstructor
public class TagEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 50)
    private String cssClass;

    @ManyToMany(mappedBy = "tags")
    private Set<ArticleEntity> posts = new HashSet<>();

    public TagEntity(String name) {
        this.name = name;
    }
}
