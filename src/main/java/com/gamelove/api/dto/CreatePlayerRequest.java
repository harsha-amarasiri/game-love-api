package com.gamelove.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record CreatePlayerRequest(

        @NotEmpty
        @Length(min = 3, max = 64)
        String username,

        @NotEmpty
        @Length(min = 3, max = 255)
        String firstName,

        @NotEmpty
        @Length(min = 3, max = 255)
        String lastName
) {
}
