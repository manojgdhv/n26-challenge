package com.n26.controllers;

import java.time.Instant;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

import com.n26.constants.Constants;
import com.n26.models.Transaction;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

import junit.framework.Assert;

public class TransactionControllerTest extends JerseyTest {

	@Override
	protected AppDescriptor configure() {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE );

		WebAppDescriptor descriptor = new WebAppDescriptor.Builder("com.n26.controllers")
			.contextParam("contextConfigLocation", "classpath:/applicationContext.xml")
			.contextPath("/n26").servletClass(SpringServlet.class).initParam("com.sun.jersey.api.json.POJOMappingFeature", "true")
			.contextListenerClass(ContextLoaderListener.class)
			.requestListenerClass(RequestContextListener.class)
			.clientConfig(clientConfig)
			.build();
		
		return descriptor;
	}
	
	private ClientResponse callAddTransaction(Transaction transaction) {
		WebResource webResource = resource();
		ClientResponse transactionResponse = webResource.path(Constants.TRANSACTIONS).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, transaction);
		return transactionResponse;
	}

	@Test
	public void testTransactionStatus() throws Exception {
		Transaction transaction = new Transaction();
		transaction.setAmount(1000D);
		transaction.setTimestamp(Instant.now().toEpochMilli());
		
		ClientResponse transactionResponse = callAddTransaction(transaction);
		Assert.assertEquals(Status.OK.getStatusCode(), transactionResponse.getStatus());
	}
	
	@Test
	public void testTransactionStatusBadRequest() throws Exception {
		//Future transaction
		Transaction transaction = new Transaction();
		transaction.setAmount(1000D);
		transaction.setTimestamp(Instant.now().toEpochMilli() + (10 * 1000));
		
		ClientResponse transactionResponse = callAddTransaction(transaction);
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), transactionResponse.getStatus());
	}
}
