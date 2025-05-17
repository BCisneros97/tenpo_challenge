package com.example.tenpo.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.example.tenpo.dto.TransactionResponse;
import com.example.tenpo.entity.Transaction;

public class TransactionMapper {
  public static TransactionResponse toResponse(Transaction transaction) {
    return TransactionResponse.builder()
        .id(transaction.getId())
        .amount(transaction.getAmount())
        .businessSector(transaction.getBusinessSector())
        .clientName(transaction.getClient().getName())
        .transactionDate(transaction.getTransactionDate())
        .createdAt(transaction.getCreatedAt())
        .updatedAt(transaction.getUpdatedAt())
        .build();
  }

  public static List<TransactionResponse> toResponseList(List<Transaction> transactions) {
    return transactions.stream()
        .map(TransactionMapper::toResponse)
        .collect(Collectors.toList());
  }
}
