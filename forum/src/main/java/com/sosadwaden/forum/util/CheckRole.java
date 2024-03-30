package com.sosadwaden.forum.util;

import com.sosadwaden.forum.exception.UserNotAuthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;

public final class CheckRole {

    private static Set<String> userRoles = new HashSet<>();

    static {
        initUserRoles();
    }

    public static boolean hasUserRole() {
        return userRoles.contains("user");
    }

    public static boolean hasAdminRole() {
        return userRoles.contains("admin");
    }

    private static void initUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            authentication.getAuthorities().forEach(authority -> {
                userRoles.add(authority.getAuthority());
            });
        } else {
            throw UserNotAuthenticatedException.builder()
                    .message("User is not authenticated")
                    .build();
        }
    }
}
