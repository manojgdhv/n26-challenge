package com.n26.components.facades;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.models.Statistics;
import com.n26.models.Transaction;
import com.n26.services.StatisticsService;
import com.n26.services.TransactionService;

/**
 * Facade responsible for managing transaction and statistics.
 * @author manojg
 *
 */
@Service("transactionStatisticsFacade")
public class TransactionStatisticsFacade {
	private @Autowired TransactionService transactionService;
	private @Autowired StatisticsService statisticsService;

	public Statistics getStatisticsForLastNSeconds(long seconds) {
		Long timeBeforeLastNSeconds = getTimeBeforeLastNSeconds(seconds);
		List<Transaction> transactions = transactionService.getTransactions().
				tailMap(timeBeforeLastNSeconds).values().
				stream().flatMap(transaction -> transaction.stream()).
				collect(Collectors.toList());

		return statisticsService.getStatistics(transactions);
	}

	public void addTransaction(Transaction transaction) {
		transactionService.addTransaction(transaction);		
	}

	private Long getTimeBeforeLastNSeconds(long seconds){
		return Instant.now().minusSeconds(seconds).toEpochMilli();
	}

	//For JUnit test purpose
	public void setStatisticsService(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	//For JUnit test purpose
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
}
