package com.github.jrry.blog.service;

import com.github.jrry.blog.entity.UserEntity;

public interface UserService {
    UserEntity getAuthenticatedUser();
}
