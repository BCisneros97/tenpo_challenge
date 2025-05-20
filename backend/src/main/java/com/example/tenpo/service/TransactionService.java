package com.example.tenpo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tenpo.dto.CreateTransactionRequest;
import com.example.tenpo.dto.UpdateTransactionRequest;
import com.example.tenpo.entity.Client;
import com.example.tenpo.entity.Transaction;
import com.example.tenpo.exceptions.ForbiddenException;
import com.example.tenpo.exceptions.NotFoundException;
import com.example.tenpo.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final ClientService clientService;

  public List<Transaction> findAll() {
    return this.transactionRepository.findAllByOrderByIdDesc();
  }

  public Transaction create(CreateTransactionRequest createTransactionDto) {
    Client client = this.findClientByName(createTransactionDto.getClientName());
    this.validateClientTransactionLimit(client);

    Transaction transaction = new Transaction();
    transaction.setAmount(createTransactionDto.getAmount());
    transaction.setBusinessSector(createTransactionDto.getBusinessSector());
    transaction.setClient(client);
    transaction.setClientName(client.getName());
    transaction.setTransactionDate(createTransactionDto.getTransactionDate());

    return this.transactionRepository.save(transaction);
  }

  public Transaction update(Integer id, UpdateTransactionRequest updateTransactionDto) {
    Transaction transaction = this.findById(id);

    Client client = null;
    if (updateTransactionDto.getClientName() != null) {
      client = this.findClientByName(updateTransactionDto.getClientName());
    }
    this.validateClientTransactionLimit(client);

    transaction.setAmount(
        updateTransactionDto.getAmount() != null ? updateTransactionDto.getAmount() : transaction.getAmount());
    transaction.setBusinessSector(updateTransactionDto.getBusinessSector() != null
        ? updateTransactionDto.getBusinessSector()
        : transaction.getBusinessSector());
    transaction.setTransactionDate(updateTransactionDto.getTransactionDate() != null
        ? updateTransactionDto.getTransactionDate()
        : transaction.getTransactionDate());
    if (client != null) {
      transaction.setClient(client);
      transaction.setClientName(client.getName());
    }

    return this.transactionRepository.save(transaction);
  }

  public void delete(Integer id) {
    Transaction transaction = this.findById(id);
    this.transactionRepository.delete(transaction);
  }

  private Transaction findById(Integer id) {
    return this.transactionRepository.findById(id).orElseThrow(
        () -> new NotFoundException("Transaction not found"));
  }

  private Client findClientByName(String clientName) {
    Client client = this.clientService.findByName(clientName);
    if (client == null) {
      throw new NotFoundException("Client not found");
    }
    return client;
  }

  private void validateClientTransactionLimit(Client client) {
    if (client.getTransactions().size() >= 2) {
      throw new ForbiddenException("Client has reached the maximum number of transactions");
    }
  }
}
