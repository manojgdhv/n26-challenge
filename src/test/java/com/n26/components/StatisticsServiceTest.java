package com.n26.components;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.n26.models.Statistics;
import com.n26.models.Transaction;
import com.n26.services.StatisticsService;

public class StatisticsServiceTest {

	@Test
	public void testGetStatistics() {
		StatisticsService statisticsService = new StatisticsService();
	
		//Transaction for current time
		List<Transaction> transactions = new ArrayList<Transaction>();
		Transaction t1 = new Transaction();
		t1.setAmount(1000D);
		long currentTime = Instant.now().toEpochMilli();
		t1.setTimestamp(currentTime);
		
		//Transaction before 10 seconds
		Transaction t2 = new Transaction();
		t2.setAmount(2000D);
		t2.setTimestamp(currentTime - (10 * 1000));
		
		transactions.add(t1);
		transactions.add(t2);
		
		Statistics expectedStatistics = new Statistics();
		expectedStatistics.setAvg((t1.getAmount() + t2.getAmount())/2);
		expectedStatistics.setCount(2);
		expectedStatistics.setMax(t2.getAmount());
		expectedStatistics.setMin(t1.getAmount());
		expectedStatistics.setSum(t1.getAmount() + t2.getAmount());
		
		Statistics actualStatistics = statisticsService.getStatistics(transactions);
		
		Assert.assertEquals(expectedStatistics.getAvg(),actualStatistics.getAvg(),0);
		Assert.assertEquals(expectedStatistics.getCount(),actualStatistics.getCount(),0);
		Assert.assertEquals(expectedStatistics.getMax(),actualStatistics.getMax(),0);
		Assert.assertEquals(expectedStatistics.getMin(),actualStatistics.getMin(),0);
		Assert.assertEquals(expectedStatistics.getSum(),actualStatistics.getSum(),0);
	}

}
