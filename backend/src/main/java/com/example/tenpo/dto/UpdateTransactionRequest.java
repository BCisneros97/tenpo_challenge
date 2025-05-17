package com.example.tenpo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTransactionRequest {
  @Min(value = 1, message = "amount must be greater than 0")
  private Integer amount;

  @Size(min = 2, max = 255, message = "business sector must be at least 2 characters long and at most 255 characters long")
  private String businessSector;

  @Size(min = 2, max = 255, message = "client name must be at least 2 characters long and at most 255 characters long")
  private String clientName;

  @PastOrPresent(message = "transaction date must be in the past or present")
  private LocalDateTime transactionDate;
}
