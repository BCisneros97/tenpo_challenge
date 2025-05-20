package com.example.tenpo.repository;

import java.time.LocalDateTime;
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
import com.example.tenpo.entity.Transaction;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TransactionRepositoryTests {
  @Autowired
  private TransactionRepository transactionRepository;

  @Test
  @DisplayName("Test 1:Save Transaction Test")
  @Order(1)
  @Rollback(value = false)
  public void saveTransactionTest() {
    // Action
    Transaction transaction = new Transaction();
    transaction.setAmount(1000);
    transaction.setBusinessSector("Retail");
    transaction.setTransactionDate(LocalDateTime.now());
    Client client = new Client();
    client.setId(1);
    client.setName("Pablo Gomez");
    transaction.setClient(client);
    transaction.setClientName(client.getName());

    transactionRepository.save(transaction);

    // Verify
    System.out.println(transaction);
    Assertions.assertThat(transaction.getId()).isGreaterThan(0);
  }

  @Test
  @Order(2)
  public void getTransactionTest() {

    // Action
    Transaction transaction = transactionRepository.findById(1).get();
    // Verify
    System.out.println(transaction);
    Assertions.assertThat(transaction.getId()).isEqualTo(1);
  }

  @Test
  @Order(3)
  public void getListOfClientsTest() {
    // Action
    List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
    // Verify
    System.out.println(transactions);
    Assertions.assertThat(transactions.size()).isGreaterThan(0);
  }

  @Test
  @Order(4)
  @Rollback(value = false)
  public void updateTransactionTest() {

    // Action
    Transaction transaction = transactionRepository.findById(1).get();
    transaction.setAmount(3000);
    transaction.setBusinessSector("Compras");
    Transaction transactionUpdated = transactionRepository.save(transaction);

    // Verify
    System.out.println(transactionUpdated);
    Assertions.assertThat(transactionUpdated.getAmount()).isEqualTo(3000);
    Assertions.assertThat(transactionUpdated.getBusinessSector()).isEqualTo("Compras");
  }

  @Test
  @Order(5)
  @Rollback(value = false)
  public void deleteTransactionTest() {
    // Action
    transactionRepository.deleteById(1);
    Optional<Transaction> transactionOptional = transactionRepository.findById(1);

    // Verify
    Assertions.assertThat(transactionOptional).isEmpty();
  }
}
