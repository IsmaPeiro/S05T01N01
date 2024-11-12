package cat.itacademy.s05.t02.n01.handler;

import cat.itacademy.s05.t02.n01.dto.PlayerDto;
import cat.itacademy.s05.t02.n01.service.PlayerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerHandlerTest {
    
    @Mock
    private PlayerService playerService;
    
    @Mock
    private ServerRequest request;
    
    @InjectMocks
    private PlayerHandler playerHandler;
    private AutoCloseable closeable;
    private PlayerDto playerDto;
    private PlayerDto updatedPlayerDto;
    
    @BeforeEach
    public void setUp() {
        playerDto = new PlayerDto(1L, "IsmaTest", 35, 5, 3, 1);
        updatedPlayerDto = new PlayerDto(1L, "IsmaTestUpdated", 35, 5, 3, 1);
        closeable = MockitoAnnotations.openMocks(this);
    }
    
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
    
    @Test
    public void testGetAll() {
        when(playerService.getAll()).thenReturn(Flux.just(playerDto));
        
        Mono<ServerResponse> responseMono = playerHandler.getAll(request);
        
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().value() == 200)
                .verifyComplete();
        
        verify(playerService).getAll();
    }
    
    @Test
    public void testGetOne() {
        when(playerService.getById(1L)).thenReturn(Mono.just(playerDto));
        
        ServerRequest request = MockServerRequest.builder().pathVariable("id", "1").build();
        Mono<ServerResponse> responseMono = playerHandler.getOne(request);
        
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().value() == 200)
                .verifyComplete();
        
        verify(playerService).getById(playerDto.getId());
    }
    
    @Test
    public void testSave() {
        when(playerService.save(any(PlayerDto.class))).thenReturn(Mono.just(playerDto));
        
        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .body(Mono.just(playerDto));
        
        Mono<ServerResponse> responseMono = playerHandler.save(request);
        
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().value() == 200)
                .verifyComplete();
        
        verify(playerService).save(any(PlayerDto.class));
    }
    
    @Test
    public void testUpdate() {
        when(playerService.update(anyLong(), any(PlayerDto.class))).thenReturn(Mono.just(updatedPlayerDto));
        
        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .body(Mono.just(updatedPlayerDto));
        
        Mono<ServerResponse> responseMono = playerHandler.update(request);
        
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().value() == 200)
                .verifyComplete();
        
        verify(playerService).update(anyLong(), any(PlayerDto.class));
    }
    
    @Test
    public void testDelete() {
        when(playerService.delete(1L)).thenReturn(Mono.empty());
        
        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .build();
        
        Mono<ServerResponse> responseMono = playerHandler.delete(request);
        
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().value() == 200)
                .verifyComplete();
        
        verify(playerService).delete(1L);
    }
    
    @Test
    public void testChangeNickname() {
        when(playerService.changeNickname(anyLong(), any(String.class))).thenReturn(Mono.just(updatedPlayerDto));
        
        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .body(Mono.just("NewNickname"));
        Mono<ServerResponse> responseMono = playerHandler.changeNickname(request);
        
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().value() == 200)
                .verifyComplete();
        
        verify(playerService).changeNickname(1L, "NewNickname");
    }
    
    @Test
    public void testRanking() {
        when(playerService.ranking()).thenReturn(Flux.just(playerDto));
        
        Mono<ServerResponse> responseMono = playerHandler.ranking(request);
        
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().value() == 200)
                .verifyComplete();
        
        verify(playerService).ranking();
    }
    
}

