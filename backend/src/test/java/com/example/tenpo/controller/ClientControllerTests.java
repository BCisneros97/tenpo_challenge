package com.example.tenpo.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.tenpo.entity.Client;
import com.example.tenpo.service.ClientService;

import static org.mockito.BDDMockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClientService clientService;

  Client client;

  @BeforeEach
  public void setup() {
    client = Client.builder()
        .id(1)
        .name("Pablo Gomez")
        .build();
  }

  @Test
  @Order(1)
  public void getClientsTest() throws Exception {
    // precondition
    List<Client> clientsList = new ArrayList<>();
    clientsList.add(client);
    clientsList.add(Client.builder().id(2).name("Jose Perez").build());
    given(clientService.findAll()).willReturn(clientsList);

    // action
    ResultActions response = mockMvc.perform(get("/clients"));

    // verify the output
    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.size()",
            is(clientsList.size())));
  }
}
