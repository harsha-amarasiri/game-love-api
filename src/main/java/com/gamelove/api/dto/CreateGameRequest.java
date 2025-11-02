package com.gamelove.api.dto;

import com.gamelove.api.model.Genre;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Builder
public record CreateGameRequest(

        @NotEmpty
        @Length(min = 3, max = 255)
        String title,

        @NotEmpty
        @Length(min = 3, max = 100)
        String description,

        List<Genre> genres
) {
}