package com.example.tenpo.service;

import org.springframework.stereotype.Service;

import com.example.tenpo.entity.Client;
import com.example.tenpo.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {
  private final ClientRepository clientRepository;

  public Client findByName(String name) {
    return clientRepository.findByName(name);
  }
}
