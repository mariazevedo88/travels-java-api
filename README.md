# finance-java-api

A financial API for managing transactions

## How API works?

The API creates, updates, deletes and lists financial transactions. It also calculates statistics of the transactions created. The API has following endpoints:

`POST/transaction` – endpoint to create a transaction.

`PUT/transaction` – endpoint to update a transaction.

`GET/transactions` – returns the transactions created.

`DELETE/transactions` – deletes all transactions.

`GET/statistics` – returns the statistic based of the transactions created.

### Details

`POST/transaction`

This endpoint is called to create a new transaction.

**Body:**

<code>
{
  "id": 1,
  "nsu": "220788",
  "autorizationNumber": "010203",
  "amount": "22.88",
  "transactionDate": "2019-09-11T09:59:51.312Z"
}
</code>

**Where:**

`id` - transaction id; 

`nsu` - identification number of a sales transaction using cards. May be null if transaction was paid in cash;

`autorizationNumber` - is a one-time code used in the processing of online transactions;

`amount` – transaction amount; a string of arbitrary length that is parsable as a BigDecimal;

`transactionDate` – transaction time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local timezone.

Returns an empty body with one of the following:

201 – in case of success
400 – if the JSON is invalid
422 – if any of the fields are not parsable or the transaction date is in the future

`PUT/transaction/{id}`

This endpoint is called to update a transaction.

**Body:**

<code>
{
  "amount": "30.06"
}
</code>

Must be submitted or transaction id and the fields that will be modified. Must return a transaction specified by ID and all fields recorded above, including the one that was updated.

<code>
{   
   "id": 1,
   "nsu": "220788",
   "autorizationNumber": "010203",
   "amount": "30.06",
   "transactionDate": "2019-09-11T09:59:51.312Z"
}
</code>

`GET/transactions`

This endpoint returns all transactions created.

`DELETE/transactions`

This endpoint causes all existing transactions to be deleted, accepting an empty request body and return a 204 status code.

`GET/statistics`

This endpoint returns the statistics based on the transactions created.

**Returns:**

<code>
{
  "sum": "150.06",
  "avg": "75.3",
  "max": "120.0",
  "min": "30.06",
  "count": 2
}
</code>
 
**Where:**

`sum` – a BigDecimal specifying the total sum of transaction value.

`avg` – a BigDecimal specifying the average amount of transaction value.

`max` – a BigDecimal specifying single highest transaction value.

`min` – a BigDecimal specifying single lowest transaction value.

`count` – a long specifying the total number of transactions.

All BigDecimal values always contain exactly two decimal places, eg: 15.385 is returned as 15.39.

### Test

* For both test phases, you can run:

```
mvn test
```

### Run

```
mvn spring-boot:run -Dspring.profiles.active=prod
```

By default, the API will be available at [http://localhost:8080/](http://localhost:8080/)
