package com.n26.controllers;
 
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.n26.components.facades.TransactionStatisticsFacade;
import com.n26.constants.Constants;
import com.n26.models.Transaction;
 
/**
 * Web service controller for Transaction related operation
 * @author manojg
 *
 */
@Component
@Path(Constants.TRANSACTIONS)
public class TransactionController {
	@Autowired TransactionStatisticsFacade transactionStatisticsFacade;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addTransaction(Transaction transaction){
		transactionStatisticsFacade.addTransaction(transaction);
	}
}