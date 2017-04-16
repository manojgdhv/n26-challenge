package com.n26.controllers;

import java.time.Instant;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

import com.n26.constants.Constants;
import com.n26.models.Statistics;
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

public class StatiticsControllerTest extends JerseyTest {

	private WebResource webResource;

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
	
	@Before
	public void setup(){
		webResource = resource();
	}
	
	private ClientResponse callGetStatistics() {
		ClientResponse response = webResource.path(Constants.STATISTICS).get(ClientResponse.class);
		return response;
	}
	
	private ClientResponse callAddTransaction(Transaction transaction) {
		ClientResponse transactionResponse = webResource.path(Constants.TRANSACTIONS).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, transaction);
		return transactionResponse;
	}
	
	@Test
	public void testStatisticsNoContent() throws Exception {
		ClientResponse response = callGetStatistics();
		Assert.assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	
	@Test
	public void testStatisticsOkStatus() throws Exception {
		Transaction transaction = new Transaction();
		transaction.setAmount(1000D);
		transaction.setTimestamp(Instant.now().toEpochMilli());
		
		callAddTransaction(transaction);
		
		ClientResponse response = callGetStatistics();
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testStatisticsBeforeSixtySeconds() throws Exception {
		//Transaction under last 60 seconds
		Transaction transaction1 = new Transaction();
		transaction1.setAmount(1000D);
		transaction1.setTimestamp(Instant.now().toEpochMilli());
		
		callAddTransaction(transaction1);

		//Transaction before last 60 seconds
		Transaction transaction2 = new Transaction();
		transaction2.setAmount(1000D);
		transaction2.setTimestamp(Instant.now().toEpochMilli() - (61 * 1000));
		
		callAddTransaction(transaction2);

		Statistics response = callGetStatistics().getEntity(Statistics.class);
		//Statistics should consider only consider last 60 seconds transaction
		Assert.assertEquals(1, response.getCount());
	}
	
	@Test
	public void testStatistics() throws Exception {
		Transaction transaction1 = new Transaction();
		transaction1.setAmount(1000D);
		transaction1.setTimestamp(Instant.now().toEpochMilli());
		
		callAddTransaction(transaction1);

		Transaction transaction2 = new Transaction();
		transaction2.setAmount(2000D);
		transaction2.setTimestamp(Instant.now().toEpochMilli() - (10 * 1000));
		
		callAddTransaction(transaction2);
		
		Statistics response = callGetStatistics().getEntity(Statistics.class);
		Assert.assertEquals(2, response.getCount());
		Assert.assertEquals((transaction1.getAmount() + transaction2.getAmount())/2, response.getAvg());
		Assert.assertEquals(transaction2.getAmount(), response.getMax());
		Assert.assertEquals(transaction1.getAmount(), response.getMin());
		Assert.assertEquals((transaction1.getAmount() + transaction2.getAmount()), response.getSum());
	}

}
