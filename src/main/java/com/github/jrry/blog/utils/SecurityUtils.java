package com.github.jrry.blog.utils;

import com.github.jrry.blog.entity.UserEntity;
import com.github.jrry.blog.repository.UserRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.github.jrry.blog.common.errors.NotFoundException;

import java.util.Optional;

public final class SecurityUtils {
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof UserDetails) {
                        UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                        return springSecurityUser.getUsername();
                    } else if (authentication.getPrincipal() instanceof String) {
                        return (String) authentication.getPrincipal();
                    }
                    return null;
                });
    }

    public static UserEntity getCurrentUser(UserRepository userRepository) {
        return getCurrentUserLogin().flatMap(userRepository::findByUsername).orElseThrow(NotFoundException::new);
    }
}
