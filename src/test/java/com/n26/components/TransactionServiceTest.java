package com.n26.components;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.n26.models.Transaction;
import com.n26.services.TransactionService;

public class TransactionServiceTest {
	@Test
	public void testAddTransaction() {
		Transaction t1 = new Transaction();
		t1.setAmount(1000D);
		t1.setTimestamp(1492188482000L);
		
		Transaction t2 = new Transaction();
		t2.setAmount(2000D);
		t2.setTimestamp(1492188482000L);
		
		List<Transaction> expectedTransactions = new ArrayList<Transaction>();
		expectedTransactions.add(t1);
		expectedTransactions.add(t2);
		
		TransactionService transactionService = new TransactionService();
		transactionService.addTransaction(t1);
		transactionService.addTransaction(t2);
		
		List<Transaction> actualTransactions = transactionService.getTransactions().get(1492188482000L);
		
		Assert.assertEquals(expectedTransactions.size(), actualTransactions.size());
		Assert.assertEquals(expectedTransactions.get(0).getAmount(),actualTransactions.get(0).getAmount(),0);
		Assert.assertEquals(expectedTransactions.get(1).getAmount(),actualTransactions.get(1).getAmount(),0);
		Assert.assertEquals(expectedTransactions.get(0).getTimestamp(),actualTransactions.get(0).getTimestamp(),0);
		Assert.assertEquals(expectedTransactions.get(1).getTimestamp(),actualTransactions.get(1).getTimestamp(),0);
	}

}
