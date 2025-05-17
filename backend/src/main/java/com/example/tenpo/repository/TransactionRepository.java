package com.example.tenpo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.tenpo.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

}
