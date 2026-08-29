package com.crypto.EvoCoin.user.model;

import com.crypto.EvoCoin.common.enums.Role;

public record UserRegistration(
        String email,
        String username,
        String password,
        Role role
) {
}
