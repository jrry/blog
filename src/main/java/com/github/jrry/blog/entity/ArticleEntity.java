package com.github.jrry.blog.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.github.jrry.blog.enums.ArticleStatusEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
@Getter @Setter
@EqualsAndHashCode(exclude = {"author", "category", "image", "tags"})
@ToString(exclude = {"author", "tags"})
public class ArticleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    @Column(nullable = false)
    private String seoLink;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ArticleStatusEnum status;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime modified;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "article_tags",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false))
    private Set<TagEntity> tags = new HashSet<>();
}
