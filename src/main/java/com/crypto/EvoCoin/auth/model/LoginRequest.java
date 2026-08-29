package com.crypto.EvoCoin.auth.model;

public record LoginRequest(
        String username,
        String password
) {
}
