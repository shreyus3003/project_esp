package com.foodys.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodys.app.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	
	public List<Transaction> findTransactionByDebitacc(String debitacc);
	public List<Transaction> findTransactionByCreditacc(String creditacc);
	public Transaction findTransactionById(long id);
	public List<Transaction> findAll();
	
}
