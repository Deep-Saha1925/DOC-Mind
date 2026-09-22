package com.deep.docmind.dto;

import com.deep.docmind.entity.Role;

public record UserDto(
        Long id,
        String username,
        String email,
        Role role
) {
}