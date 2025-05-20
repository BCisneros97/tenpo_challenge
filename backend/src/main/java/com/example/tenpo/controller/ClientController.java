package com.example.tenpo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tenpo.dto.ClientResponse;
import com.example.tenpo.entity.Client;
import com.example.tenpo.mappers.ClientMapper;
import com.example.tenpo.service.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
  private final ClientService clientService;

  @GetMapping
  public List<ClientResponse> findAll() {
    List<Client> clientList = this.clientService.findAll();
    return ClientMapper.toResponseList(clientList);
  }
}
