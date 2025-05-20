package com.example.tenpo.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.tenpo.entity.Client;
import com.example.tenpo.repository.ClientRepository;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientServiceTests {

  @Mock
  private ClientRepository clientRepository;

  @InjectMocks
  private ClientService clientService;

  private Client client;

  @BeforeEach
  public void setup() {
    client = Client.builder()
        .id(1)
        .name("Jose Perez")
        .build();
  }

  @Test
  @Order(1)
  public void getAllClients() {
    Client client2 = Client.builder()
        .id(2)
        .name("Pablo Lopez")
        .build();

    // precondition
    given(clientRepository.findAll()).willReturn(List.of(client, client2));

    // action
    List<Client> clientsList = clientService.findAll();

    // verify
    System.out.println(clientsList);
    assertThat(clientsList).isNotNull();
    assertThat(clientsList.size()).isGreaterThan(1);
  }

  @Test
  @Order(2)
  public void getClientByName() {
    // precondition
    given(clientRepository.findByName("Jose Perez")).willReturn(client);

    // action
    Client existingClient = clientService.findByName("Jose Perez");

    // verify
    System.out.println(existingClient);
    assertThat(existingClient).isNotNull();
  }

}
