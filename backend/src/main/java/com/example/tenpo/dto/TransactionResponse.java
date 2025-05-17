package com.example.tenpo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
  private Integer id;
  private Integer amount;
  private String businessSector;
  private String clientName;
  private LocalDateTime transactionDate;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
