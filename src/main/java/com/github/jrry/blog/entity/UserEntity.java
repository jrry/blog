/*
 * Copyright (c) 2018 Jarosław Pawłowski
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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

/**
 * @author Jarosław Pawłowski
 */
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
