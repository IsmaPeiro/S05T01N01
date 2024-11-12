package cat.itacademy.s05.t02.n01.service;

import cat.itacademy.s05.t02.n01.dto.PlayerDto;
import cat.itacademy.s05.t02.n01.entity.Player;
import cat.itacademy.s05.t02.n01.exception.CustomException;
import cat.itacademy.s05.t02.n01.mapper.PlayerMapper;
import cat.itacademy.s05.t02.n01.repository.GameRepository;
import cat.itacademy.s05.t02.n01.repository.PlayerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PlayerServiceTest {
    
    @Mock
    private PlayerRepository playerRepository;
    
    @Mock
    private GameRepository gameRepository;
    
    @Mock
    private PlayerMapper playerMapper;
    
    @InjectMocks
    private PlayerService playerService;
    private Long playerId;
    private Player player;
    private PlayerDto playerDto;
    private Player updatedPlayer;
    private PlayerDto updatedPlayerDto;
    private AutoCloseable closeable;
    
    @BeforeEach
    void setUp() {
        playerId = 1L;
        closeable = MockitoAnnotations.openMocks(this);
        player = new Player(playerId, "OldName", 5, 3, 1);
        playerDto = new PlayerDto(playerId, "OldName", 25, 5, 3, 1);
        updatedPlayerDto = new PlayerDto(playerId, "UpdatedName", 35, 6, 3, 1);
        updatedPlayer = new Player(playerId, "UpdatedName", 6, 3, 1);
    }
    
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
    
    @Test
    void testChangeNickname_Succes() {
        Long playerId = 1L;
        PlayerDto updatedPlayerDto = new PlayerDto(playerId, "UpdatedName", 35, 5, 3, 1);
        Player existingPlayer = new Player(playerId, "OldName", 5, 3, 1);
        Player updatedPlayer = new Player(playerId, "UpdatedName", 6, 3, 1);
        
        when(playerRepository.findById(playerId)).thenReturn(Mono.just(existingPlayer));
        when(playerRepository.repeatedNickname(playerId, updatedPlayerDto.getNickname())).thenReturn(Mono.empty());
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(updatedPlayer));
        when(playerMapper.toDto(updatedPlayer)).thenReturn(updatedPlayerDto);
        when(gameRepository.findAll()).thenReturn(Flux.empty());
        
        StepVerifier.create(playerService.changeNickname(playerId, updatedPlayerDto.getNickname()))
                .expectNext(updatedPlayerDto)
                .verifyComplete();
        
        verify(playerRepository, times(1)).findById(playerId);
        verify(playerRepository, times(1)).repeatedNickname(playerId, updatedPlayerDto.getNickname());
        verify(playerRepository, times(1)).save(any(Player.class));
        verify(playerMapper, times(1)).toDto(updatedPlayer);
    }
    
    @Test
    void testChangeNickname_PlayerNotFound() {
        Long playerId = 1L;
        PlayerDto updatedPlayerDto = new PlayerDto(playerId, "UpdatedName", 25, 5, 3, 1);
        
        when(playerRepository.findById(playerId)).thenReturn(Mono.empty());
        when(playerRepository.repeatedNickname(anyLong(), anyString())).thenReturn(Mono.empty());
        
        StepVerifier.create(playerService.changeNickname(playerId, updatedPlayerDto.getNickname()))
                .expectErrorMatches(throwable -> throwable instanceof CustomException &&
                        ((CustomException) throwable).getStatus() == HttpStatus.NOT_FOUND)
                .verify();
        
        verify(playerRepository, times(1)).findById(playerId);
        verify(playerRepository, times(1)).repeatedNickname(anyLong(), anyString());
        verify(playerRepository, never()).save(any(Player.class));
    }
    
    @Test
    void testChangeNickname_NicknameAlreadyExists() {
        Long playerId = 1L;
        PlayerDto updatedPlayerDto = new PlayerDto(playerId, "UpdatedName", 35, 6, 3, 1);
        Player existingPlayer = new Player(playerId,"OldName",5,3,1);
        
        when(playerRepository.findById(playerId)).thenReturn(Mono.just(existingPlayer));
        when(playerRepository.repeatedNickname(playerId, updatedPlayerDto.getNickname())).thenReturn(Mono.just(existingPlayer));
        
        StepVerifier.create(playerService.changeNickname(playerId, updatedPlayerDto.getNickname()))
                .expectErrorMatches(throwable -> throwable instanceof CustomException &&
                        ((CustomException) throwable).getStatus() == HttpStatus.BAD_REQUEST)
                .verify();
        
        verify(playerRepository, times(1)).findById(playerId);
        verify(playerRepository, times(1)).repeatedNickname(playerId, updatedPlayerDto.getNickname());
        verify(playerRepository, never()).save(any(Player.class));
    }
    
    @Test
    void testRanking_Success() {
        when(playerRepository.findAll()).thenReturn(Flux.just(player));
        when(playerMapper.toDto(player)).thenReturn(playerDto);
        
        StepVerifier.create(playerService.ranking())
                .expectNext(playerDto)
                .verifyComplete();
        
        verify(playerRepository, times(1)).findAll();
    }
    
    @Test
    void testRanking_NotFound() {
        when(playerRepository.findAll()).thenReturn(Flux.empty());
        
        StepVerifier.create(playerService.ranking())
                .expectErrorMatches(throwable -> throwable instanceof CustomException &&
                        ((CustomException) throwable).getStatus() == HttpStatus.BAD_REQUEST)
                .verify();
        
        verify(playerRepository, times(1)).findAll();
    }
}


