package com.github.jrry.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.github.jrry.blog.entity.UserEntity;
import com.github.jrry.blog.repository.UserRepository;
import com.github.jrry.blog.utils.SecurityUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity getAuthenticatedUser() {
        return SecurityUtils.getCurrentUser(userRepository);
    }
}
