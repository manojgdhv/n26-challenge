package com.n26.components.facades;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;

import com.n26.models.Statistics;
import com.n26.models.Transaction;
import com.n26.services.StatisticsService;
import com.n26.services.TransactionService;

public class TransactionStatisticsFacadeTest {
	
	@Test
	public void testGetStatisticsForLastNSeconds() {
		long currentTimeInMillis = Instant.now().toEpochMilli();
		
		//Last 60 seconds transaction
		Transaction t1 = new Transaction();
		t1.setAmount(1000D);
		t1.setTimestamp(currentTimeInMillis);
		
		//Transaction before 60 seconds
		long timeBeforeSixtySeconds = currentTimeInMillis - (61 * 1000);
		Transaction t2 = new Transaction();
		t2.setAmount(2000D);
		t2.setTimestamp(timeBeforeSixtySeconds);
		
		TransactionStatisticsFacade facade = new TransactionStatisticsFacade();
		facade.setStatisticsService(new StatisticsService());
		facade.setTransactionService(new TransactionService());
		facade.addTransaction(t1);
		facade.addTransaction(t2);
		
		Statistics actualStatistics = facade.getStatisticsForLastNSeconds(60);
		
		//Result should contain only one transaction which is under last 60 seconds
		Assert.assertNotNull(actualStatistics);
		
		Assert.assertEquals(t1.getAmount(),actualStatistics.getAvg(),0);
		Assert.assertEquals(1,actualStatistics.getCount(),0);
		Assert.assertEquals(t1.getAmount(),actualStatistics.getMax(),0);
		Assert.assertEquals(t1.getAmount(),actualStatistics.getMin(),0);
		Assert.assertEquals(t1.getAmount(),actualStatistics.getSum(),0);
	}

}
