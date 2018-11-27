package com.github.jrry.blog.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter @Setter
@EqualsAndHashCode(exclude = "posts")
@ToString(exclude = "posts")
public class CategoryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String seoName;

    @OneToMany(mappedBy = "category")
    private Set<ArticleEntity> posts = new HashSet<>();
}
