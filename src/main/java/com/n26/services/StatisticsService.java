package com.n26.services;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.n26.models.Statistics;
import com.n26.models.Transaction;

/**
 * Component responsible for converting transaction to summary and the to statistics
 * @author manojg
 *
 */
@Service("statisticsService")
public class StatisticsService{

	public synchronized Statistics getStatistics(List<Transaction> transactions) {
		if (transactions == null || transactions.isEmpty()) {
			return null;
		}

		DoubleSummaryStatistics summaryStatitistics = transactions.parallelStream()
				.mapToDouble((transaction) -> transaction.getAmount()).summaryStatistics();
		
		return convertSummaryToStatistics(summaryStatitistics);
	}

	private Statistics convertSummaryToStatistics(DoubleSummaryStatistics summaryStatitistics) {
		Function<DoubleSummaryStatistics, Statistics> statistics = new Function<DoubleSummaryStatistics, Statistics>() {
			public Statistics apply(DoubleSummaryStatistics summary) {
				Statistics statistics = new Statistics();
				statistics.setAvg(Double.valueOf(summary.getAverage()));
				statistics.setCount(summary.getCount());
				statistics.setMax(Double.valueOf(summary.getMax()));
				statistics.setMin(Double.valueOf(summary.getMin()));
				statistics.setSum(Double.valueOf(summary.getSum()));
				return statistics;
			}
		};

		return statistics.apply(summaryStatitistics);
	}

}
