package com.gamelove.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamelove.api.model.Status;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public record PlayerResponse(
        UUID id,
        String username,

        String firstName,
        String lastName,

        Status status
) {
}
