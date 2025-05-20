package com.example.tenpo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.tenpo.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
  public List<Transaction> findAllByOrderByIdDesc();
}
