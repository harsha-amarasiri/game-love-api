package com.gamelove.api.dto;

import com.gamelove.api.model.Genre;
import com.gamelove.api.model.Status;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record GameResponse(
        UUID id,
        String title,
        String description,
        Status status,
        List<Genre> genres
) {
}
