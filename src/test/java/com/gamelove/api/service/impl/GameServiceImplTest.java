package com.gamelove.api.service.impl;

import com.gamelove.api.dto.GameResponse;
import com.gamelove.api.dto.GameStatsProjection;
import com.gamelove.api.exception.ResourceAlreadyExistsException;
import com.gamelove.api.exception.ResourceNotFoundException;
import com.gamelove.api.mapper.GameMapper;
import com.gamelove.api.model.Game;
import com.gamelove.api.model.Genre;
import com.gamelove.api.model.Status;
import com.gamelove.api.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("GameService Unit Tests")
class GameServiceImplTest {


    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private GameServiceImpl gameService;

    private Game testGame;
    private UUID testGameId;

    @BeforeEach
    void setUp() {
        // setup test game
        testGame = new Game();
        testGame.setId(UUID.randomUUID());
        testGame.setTitle("Test Game");
        testGame.setDescription("Test Description");
        testGame.getGenres().add(Genre.ACTION);
        testGame.getGenres().add(Genre.ADVENTURE);
        testGame.getGenres().add(Genre.ROGUELIKE);
        testGame.setStatus(Status.ACTIVE);

        // setup test game id
        testGameId = testGame.getId();
    }

    @Test
    @DisplayName("getAllGames should return list of all games")
    void getAllGames_ShouldReturnAllGames() {
        // Arrange
        List<Game> games = Arrays.asList(testGame, new Game());
        when(gameRepository.findAll()).thenReturn(games);

        // Act
        List<Game> result = gameService.getAllGames();

        // Assert
        assertThat(result).hasSize(2);
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findGameById should return game when found")
    void findGameById_WhenGameExists_ShouldReturnGame() {
        // Arrange
        when(gameRepository.findById(testGameId)).thenReturn(Optional.of(testGame));

        // Act
        Game result = gameService.findGameById(testGameId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testGameId);
        assertThat(result.getTitle()).isEqualTo("Test Game");
        verify(gameRepository, times(1)).findById(testGameId);
    }

    @Test
    @DisplayName("findGameById should throw exception when game not found")
    void findGameById_WhenGameDoesNotExist_ShouldThrowException() {
        // Arrange
        when(gameRepository.findById(testGameId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> gameService.findGameById(testGameId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Game with id " + testGameId + " was not found");
        verify(gameRepository, times(1)).findById(testGameId);
    }

    @Test
    @DisplayName("createGame should create and return game when title does not exist")
    void createGame_WhenTitleDoesNotExist_ShouldCreateGame() {
        // Arrange
        Game newGame = new Game();
        newGame.setTitle("New Game");
        newGame.setDescription("New Description");

        // genres
        var genres = List.of(Genre.ACTION, Genre.ADVENTURE, Genre.ROGUELIKE);

        newGame.setGenres(genres);

        Game savedGame = new Game();
        savedGame.setId(UUID.randomUUID());
        savedGame.setTitle("New Game");
        savedGame.setStatus(Status.ACTIVE);

        when(gameRepository.existsGameByTitleIgnoreCase("New Game")).thenReturn(false);
        when(gameRepository.save(any(Game.class))).thenReturn(savedGame);

        // Act
        Game result = gameService.createGame(newGame);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(Status.ACTIVE);
        verify(gameRepository, times(1)).existsGameByTitleIgnoreCase("New Game");
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    @DisplayName("createGame should throw exception when title already exists")
    void createGame_WhenTitleExists_ShouldThrowException() {
        // Arrange
        Game newGame = new Game();
        newGame.setTitle("Existing Game");

        when(gameRepository.existsGameByTitleIgnoreCase("Existing Game")).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> gameService.createGame(newGame))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Game with title Existing Game already exists");
        verify(gameRepository, times(1)).existsGameByTitleIgnoreCase("Existing Game");
        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test
    @DisplayName("updateGameDetails should update and return game when valid")
    void updateGameDetails_WhenValid_ShouldUpdateGame() {
        // Arrange
        Game updates = new Game();
        updates.setTitle("Updated Title");
        updates.setDescription("Updated Description");

        Game updatedGame = new Game();
        updatedGame.setId(testGameId);
        updatedGame.setTitle("Updated Title");

        when(gameRepository.findById(testGameId)).thenReturn(Optional.of(testGame));
        when(gameRepository.existsByTitleIgnoreCaseAndIdNot("Updated Title", testGameId)).thenReturn(false);
        doNothing().when(gameMapper).transferDetails(updates, testGame);
        when(gameRepository.save(testGame)).thenReturn(updatedGame);

        // Act
        Game result = gameService.updateGameDetails(testGameId, updates);

        // Assert
        assertThat(result).isNotNull();
        verify(gameRepository, times(1)).findById(testGameId);
        verify(gameMapper, times(1)).transferDetails(updates, testGame);
        verify(gameRepository, times(1)).save(testGame);
    }

    @Test
    @DisplayName("updateGameDetails should throw exception when game not found")
    void updateGameDetails_WhenGameNotFound_ShouldThrowException() {
        // Arrange
        Game updates = new Game();
        updates.setTitle("Updated Title");

        when(gameRepository.findById(testGameId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> gameService.updateGameDetails(testGameId, updates))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Game with id " + testGameId + " was not found");
        verify(gameRepository, times(1)).findById(testGameId);
        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test
    @DisplayName("updateGameDetails should throw exception when title conflicts with another game")
    void updateGameDetails_WhenTitleConflicts_ShouldThrowException() {
        // Arrange
        Game updates = new Game();
        updates.setTitle("Conflicting Title");

        when(gameRepository.findById(testGameId)).thenReturn(Optional.of(testGame));
        when(gameRepository.existsByTitleIgnoreCaseAndIdNot("Conflicting Title", testGameId)).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> gameService.updateGameDetails(testGameId, updates))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Game with title Conflicting Title already exists");
        verify(gameRepository, times(1)).findById(testGameId);
        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test
    @DisplayName("updateGameStatus should update status when game exists")
    void updateGameStatus_WhenGameExists_ShouldUpdateStatus() {
        // Arrange
        Status newStatus = Status.INACTIVE;
        when(gameRepository.findById(testGameId)).thenReturn(Optional.of(testGame));
        when(gameRepository.save(testGame)).thenReturn(testGame);

        // Act
        Game result = gameService.updateGameStatus(testGameId, newStatus);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(Status.INACTIVE);
        verify(gameRepository, times(1)).findById(testGameId);
        verify(gameRepository, times(1)).save(testGame);
    }

    @Test
    @DisplayName("updateGameStatus should throw exception when game not found")
    void updateGameStatus_WhenGameNotFound_ShouldThrowException() {
        // Arrange
        when(gameRepository.findById(testGameId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> gameService.updateGameStatus(testGameId, Status.INACTIVE))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Game with id " + testGameId + " was not found");
        verify(gameRepository, times(1)).findById(testGameId);
        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test
    @DisplayName("deleteGame should delete when game exists")
    void deleteGame_WhenGameExists_ShouldDelete() {
        // Arrange
        when(gameRepository.findById(testGameId)).thenReturn(Optional.of(testGame));
        doNothing().when(gameRepository).deleteById(testGameId);

        // Act
        gameService.deleteGame(testGameId);

        // Assert
        verify(gameRepository, times(1)).findById(testGameId);
        verify(gameRepository, times(1)).deleteById(testGameId);
    }

    @Test
    @DisplayName("deleteGame should throw exception when game not found")
    void deleteGame_WhenGameNotFound_ShouldThrowException() {
        // Arrange
        when(gameRepository.findById(testGameId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> gameService.deleteGame(testGameId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Game with id " + testGameId + " was not found");
        verify(gameRepository, times(1)).findById(testGameId);
        verify(gameRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("getMostPopularGameStats should return game statistics")
    void getMostPopularGameStats_ShouldReturnStats() {
        // Arrange
        int size = 10;
        List<GameStatsProjection> mockStats = Arrays.asList(
                mock(GameStatsProjection.class),
                mock(GameStatsProjection.class)
        );
        when(gameRepository.getPopularGameStats(size)).thenReturn(mockStats);

        // Act
        List<GameStatsProjection> result = gameService.getMostPopularGameStats(size);

        // Assert
        assertThat(result).hasSize(2);
        verify(gameRepository, times(1)).getPopularGameStats(size);
    }

    @Test
    @DisplayName("getMostPopularGames should return popular games")
    void getMostPopularGames_ShouldReturnGames() {

        int count = 5;

        // Arrange
        var game1 = new Game();
        game1.setId(UUID.randomUUID());
        game1.setTitle("Game 1");
        game1.setDescription("Game 1 Description");
        game1.setStatus(Status.ACTIVE);
        game1.setGenres(List.of(Genre.ACTION, Genre.ADVENTURE, Genre.RPG));

        var game2 = new Game();
        game2.setId(UUID.randomUUID());
        game2.setTitle("Game 2");
        game2.setDescription("Game 2 Description");
        game2.setStatus(Status.ACTIVE);
        game2.setGenres(List.of(Genre.ACTION, Genre.ADVENTURE, Genre.RPG));

        var popularGames = Arrays.asList(game1, game2);

        var gameResponse1 = GameResponse.builder()
                .id(game1.getId())
                .title(game1.getTitle())
                .description(game1.getDescription())
                .status(game1.getStatus())
                .genres(game1.getGenres())
                .build();

        var gameResponse2 = GameResponse.builder()
                .id(game2.getId())
                .title(game2.getTitle())
                .description(game2.getDescription())
                .status(game2.getStatus())
                .genres(game2.getGenres())
                .build();

        var gameResponses = Arrays.asList(gameResponse1, gameResponse2);

        when(gameRepository.findMostPopularGames(count)).thenReturn(popularGames);
        when(gameMapper.toGameResponseList(popularGames)).thenReturn(gameResponses);

        // Act
        List<GameResponse> result = gameService.getMostPopularGames(count);

        // Assert
        assertThat(result).hasSize(2);
        verify(gameRepository, times(1)).findMostPopularGames(count);
        verify(gameMapper, times(1)).toGameResponseList(popularGames);
    }

}