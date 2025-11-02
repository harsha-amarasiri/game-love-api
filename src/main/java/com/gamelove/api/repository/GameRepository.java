package com.gamelove.api.repository;

import com.gamelove.api.dto.GameStatsProjection;
import com.gamelove.api.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    boolean findByTitleIgnoreCase(String description);

    boolean existsGameByTitleIgnoreCase(String title);

    boolean existsByTitleIgnoreCaseAndIdNot(String title, UUID id);


    @Query(value = """
            SELECT CAST(PG.GAME_ID AS VARCHAR) as gameId, GG.TITLE as gameTitle, COUNT(pg.player_id) as loveCount
            FROM GL_PLAYER_GAMES PG
            INNER JOIN GL_GAMES GG on GG.ID = pg.GAME_ID
            GROUP BY PG.GAME_ID
            ORDER BY COUNT(PG.PLAYER_ID) DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<GameStatsProjection> getPopularGameStats(@Param("limit") int limit);

    @Query(value = """
            SELECT g.*
            FROM gl_games g
            WHERE g.id IN (
                SELECT pg.game_id
                FROM gl_player_games pg
                GROUP BY pg.game_id
                ORDER BY COUNT(pg.player_id) DESC
                LIMIT :limit
            )
            """, nativeQuery = true)
    List<Game> findMostPopularGames(@Param("limit") int limit);

}
