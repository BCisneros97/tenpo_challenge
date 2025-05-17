package com.example.tenpo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tenpo.dto.CreateTransactionRequest;
import com.example.tenpo.dto.TransactionResponse;
import com.example.tenpo.dto.UpdateTransactionRequest;
import com.example.tenpo.entity.Transaction;
import com.example.tenpo.mappers.TransactionMapper;
import com.example.tenpo.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;

  @GetMapping
  public List<TransactionResponse> findAll() {
    List<Transaction> transactionList = this.transactionService.findAll();
    return TransactionMapper.toResponseList(transactionList);
  }

  @PostMapping
  public TransactionResponse create(@Valid @RequestBody CreateTransactionRequest createTransactionDto) {
    Transaction transaction = this.transactionService.create(createTransactionDto);
    return TransactionMapper.toResponse(transaction);
  }

  @PutMapping("/{id}")
  public TransactionResponse update(@PathVariable Integer id,
      @Valid @RequestBody UpdateTransactionRequest updateTransactionDto) {
    Transaction transaction = this.transactionService.update(id, updateTransactionDto);
    return TransactionMapper.toResponse(transaction);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Integer id) {
    this.transactionService.delete(id);
    return ResponseEntity.ok("Transaction deleted successfully");
  }
}
