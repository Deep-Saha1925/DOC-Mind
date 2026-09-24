package com.deep.docmind.dto;

public record RegisterUserRequest(
        String username,
        String email,
        String password
) {
}
