package com.example.tenpo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tenpo.dto.CreateTransactionRequest;
import com.example.tenpo.dto.ErrorResponse;
import com.example.tenpo.dto.TransactionResponse;
import com.example.tenpo.dto.UpdateTransactionRequest;
import com.example.tenpo.dto.ValidationErrorResponse;
import com.example.tenpo.entity.Transaction;
import com.example.tenpo.mappers.TransactionMapper;
import com.example.tenpo.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Transactions", description = "Transaction management")
public class TransactionController {
  private final TransactionService transactionService;

  @GetMapping
  @Operation(summary = "Get all transactions")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TransactionResponse.class)))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
  })
  public List<TransactionResponse> findAll() {
    List<Transaction> transactionList = this.transactionService.findAll();
    return TransactionMapper.toResponseList(transactionList);
  }

  @PostMapping
  @Operation(summary = "Create a new transaction")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Transaction created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
      @ApiResponse(responseCode = "403", description = "Client has reached the maximum number of transactions", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
  })
  public TransactionResponse create(@Valid @RequestBody CreateTransactionRequest createTransactionDto) {
    Transaction transaction = this.transactionService.create(createTransactionDto);
    return TransactionMapper.toResponse(transaction);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update an existing transaction")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Transaction updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
      @ApiResponse(responseCode = "403", description = "Client has reached the maximum number of transactions", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
  })
  public TransactionResponse update(@PathVariable Integer id,
      @Valid @RequestBody UpdateTransactionRequest updateTransactionDto) {
    Transaction transaction = this.transactionService.update(id, updateTransactionDto);
    return TransactionMapper.toResponse(transaction);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a transaction")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Transaction deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
      @ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
  })
  public ResponseEntity<String> delete(@PathVariable Integer id) {
    this.transactionService.delete(id);
    return ResponseEntity.ok("Transaction deleted successfully");
  }
}
