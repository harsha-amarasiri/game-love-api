package com.gamelove.api.service.impl;

import com.gamelove.api.exception.GameLoveException;
import com.gamelove.api.exception.ResourceAlreadyExistsException;
import com.gamelove.api.exception.ResourceNotFoundException;
import com.gamelove.api.mapper.PlayerMapper;
import com.gamelove.api.model.Game;
import com.gamelove.api.model.Genre;
import com.gamelove.api.model.Player;
import com.gamelove.api.model.Status;
import com.gamelove.api.repository.PlayerRepository;
import com.gamelove.api.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PlayerService Unit Tests")
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerMapper playerMapper;

    @Mock
    private GameService gameService;

    @InjectMocks
    private PlayerServiceImpl playerService;

    private Player testPlayer;
    private UUID testPlayerId;
    private Game testGame;
    private UUID testGameId;

    @BeforeEach
    void setUp() {
        testPlayerId = UUID.randomUUID();
        testPlayer = new Player();
        testPlayer.setId(testPlayerId);
        testPlayer.setUsername("testuser");
        testPlayer.setFirstName("Test");
        testPlayer.setLastName("User");
        testPlayer.setStatus(Status.ACTIVE);
        testPlayer.setLovedGames(new ArrayList<>());

        testGameId = UUID.randomUUID();
        testGame = new Game();
        testGame.setId(testGameId);
        testGame.setTitle("Test Game");
        testGame.setGenres(List.of(Genre.ACTION, Genre.ADVENTURE));
        testGame.setStatus(Status.ACTIVE);
    }

    @Test
    @DisplayName("fetchAllPlayers should return list of all players")
    void fetchAllPlayers_ShouldReturnAllPlayers() {
        // Arrange
        List<Player> players = Arrays.asList(testPlayer, new Player());
        when(playerRepository.findAll()).thenReturn(players);

        // Act
        List<Player> result = playerService.fetchAllPlayers();

        // Assert
        assertThat(result).hasSize(2);
        verify(playerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("fetchAllPlayersWithLovedGames should return players with loved games")
    void fetchAllPlayersWithLovedGames_ShouldReturnPlayersWithGames() {
        // Arrange
        List<Player> players = Arrays.asList(testPlayer, new Player());
        when(playerRepository.findAllWithLovedGames()).thenReturn(players);

        // Act
        List<Player> result = playerService.fetchAllPlayersWithLovedGames();

        // Assert
        assertThat(result).hasSize(2);
        verify(playerRepository, times(1)).findAllWithLovedGames();
    }

    @Test
    @DisplayName("getPlayerById should return player when found")
    void getPlayerById_WhenPlayerExists_ShouldReturnPlayer() {
        // Arrange
        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.of(testPlayer));

        // Act
        Player result = playerService.getPlayerById(testPlayerId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testPlayerId);
        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(playerRepository, times(1)).findById(testPlayerId);
    }

    @Test
    @DisplayName("getPlayerById should throw exception when player not found")
    void getPlayerById_WhenPlayerDoesNotExist_ShouldThrowException() {
        // Arrange
        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> playerService.getPlayerById(testPlayerId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Player with id " + testPlayerId + " was not found");
        verify(playerRepository, times(1)).findById(testPlayerId);
    }

    @Test
    @DisplayName("createPlayer should create and return player when username does not exist")
    void createPlayer_WhenUsernameDoesNotExist_ShouldCreatePlayer() {
        // Arrange
        Player newPlayer = new Player();
        newPlayer.setUsername("newuser");
        newPlayer.setFirstName("New");
        newPlayer.setLastName("User");

        Player savedPlayer = new Player();
        savedPlayer.setId(UUID.randomUUID());
        savedPlayer.setUsername("newuser");
        savedPlayer.setStatus(Status.ACTIVE);

        when(playerRepository.existsPlayerByUsername("newuser")).thenReturn(false);
        when(playerRepository.save(any(Player.class))).thenReturn(savedPlayer);

        // Act
        Player result = playerService.createPlayer(newPlayer);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(Status.ACTIVE);
        verify(playerRepository, times(1)).existsPlayerByUsername("newuser");
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    @DisplayName("createPlayer should throw exception when username already exists")
    void createPlayer_WhenUsernameExists_ShouldThrowException() {
        // Arrange
        Player newPlayer = new Player();
        newPlayer.setUsername("existinguser");

        when(playerRepository.existsPlayerByUsername("existinguser")).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> playerService.createPlayer(newPlayer))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Player with username existinguser already exists");
        verify(playerRepository, times(1)).existsPlayerByUsername("existinguser");
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    @DisplayName("updatePlayer should update and return player when valid")
    void updatePlayer_WhenValid_ShouldUpdatePlayer() {
        // Arrange
        Player updates = new Player();
        updates.setUsername("testuser"); // Same username
        updates.setFirstName("Updated");
        updates.setLastName("Name");

        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.of(testPlayer));
        doNothing().when(playerMapper).transferDetails(updates, testPlayer);
        when(playerRepository.save(testPlayer)).thenReturn(testPlayer);

        // Act
        Player result = playerService.updatePlayer(testPlayerId, updates);

        // Assert
        assertThat(result).isNotNull();
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(playerMapper, times(1)).transferDetails(updates, testPlayer);
        verify(playerRepository, times(1)).save(testPlayer);
    }

    @Test
    @DisplayName("updatePlayer should throw exception when player not found")
    void updatePlayer_WhenPlayerNotFound_ShouldThrowException() {
        // Arrange
        Player updates = new Player();
        updates.setUsername("testuser");

        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> playerService.updatePlayer(testPlayerId, updates))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Player with id " + testPlayerId + " was not found");
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    @DisplayName("updatePlayer should throw exception when player is suspended")
    void updatePlayer_WhenPlayerSuspended_ShouldThrowException() {
        // Arrange
        testPlayer.setStatus(Status.SUSPENDED);
        Player updates = new Player();
        updates.setUsername("testuser");

        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.of(testPlayer));

        // Act & Assert
        assertThatThrownBy(() -> playerService.updatePlayer(testPlayerId, updates))
                .isInstanceOf(GameLoveException.class)
                .hasMessageContaining("Updates to player with id " + testPlayerId + " are not allowed. Player is suspended");
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    @DisplayName("updatePlayer should throw exception when username change is attempted")
    void updatePlayer_WhenUsernameChanged_ShouldThrowException() {
        // Arrange
        Player updates = new Player();
        updates.setUsername("newusername"); // Different username

        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.of(testPlayer));

        // Act & Assert
        assertThatThrownBy(() -> playerService.updatePlayer(testPlayerId, updates))
                .isInstanceOf(GameLoveException.class)
                .hasMessageContaining("Username cannot be changed");
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    @DisplayName("updatePlayerStatus should update status when player exists")
    void updatePlayerStatus_WhenPlayerExists_ShouldUpdateStatus() {
        // Arrange
        Status newStatus = Status.INACTIVE;
        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.of(testPlayer));
        when(playerRepository.save(testPlayer)).thenReturn(testPlayer);

        // Act
        Player result = playerService.updatePlayerStatus(testPlayerId, newStatus);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(Status.INACTIVE);
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(playerRepository, times(1)).save(testPlayer);
    }

    @Test
    @DisplayName("updatePlayerStatus should throw exception when player not found")
    void updatePlayerStatus_WhenPlayerNotFound_ShouldThrowException() {
        // Arrange
        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> playerService.updatePlayerStatus(testPlayerId, Status.INACTIVE))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Player with id " + testPlayerId + " was not found");
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    @DisplayName("deletePlayer should delete when player exists")
    void deletePlayer_WhenPlayerExists_ShouldDelete() {
        // Arrange
        when(playerRepository.existsById(testPlayerId)).thenReturn(true);
        doNothing().when(playerRepository).deleteById(testPlayerId);

        // Act
        playerService.deletePlayer(testPlayerId);

        // Assert
        verify(playerRepository, times(1)).existsById(testPlayerId);
        verify(playerRepository, times(1)).deleteById(testPlayerId);
    }

    @Test
    @DisplayName("deletePlayer should throw exception when player not found")
    void deletePlayer_WhenPlayerNotFound_ShouldThrowException() {
        // Arrange
        when(playerRepository.existsById(testPlayerId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> playerService.deletePlayer(testPlayerId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Player with id " + testPlayerId + " was not found");
        verify(playerRepository, times(1)).existsById(testPlayerId);
        verify(playerRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("loveGame should add game to loved games when valid")
    void loveGame_WhenValid_ShouldAddGameToLovedGames() {
        // Arrange
        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.of(testPlayer));
        when(gameService.findGameById(testGameId)).thenReturn(testGame);
        when(playerRepository.save(testPlayer)).thenReturn(testPlayer);

        // Act
        Player result = playerService.loveGame(testPlayerId, testGameId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(testPlayer.getLovedGames()).contains(testGame);
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(gameService, times(1)).findGameById(testGameId);
        verify(playerRepository, times(1)).save(testPlayer);
    }

    @Test
    @DisplayName("loveGame should throw exception when player not found")
    void loveGame_WhenPlayerNotFound_ShouldThrowException() {
        // Arrange
        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> playerService.loveGame(testPlayerId, testGameId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Player with id " + testPlayerId + " was not found");
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(gameService, never()).findGameById(any(UUID.class));
    }

    @Test
    @DisplayName("loveGame should throw exception when game not found")
    void loveGame_WhenGameNotFound_ShouldThrowException() {
        // Arrange
        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.of(testPlayer));
        when(gameService.findGameById(testGameId)).thenThrow(
                new ResourceNotFoundException("Game with id " + testGameId + " was not found.")
        );

        // Act & Assert
        assertThatThrownBy(() -> playerService.loveGame(testPlayerId, testGameId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Game with id " + testGameId + " was not found");
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(gameService, times(1)).findGameById(testGameId);
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    @DisplayName("loveGame should return existing player when game already loved (idempotent)")
    void loveGame_WhenGameAlreadyLoved_ShouldReturnExistingPlayer() {
        // Arrange
        testPlayer.loveGame(testGame); // Already loved
        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.of(testPlayer));
        when(gameService.findGameById(testGameId)).thenReturn(testGame);

        // Act
        Player result = playerService.loveGame(testPlayerId, testGameId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getLovedGames()).contains(testGame);
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(gameService, times(1)).findGameById(testGameId);
        verify(playerRepository, never()).save(any(Player.class)); // Should not save
    }

    @Test
    @DisplayName("unloveGame should remove game from loved games when valid")
    void unloveGame_WhenValid_ShouldRemoveGameFromLovedGames() {
        // Arrange
        testPlayer.loveGame(testGame); // First love the game
        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.of(testPlayer));
        when(gameService.findGameById(testGameId)).thenReturn(testGame);
        when(playerRepository.save(testPlayer)).thenReturn(testPlayer);

        // Act
        Player result = playerService.unloveGame(testPlayerId, testGameId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(testPlayer.getLovedGames()).doesNotContain(testGame);
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(gameService, times(1)).findGameById(testGameId);
        verify(playerRepository, times(1)).save(testPlayer);
    }

    @Test
    @DisplayName("unloveGame should throw exception when player not found")
    void unloveGame_WhenPlayerNotFound_ShouldThrowException() {
        // Arrange
        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> playerService.unloveGame(testPlayerId, testGameId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Player with id " + testPlayerId + " was not found");
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(gameService, never()).findGameById(any(UUID.class));
    }

    @Test
    @DisplayName("unloveGame should throw exception when game not found")
    void unloveGame_WhenGameNotFound_ShouldThrowException() {
        // Arrange
        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.of(testPlayer));
        when(gameService.findGameById(testGameId)).thenThrow(
                new ResourceNotFoundException("Game with id " + testGameId + " was not found.")
        );

        // Act & Assert
        assertThatThrownBy(() -> playerService.unloveGame(testPlayerId, testGameId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Game with id " + testGameId + " was not found");
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(gameService, times(1)).findGameById(testGameId);
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    @DisplayName("unloveGame should throw exception when game is not in loved games")
    void unloveGame_WhenGameNotLoved_ShouldThrowException() {
        // Arrange
        when(playerRepository.findById(testPlayerId)).thenReturn(Optional.of(testPlayer));
        when(gameService.findGameById(testGameId)).thenReturn(testGame);

        // Act & Assert
        assertThatThrownBy(() -> playerService.unloveGame(testPlayerId, testGameId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Player with id " + testPlayerId + " does not love the game with id " + testGameId);
        verify(playerRepository, times(1)).findById(testPlayerId);
        verify(gameService, times(1)).findGameById(testGameId);
        verify(playerRepository, never()).save(any(Player.class));
    }
}