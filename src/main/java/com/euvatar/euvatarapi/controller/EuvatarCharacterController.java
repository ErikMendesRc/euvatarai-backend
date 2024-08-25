package com.euvatar.euvatarapi.controller;

import com.euvatar.euvatarapi.dto.request.CreateCharacterRequest;
import com.euvatar.euvatarapi.dto.request.GetCharacterDetailsRequest;
import com.euvatar.euvatarapi.dto.request.InteractWithCharacterRequest;
import com.euvatar.euvatarapi.dto.request.UpdateCharacterRequest;
import com.euvatar.euvatarapi.dto.response.CreateCharacterResponse;
import com.euvatar.euvatarapi.dto.response.GetCharacterDetailsResponse;
import com.euvatar.euvatarapi.dto.response.InteractWithCharacterResponse;
import com.euvatar.euvatarapi.service.EuvatarCharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/characters")
public class EuvatarCharacterController {

    private final EuvatarCharacterService euvatarCharacterService;

    public EuvatarCharacterController(EuvatarCharacterService euvatarCharacterService) {
        this.euvatarCharacterService = euvatarCharacterService;
    }

    @Operation(summary = "Cria um novo personagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personagem criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateCharacterResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PostMapping("/create")
    public Mono<ResponseEntity<CreateCharacterResponse>> createCharacter(@RequestBody CreateCharacterRequest request) {
        return euvatarCharacterService.createCharacter(request)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Atualiza um personagem existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personagem atualizado com sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Personagem não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PostMapping("/update")
    public Mono<ResponseEntity<String>> updateCharacter(@RequestBody UpdateCharacterRequest request) {
        return euvatarCharacterService.updateCharacter(request)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Obtém detalhes de um personagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes do personagem retornados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetCharacterDetailsResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Personagem não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PostMapping("/details")
    public Mono<ResponseEntity<String>> getCharacterDetails(@RequestBody GetCharacterDetailsRequest request) {
        if (request.getCharID() == null || request.getCharID().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        return euvatarCharacterService.getCharacterDetails(request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Interage com um personagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interação realizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InteractWithCharacterResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Personagem não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PostMapping("/interact")
    public Mono<ResponseEntity<InteractWithCharacterResponse>> interactWithCharacter(@RequestBody InteractWithCharacterRequest request) {
        return euvatarCharacterService.interactWithCharacter(request)
                .map(ResponseEntity::ok);
    }
}