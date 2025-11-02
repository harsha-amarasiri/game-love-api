package com.gamelove.api.repository;

import com.gamelove.api.model.Player;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    boolean existsPlayerByUsername(String username);

    @EntityGraph(attributePaths = "lovedGames")
    @Query("SELECT p FROM Player p")
    List<Player> findAllWithLovedGames();

    // Finds all without loading loved games
    @Override
    List<Player> findAll();

//    @Query("SELECT DISTINCT p FROM Player p LEFT JOIN FETCH p.lovedGames")
//    List<Player> findAllPlayersWithLovedGames();
}
