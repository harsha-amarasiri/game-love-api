package com.gamelove.api.repository;

import com.gamelove.api.dto.GameStatsProjection;
import com.gamelove.api.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    Optional<Game> findByTitleIgnoreCase(String description);

    boolean existsGameByTitleIgnoreCase(String title);

    boolean existsByTitleIgnoreCaseAndIdNot(String title, UUID id);

    // Harsha => casting here may cause portability issues, however at present only support for H2 is required.
    @Query(value = """
            SELECT CAST(PG.GAME_ID AS VARCHAR) as gameId, GG.TITLE as gameTitle, COUNT(pg.player_id) as loveCount
            FROM PUBLIC.GL_PLAYER_LOVED_GAMES PG
            INNER JOIN GL_GAMES GG on GG.ID = PG.GAME_ID
            GROUP BY PG.GAME_ID
            ORDER BY COUNT(PG.PLAYER_ID) DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<GameStatsProjection> getPopularGameStats(@Param("limit") int limit);

    @Query(value = """
            SELECT G.*
            FROM GL_GAMES G
            INNER JOIN (
                SELECT PG.GAME_ID
                FROM GL_PLAYER_LOVED_GAMES PG
                GROUP BY PG.GAME_ID
                ORDER BY COUNT(PG.PLAYER_ID) DESC
                LIMIT :limit
            )
            """, nativeQuery = true)
    List<Game> findMostPopularGames(@Param("limit") int limit);

}
