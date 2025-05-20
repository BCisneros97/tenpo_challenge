package com.example.tenpo.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.tenpo.dto.CreateTransactionRequest;
import com.example.tenpo.dto.UpdateTransactionRequest;
import com.example.tenpo.entity.Client;
import com.example.tenpo.entity.Transaction;
import com.example.tenpo.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionServiceTests {
  @Mock
  private TransactionRepository transactionRepository;

  @InjectMocks
  private TransactionService transactionService;

  private Transaction transaction;

  @BeforeEach
  public void setup() {
    transaction = Transaction.builder()
        .id(1)
        .amount(3000)
        .businessSector("Compras")
        .clientName("Jose Perez")
        .client(Client.builder()
            .id(1)
            .name("Jose Perez")
            .build())
        .transactionDate(LocalDateTime.now().minusDays(1))
        .build();
  }

  @Test
  @Order(1)
  public void saveTransactionTest() {
    // precondition
    given(transactionRepository.save(transaction)).willReturn(transaction);
    CreateTransactionRequest transactionRequest = CreateTransactionRequest.builder()
        .amount(3000)
        .businessSector("Compras")
        .clientName("Jose Perez")
        .transactionDate(LocalDateTime.now().minusDays(1))
        .build();

    // action
    Transaction savedTransaction = transactionService.create(transactionRequest);

    // verify the output
    System.out.println(savedTransaction);
    assertThat(savedTransaction).isNotNull();
  }

  @Test
  @Order(2)
  public void getAllTransactions() {
    Transaction transaction2 = Transaction.builder()
        .id(2)
        .amount(4000)
        .businessSector("Ventas")
        .transactionDate(LocalDateTime.now().minusDays(2))
        .clientName("Pablo Lopez")
        .client(Client.builder()
            .id(2)
            .name("Pablo Lopez")
            .build())
        .build();

    // precondition
    given(transactionRepository.findAllByOrderByIdDesc()).willReturn(List.of(transaction, transaction2));

    // action
    List<Transaction> transactionsList = transactionService.findAll();

    // verify
    System.out.println(transactionsList);
    assertThat(transactionsList).isNotNull();
    assertThat(transactionsList.size()).isGreaterThan(1);
  }

  @Test
  @Order(3)
  public void updateTransaction() {

    // precondition
    transaction.setAmount(5000);
    transaction.setBusinessSector("Ventas");
    given(transactionRepository.findById(transaction.getId())).willReturn(Optional.of(transaction));
    given(transactionRepository.save(transaction)).willReturn(transaction);

    // action
    UpdateTransactionRequest updateTransactionRequest = UpdateTransactionRequest.builder()
        .amount(5000)
        .businessSector("Ventas")
        .build();
    Transaction updatedTransaction = transactionService.update(transaction.getId(), updateTransactionRequest);

    // verify
    System.out.println(updatedTransaction);
    assertThat(updatedTransaction.getAmount()).isEqualTo(updateTransactionRequest.getAmount());
    assertThat(updatedTransaction.getBusinessSector()).isEqualTo(updateTransactionRequest.getBusinessSector());
  }

  @Test
  @Order(4)
  public void deleteTransaction() {

    // precondition
    willDoNothing().given(transactionRepository).deleteById(transaction.getId());

    // action
    transactionService.delete(transaction.getId());

    // verify
    verify(transactionRepository, times(1)).deleteById(transaction.getId());
  }
}
