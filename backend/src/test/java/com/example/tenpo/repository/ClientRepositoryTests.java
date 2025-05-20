package com.example.tenpo.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.example.tenpo.entity.Client;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ClientRepositoryTests {
  @Autowired
  private ClientRepository clientRepository;

  @Test
  @DisplayName("Test 1:Save Client Test")
  @Order(1)
  @Rollback(value = false)
  public void saveClientTest() {
    // Action
    Client client = new Client();
    client.setName("Pablo Gomez");

    clientRepository.save(client);

    // Verify
    System.out.println(client);
    Assertions.assertThat(client.getId()).isGreaterThan(0);
  }

  @Test
  @Order(2)
  public void getClientTest() {

    // Action
    Client client = clientRepository.findById(1).get();
    // Verify
    System.out.println(client);
    Assertions.assertThat(client.getId()).isEqualTo(1);
  }

  @Test
  @Order(3)
  public void getClientByNameTest() {

    // Action
    Client client = clientRepository.findByName("Pablo Gomez");
    // Verify
    System.out.println(client);
    Assertions.assertThat(client.getId()).isEqualTo(1);
  }

  @Test
  @Order(3)
  public void getListOfClientsTest() {
    // Action
    List<Client> clients = (List<Client>) clientRepository.findAll();
    // Verify
    System.out.println(clients);
    Assertions.assertThat(clients.size()).isGreaterThan(0);
  }

  @Test
  @Order(4)
  @Rollback(value = false)
  public void updateClientTest() {

    // Action
    Client client = clientRepository.findById(1).get();
    client.setName("Pablo Torres");
    Client clientUpdated = clientRepository.save(client);

    // Verify
    System.out.println(clientUpdated);
    Assertions.assertThat(clientUpdated.getName()).isEqualTo("Pablo Torres");
  }

  @Test
  @Order(5)
  @Rollback(value = false)
  public void deleteClientTest() {
    // Action
    clientRepository.deleteById(1);
    Optional<Client> clientOptional = clientRepository.findById(1);

    // Verify
    Assertions.assertThat(clientOptional).isEmpty();
  }
}
