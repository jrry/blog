package com.github.jrry.blog.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "configs")
@Getter @Setter
@EqualsAndHashCode
@ToString
public class ConfigEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String value;
}
