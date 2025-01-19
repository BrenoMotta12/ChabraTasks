package com.bm12.chabra.controller;

import com.bm12.chabra.config.validation.FormException;
import com.bm12.chabra.dto.user.AuthUser;
import com.bm12.chabra.dto.user.GetUser;
import com.bm12.chabra.dto.user.SaveUser;
import com.bm12.chabra.dto.user.UpdateUser;
import com.bm12.chabra.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint para cadastrar um novo usuário.
     *
     * @param saveUser DTO contendo as informações do usuário a ser cadastrado.
     * @return ResponseEntity contendo os dados do usuário recém-cadastrado.
     */
    @Operation(summary = "Create a new user", description = "Creates a new user in the system.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully registered",
                    content = {@Content(schema = @Schema(implementation = GetUser.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid registration data",
                    content = @Content(schema = @Schema(implementation = FormException.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(type = ""))
            ),
    })
    @PostMapping("/signUp")
    public ResponseEntity<GetUser> signUp(@RequestBody @Valid SaveUser saveUser) {
        System.out.println(saveUser);
        return this.userService.signUp(saveUser);
    }

    /**
     * Endpoint para autenticar um usuário.
     *
     * @param authUser DTO contendo as credenciais de autenticação.
     * @return ResponseEntity contendo o token de autenticação caso as credenciais estejam corretas.
     */
    @Operation(summary = "Authenticate a user", description = "Authenticates the user and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully authenticated",
                    content = @Content(schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(type = ""))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(type = "string"))
            ),

    })
    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody @Valid AuthUser authUser) {
        return this.userService.auth(authUser);
    }

    /**
     * Endpoint para retornar uma lista com todos os usuários cadastrados.
     *
     * @return ResponseEntity contendo uma lista de objetos GetUser representando todos os usuários.
     */
    @Operation(summary = "List users", description = "Returns a list of all registered users.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved user list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GetUser.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(type = ""))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(type = ""))
            )
    })
    @GetMapping
    public ResponseEntity<List<GetUser>> getAllUsers() {
        return this.userService.getAllUsers();
    }

    /**
     * Endpoint para retornar um usuário específico baseado no ID.
     *
     * @param id UUID do usuário que será recuperado.
     * @return ResponseEntity contendo os dados do usuário identificado pelo ID.
     */
    @Operation(summary = "Get a user", description = "Fetches and returns a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = @Content(schema = @Schema(implementation = GetUser.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(type = ""))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(type = "string"))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetUser> getUserById(@PathVariable UUID id) {
        return this.userService.getUserById(id);
    }

    /**
     * Endpoint para atualizar os dados de um usuário existente.
     *
     * @param updateUser DTO contendo as novas informações do usuário a ser atualizado.
     * @return ResponseEntity contendo os dados atualizados do usuário.
     */
    @Operation(summary = "Update user", description = "Updates the information of an existing user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully updated",
                    content = @Content(schema = @Schema(implementation = GetUser.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(type = ""))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data for update",
                    content = @Content(schema = @Schema(implementation = FormException.class))
            )
    })

    @PutMapping("/update")
    public ResponseEntity<GetUser> updateUser(@RequestBody @Valid UpdateUser updateUser) {
        return this.userService.updateUser(updateUser);
    }
}
