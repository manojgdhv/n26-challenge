# Code Challenge

RestFul API to generate last 60 seconds transaction details.

# Implementation

<b>Technogies used</b>

-Rest easy

-Spring

-Junit

-Java 8

Used rest easy implation of RestFul architecture along with spring 4. 

<b>Following are the requirement and implementation details</b>

<b>CR1:  Get statistics should always return in constant time and space</b>
Used ConcurrentSkipListMap which guarantees average O(log(n)) performance. Also provided methods like headMap(), subMap() and tailMap() by using which we can fetch topN or bottomN elements.

<b>CR2: API have to be thread safe </b>
ConcurrentSkipListMap assures thread safety . Along with it add transaction and get statistics methods are also synchronised to achieve thread safety. 

<b>CR3: Time discrepancy should be handled</b>
ConcurrentSkipListMap stores transaction in a sorted manner, so any transaction is added we can expect data sorted based timestamp.

<b>CR4: Code should be testable</b>
Added  JUnit test cases for controllers, facade and services.

# How to run 

<b>API Specification</b>

1. Add transaction
	RestFul API responsible for add transaction

	Request Method: POST

	Request URI: http://<BASE_PATH>/webservices/ transactions

	Request body:

	{
		"timestamp":1592246487000,
		"amount":5000
	}

2. Get statistic
	API to get transaction statistics for last 60 seconds
		
	Request Method: GET

	Request URI: http://<BASE_PATH>/webservices/statistics?last_n_seconds_transactions=<no_last_transactions>

	Query param: last_n_seconds_transactions default to 60 seconds if not provided

	Request body:

	{
	  "count": 2,
	  "max": 5000,
	  "min": 5000,
	  "sum": 10000,
	  "avg": 5000
	}

<b>Maven commands</b>

	1. Build : mvn clean install

	2. Test : mvn test
