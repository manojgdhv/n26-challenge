package com.n26.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.springframework.stereotype.Service;

import com.n26.models.Transaction;
import com.n26.services.TransactionService;

/**
 * Component responsible for adding and maintaining transactions.
 * @author manojg
 *
 */
@Service("transactionService")
public class TransactionService{
	private ConcurrentNavigableMap<Long, List<Transaction>> transactions =  new ConcurrentSkipListMap<Long, List<Transaction>>();

	public synchronized void addTransaction(Transaction transaction) {
		List<Transaction> transactionsForTimestamp = transactions.get(transaction.getTimestamp());

		if (transactionsForTimestamp == null) {
			transactionsForTimestamp = new ArrayList<Transaction>();
		}

		transactionsForTimestamp.add(transaction);
		transactions.put(transaction.getTimestamp(), transactionsForTimestamp);
	}
	
	public synchronized ConcurrentNavigableMap<Long, List<Transaction>> getTransactions() {
		return transactions;
	}
}
