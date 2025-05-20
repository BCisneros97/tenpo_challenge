package com.example.tenpo.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.example.tenpo.dto.ClientResponse;
import com.example.tenpo.entity.Client;

public class ClientMapper {
  public static ClientResponse toResponse(Client client) {
    return ClientResponse.builder()
        .id(client.getId())
        .name(client.getName())
        .createdAt(client.getCreatedAt())
        .updatedAt(client.getUpdatedAt())
        .build();
  }

  public static List<ClientResponse> toResponseList(List<Client> clients) {
    return clients.stream()
        .map(ClientMapper::toResponse)
        .collect(Collectors.toList());
  }
}
