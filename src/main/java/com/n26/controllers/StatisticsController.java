package com.n26.controllers;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.n26.components.facades.TransactionStatisticsFacade;
import com.n26.constants.Constants;
import com.n26.models.Statistics;

/**
 * Web service controller for Statistics operation
 * @author manojg
 *
 */
@Component
@Path(Constants.STATISTICS)
public class StatisticsController {
	@Autowired TransactionStatisticsFacade transactionStatisticsFacade;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Statistics getStatitistics(@DefaultValue(Constants.DEFAULT_LAST_N_SECONDS) @QueryParam(Constants.LAST_N_SECONDS_TRANSACTIONS_PARAM) long seconds) {
		return transactionStatisticsFacade.getStatisticsForLastNSeconds(seconds);
	}
} 