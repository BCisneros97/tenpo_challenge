package com.example.tenpo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tenpo.dto.ClientResponse;
import com.example.tenpo.dto.ErrorResponse;
import com.example.tenpo.entity.Client;
import com.example.tenpo.mappers.ClientMapper;
import com.example.tenpo.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Client management")
public class ClientController {
  private final ClientService clientService;

  @GetMapping
  @Operation(summary = "Get all clients")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Clients retrieved successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClientResponse.class)))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
  })
  public List<ClientResponse> findAll() {
    List<Client> clientList = this.clientService.findAll();
    return ClientMapper.toResponseList(clientList);
  }
}
