package cat.itacademy.s05.t02.n01.router;

import cat.itacademy.s05.t02.n01.handler.GameHandler;
import cat.itacademy.s05.t02.n01.handler.PlayerHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@org.springdoc.core.annotations.RouterOperations({
        /*@RouterOperation(path = "/player",
                beanClass = PlayerHandler.class,
                beanMethod = "getAll",
                method = RequestMethod.GET,
                operation = @Operation(
                        description = "Get All Players",
                        summary = "Get All Players",
                        operationId = "getAllPlayers",
                        responses = {
                                @ApiResponse(responseCode = "200", description = "All Players Fetch successfully",
                                        content = @Content(schema = @Schema(implementation = PlayerDto.class))),
                                @ApiResponse(responseCode = "404", description = "Players Not found",
                                        content = @Content),
                                @ApiResponse(responseCode = "500", description = "Internal Server error",
                                        content = @Content),
                            
                        }
                )),
        @RouterOperation(path = "/player/{id}",
                beanClass = PlayerHandler.class,
                beanMethod = "getOne",
                method = RequestMethod.GET,
                operation = @Operation(
                        description = "Get One Player",
                        summary = "Get One Player",
                        operationId = "getOnePlayer",
                        parameters = {
                                @Parameter(in= ParameterIn.PATH, name="id")
                        },
                        responses = {
                                @ApiResponse(responseCode = "200", description = "Player Fetch successfully",
                                        content = @Content(schema = @Schema(implementation = PlayerDto.class))),
                                @ApiResponse(responseCode = "404", description = "Player Not found",
                                        content = @Content),
                                @ApiResponse(responseCode = "500", description = "Internal Server error",
                                        content = @Content),
                            
                        }
                )),
        @RouterOperation(
                path = "/player",
                beanClass = PlayerHandler.class,
                beanMethod = "save",
                method = RequestMethod.POST,
                operation = @Operation(
                        description = "Create New Player",
                        summary = "Create New Player",
                        operationId = "savePlayer",
                        requestBody = @RequestBody(description = "Player entry to Add", required = true,
                                content = @Content(schema = @Schema(implementation = PlayerDto.class))),
                        responses = {
                                @ApiResponse(responseCode = "200", description = "Player entry added successfully",
                                        content = @Content(schema = @Schema(implementation = PlayerDto.class))),
                                @ApiResponse(responseCode = "400", description = "Invalid Parameters",
                                        content = @Content),
                                @ApiResponse(responseCode = "500", description = "Internal Server error",
                                        content = @Content),
                        }
                )
        ),
        @RouterOperation(
                path = "/player/{id}",
                beanClass = PlayerHandler.class,
                beanMethod = "update",
                method = RequestMethod.PUT,
                operation = @Operation(
                        description = "Update Player",
                        summary = "Update Player",
                        operationId = "updatePlayer",
                        parameters = {@Parameter(in= ParameterIn.PATH, name="id")},
                        requestBody = @RequestBody(description = "Player entry to Add", required = true,
                                content = @Content(schema = @Schema(implementation = PlayerDto.class))),
                        responses = {
                                @ApiResponse(responseCode = "200", description = "Player updated successfully",
                                        content = @Content(schema = @Schema(implementation = PlayerDto.class))),
                                @ApiResponse(responseCode = "400", description = "Invalid Parameters",
                                        content = @Content),
                                @ApiResponse(responseCode = "404", description = "Player Not Found",
                                        content = @Content),
                                @ApiResponse(responseCode = "500", description = "Internal Server error",
                                        content = @Content),
                        }
                )
        ),
        @RouterOperation(
                path = "/player/{id}",
                beanClass = PlayerHandler.class,
                beanMethod = "delete",
                method = RequestMethod.DELETE,
                operation = @Operation(
                        description = "Delete Player",
                        summary = "Delete Player",
                        operationId = "DeletePlayer",
                        parameters = {@Parameter(in= ParameterIn.PATH, name="id")},
                        responses = {
                                @ApiResponse(responseCode = "200", description = "Player entry deleted successfully",
                                        content = @Content(schema = @Schema(implementation = PlayerDto.class))),
                                @ApiResponse(responseCode = "404", description = "Player Not Found",
                                        content = @Content),
                                @ApiResponse(responseCode = "500", description = "Internal Server error",
                                        content = @Content),
                        }
                )
        ),*/
        @RouterOperation(
                path = "/player/editnickname/{id}",
                beanClass = PlayerHandler.class,
                beanMethod = "changeNickname",
                method = RequestMethod.PUT,
                operation = @Operation(
                        description = "Change Nickname of Player",
                        summary = "Change Nickname of Player",
                        operationId = "changeNickNameOfPlayer",
                        parameters = {@Parameter(in= ParameterIn.PATH, name="id", description = "Enter the id of the player")},
                        requestBody = @RequestBody(description = "Player entry to Change Nickname", required = true,
                                content = @Content(schema = @Schema(
                                        implementation = Map.class
                                ), examples = @ExampleObject(value = "Enter the new nickname"))),
                        responses = {
                                @ApiResponse(responseCode = "200", description = "Player nickname changed successfully",
                                        content = @Content),
                                @ApiResponse(responseCode = "400", description = "Invalid parameters",
                                        content = @Content),
                                @ApiResponse(responseCode = "404", description = "Player not found",
                                        content = @Content),
                                @ApiResponse(responseCode = "500", description = "Internal server error",
                                        content = @Content),
                        }
                )
        ),
        @RouterOperation(path = "/player/ranking",
                beanClass = PlayerHandler.class,
                beanMethod = "ranking",
                method = RequestMethod.GET,
                operation = @Operation(
                        description = "Get Players Ranking",
                        summary = "Get Players Ranking",
                        operationId = "getPlayersRanking",
                        responses = {
                                @ApiResponse(responseCode = "200", description = "All players fetch successfully",
                                        content = @Content),
                                @ApiResponse(responseCode = "404", description = "Players not found",
                                        content = @Content),
                                @ApiResponse(responseCode = "500", description = "Internal server error",
                                        content = @Content),
                            
                        }
                )),
        @RouterOperation(
                path = "/game/create",
                beanClass = GameHandler.class,
                beanMethod = "createGame",
                method = RequestMethod.POST,
                operation = @Operation(
                        description = "Create New Game",
                        summary = "Create New Game",
                        operationId = "createNewGame",
                        requestBody = @RequestBody(description = "Player entry to create game", required = true,
                                content = @Content(schema = @Schema(
                                        implementation = Map.class
                                ), examples = @ExampleObject(value = "Enter the player nickname"))),
                        responses = {
                                @ApiResponse(responseCode = "200", description = "Game created successfully",
                                        content = @Content),
                                @ApiResponse(responseCode = "500", description = "Internal Server error",
                                        content = @Content),
                        }
                )),
        @RouterOperation(
                path = "/game/delete/{id}",
                beanClass = GameHandler.class,
                beanMethod = "delete",
                method = RequestMethod.DELETE,
                operation = @Operation(
                        description = "Delete a Game",
                        summary = "Delete a Game",
                        operationId = "DeleteAGame",
                        parameters = {@Parameter(in= ParameterIn.PATH, name="id", description = "Enter the id of the game to delete")},
                        responses = {
                                @ApiResponse(responseCode = "200", description = "Game entry deleted successfully",
                                        content = @Content),
                                @ApiResponse(responseCode = "404", description = "Game not found",
                                        content = @Content),
                                @ApiResponse(responseCode = "500", description = "Internal server error",
                                        content = @Content),
                        }
                )
        ),
        @RouterOperation(path = "/game/{id}",
                beanClass = GameHandler.class,
                beanMethod = "getById",
                method = RequestMethod.GET,
                operation = @Operation(
                        description = "Get One Game",
                        summary = "Get One Game",
                        operationId = "getOneGame",
                        parameters = {
                                @Parameter(in= ParameterIn.PATH, name="id", description = "Enter the id of the game to fetch")
                        },
                        responses = {
                                @ApiResponse(responseCode = "200", description = "Game fetch successfully",
                                        content = @Content),
                                @ApiResponse(responseCode = "404", description = "Game not found",
                                        content = @Content),
                                @ApiResponse(responseCode = "500", description = "Internal server error",
                                        content = @Content),
                            
                        }
                )),
        @RouterOperation(
                path = "/game/{id}/play",
                beanClass = GameHandler.class,
                beanMethod = "play",
                method = RequestMethod.POST,
                operation = @Operation(
                        description = "Make a Move",
                        summary = "Make a Move",
                        operationId = "makeAMove",
                        parameters = {@Parameter(in= ParameterIn.PATH, name="id", description = "Enter the id of the game")},
                        requestBody = @RequestBody(description = "HIT or STAND", required = true,
                                content = @Content(schema = @Schema(
                                        implementation = Map.class
                                ), examples = @ExampleObject(value = "Enter the action"))),
                        responses = {
                                @ApiResponse(responseCode = "200", description = "Action performed successfully",
                                        content = @Content),
                                @ApiResponse(responseCode = "400", description = "Invalid parameters",
                                        content = @Content),
                                @ApiResponse(responseCode = "404", description = "Action not Found",
                                        content = @Content),
                                @ApiResponse(responseCode = "500", description = "Internal server error",
                                        content = @Content),
                        }
                )),
       
})
public @interface RouterOperations {}
