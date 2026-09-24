package com.deep.docmind.dto;

public record LoginResponse(
        String accessToken,
        UserDto userDto
) {
}
