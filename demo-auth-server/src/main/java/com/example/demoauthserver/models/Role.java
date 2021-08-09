package com.example.demoauthserver.models;

import java.util.Arrays;

public enum Role {

    ADMIN,
    MEMBER;

    public static boolean contains(Role role) {
        return Arrays.stream(Role.values())
                .anyMatch(r -> role == r);
    }

    public String authority() {
        return "ROLE_" + this.name();
    }
}
