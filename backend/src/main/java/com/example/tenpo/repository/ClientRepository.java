package com.example.tenpo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.tenpo.entity.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {
  Client findByName(String name);
}
