# Code Challenge

RestFul API to generate last 60 seconds transaction details.

# Implementation
Technogies used
-Rest easy
-Spring
-Junit
-Java 8

Used rest easy implation of RestFul architecture along with spring 4. 

Following are the requirement and implementation details

CR1:  Get statistics should always return in constant time and space
Used ConcurrentSkipListMap which guarantees average O(log(n)) performance. Also provided methods like headMap(), subMap() and tailMap() by using which we can fetch topN or bottomN elements.

CR2: API have to be thread safe 
ConcurrentSkipListMap assures thread safety . Along with it add transaction and get statistics methods are also synchronised to achieve thread safety. 

CR3: Time discrepancy should be handled
ConcurrentSkipListMap stores transaction in a sorted manner, so any transaction is added we can expect data sorted based timestamp.

CR4: Code should be testable
Added  JUnit test cases for controllers, facade and services.

# How to run 

<sub>API Specification</sub>

1. Add transaction
RestFul API responsible for add transaction

Request Method: POST

Request URI: http://<BASE_PATH>/webservices/ transactions

Request body:

{
	"timestamp":1592246487000,
	"amount":5000
}

2. Get statistics

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

<sub>Maven commands</sub>

1.  Build : mvn clean install

2. Test : mvn test
