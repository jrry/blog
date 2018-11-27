package com.github.jrry.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.jrry.blog.entity.ConfigEntity;

@Repository
public interface ConfigRepository extends JpaRepository<ConfigEntity, Long> {
}
