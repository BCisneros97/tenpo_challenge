package com.example.tenpo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tenpo.entity.Client;
import com.example.tenpo.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {
  private final ClientRepository clientRepository;

  public List<Client> findAll() {
    return (List<Client>) this.clientRepository.findAll();
  }

  public Client findByName(String name) {
    return clientRepository.findByName(name);
  }
}
