# travels-java-api

[![Build Status](https://travis-ci.org/mariazevedo88/travels-java-api.svg?branch=master)](https://travis-ci.org/mariazevedo88/travels-java-api) ![GitHub forks](https://img.shields.io/github/forks/mariazevedo88/travels-java-api?style=social) ![GitHub release (latest by date)](https://img.shields.io/github/v/release/mariazevedo88/travels-java-api) ![GitHub language count](https://img.shields.io/github/languages/count/mariazevedo88/travels-java-api) ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/mariazevedo88/travels-java-api) ![GitHub repo size](https://img.shields.io/github/repo-size/mariazevedo88/travels-java-api) ![GitHub last commit](https://img.shields.io/github/last-commit/mariazevedo88/travels-java-api) ![GitHub](https://img.shields.io/github/license/mariazevedo88/travels-java-api)

## About the API

An API for travel management. It is built with Java, Spring Boot, and Spring Framework. A toy-project to serve as a theoretical basis for the Medium series of articles I wrote about Java+Spring. The API main URL `/api-travels/v1`.

## Features

This API provides HTTP endpoint's and tools for the following:

* Create a trip: `POST/api-travels/v1/travels`
* Update a trip: `PUT/api-travels/v1/travels`
* Delete a trip (by id): `DELETE/api-travels/v1/travels/1`
* Get report of travels in a period of time (sorted and paginated): `GET/api-travels/v1/travels?startDate=2020-01-01&endDate=2020-09-20&page=2&size=5&sort=DESC`
* Find a unique trip by id: `GET/api-travels/v1/travels/1`
* Find a unique trip by id, but filtering JSON fields: `GET/api-travels/v1/travels/1?fields=id,orderNumber,startDate,amount`
* Find a trip by the order number (unique id on the system): `GET/api-travels/v1/travels/byOrderNumber/{orderNumber}`
* Get Statistics about the travels of the API: `GET/api-travels/v1/statistics`

### Details

`POST/api-travels/v1/travels`

This end-point is called to create a new trip.

**Body:**

```json
{
  "orderNumber": "123456",
  "amount": "22.88",
  "startDate": "2020-04-05T09:59:51.312Z",
  "endDate": "2020-04-15T16:25:00.500Z",
  "type": "RETURN"
}
```

**Where:**

`id` - travel id. It is automatically generated.

`orderNumber` - identification number of a trip on the system.

`amount` – travel amount; a string of arbitrary length that is parsable as a BigDecimal;

`startDate` – travel start date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone.

`endDate` – end date of the trip in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone.

`type` - travel type: ONE-WAY, RETURN or MULTI-CITY.

`links` - self-linking URL for the travel. It is automatically generated.

Returns an empty body with one of the following:

* 201 - Created: Everything worked as expected.
* 400 - Bad Request: the request was unacceptable, often due to missing a required parameter or invalid JSON.
* 404 - Not Found: The requested resource doesn't exist.
* 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
* 422 – Unprocessable Entity: if any of the fields are not parsable or the start date is greater than the end date.
* 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
* 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).

`PUT/api-travels/v1/travels/{id}`

This end-point is called to update a trip.

**Body:**

```json
{
   "id": 1,
   "orderNumber": "123456",
   "amount": "30.06",
   "startDate": "2020-04-05T09:59:51.312Z",
   "endDate": "2020-04-15T16:25:00.500Z",
   "type": "RETURN"
}
```

Must be submitted the object that will be modified. Must return a trip specified by ID and all fields recorded above, including links and the one that was updated.

```json
{
   "data": {   
   		"id": 1,
   		"orderCode": "123456",
   		"amount": "30.06",
   		"startDate": "2020-04-05T09:59:51.312Z",
  		"endDate": "2020-04-15T16:25:00.500Z",
  		"type": "RETURN",
   		"links": [
			{
			"rel": "self",
				"href": "http://localhost:8080/api-travels/v1/travels/1"
			}
   		]
	}
}
```

`GET/api-travels/v1/travels?startDate=2020-01-01&endDate=2020-01-18&page=2&size=5&sort=DESC`

The end-point returns travels were created within the period specified in the request. E.g., in the above query, we are looking for all travels carried out between 01-18 January 2020. Also, the result should return in descending order and only page 2
with five trips.

`DELETE/api-travels/v1/travels/{id}`

This end-point causes a specific id to be deleted, accepting an empty request body and returning a 204 status code.

**Where:**

`id` - travel id to be deleted.

`GET/api-travels/v1/statistics`

This end-point returns the statistics based on the travels created.

**Returns:**

```json
{
	"data": { 
  		"sum": "150.06",
  		"avg": "75.3",
  		"max": "120.0",
  		"min": "30.06",
  		"count": 2,
  		"links": [
			{
			"rel": "self",
				"href": "http://localhost:8080/api-travels/v1/statistics/1"
			}
   		]
   	}
}
```
 
**Where:**

`sum` – a BigDecimal specifying the total sum of the amount of all travels.

`avg` – a BigDecimal specifying the average of the amount of all travels.

`max` – a BigDecimal specifying single highest travel value.

`min` – a BigDecimal specifying single lowest travel value.

`count` – a long specifying the total number of travels.

`links` - self-linking URL for the statistic. It is automatically generated.

All `BigDecimal` values always contain exactly two decimal places, e.g: `15.385` is returned as `15.39`.

### Technologies used

This project was developed with:

* **Java 11 (Java Development Kit - JDK: 11.0.9)**
* **Spring Boot 2.3.7**
* **Spring Admin Client 2.3.1**
* **Maven**
* **JUnit 5**
* **Surfire**
* **PostgreSQL 13**
* **Flyway 6.4.4**
* **Swagger 3.0.0**
* **Model Mapper 2.3.9**
* **Heroku**
* **EhCache**
* **Bucket4j 4.10.0**
* **Partialize 20.05**

### Compile and Package

The API also was developed to run with an `jar`. In order to generate this `jar`, you should run:

```bash
mvn package
```

It will clean, compile and generate a `jar` at target directory, e.g. `travels-java-api-5.0.1-SNAPSHOT.jar`

### Execution

You need to have **PostgreSQL 9.6.17 or above** installed on your machine to run the API on `dev` profile. After installed, on the `pgAdmin` create a database named `travels`. If you don't have `pgAdmin` installed you can run on the `psql` console the follow command:

```sql
CREATE database travels;
```

After creating the API database, you need to add your **Postgres** root `username` and `password` in the `application.properties` file on `src/main/resource`. The lines that must be modified are as follows:

```properties
spring.datasource.username=
spring.datasource.password=
```

When the application is running **Flyway** will create the necessary tables for the creation of the words and the execution of the compare between the end-points. In the test profile, the application uses **H2** database (data in memory).

### Test

* For unit test phase, you can run:

```bash
mvn test
```

* To run all tests (including Integration Tests):

```bash
mvn integration-test
```

### Run

In order to run the API, run the jar simply as following:

```bash
java -jar travels-java-api-5.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```
    
or

```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

By default, the API will be available at [http://localhost:8080/api-travels/v1](http://localhost:8080/api-travels/v1)

### Documentation

* Swagger (development environment): [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

#### Medium Articles

* [Construindo uma API RESTful com Java e Spring Framework— Parte 1 (PT-BR)](https://medium.com/@mari_azevedo/construindo-uma-api-restful-com-java-e-spring-framework-46b74371d107)
* [Construindo uma API RESTful com Java e Spring Framework— Parte 2 (PT-BR)](https://medium.com/@mari_azevedo/construindo-uma-api-restful-com-java-e-spring-framework-parte-2-7a6c3e2ad453)
* [Construindo uma API RESTful com Java e Spring Framework— Parte 3 (PT-BR)](https://medium.com/@mari_azevedo/construindo-uma-api-restful-com-java-e-spring-framework-parte-3-ab34fcc00dee)
* [Construindo uma API RESTful com Java e Spring Framework— Parte 4 (PT-BR)](https://medium.com/@mari_azevedo/construindo-uma-api-restful-com-java-e-spring-framework-parte-4-6287f68ffc3c)
* [Building a RESTful API with Java and Spring Framework — Part 1 (EN)](https://mari-azevedo.medium.com/building-a-restful-api-with-java-and-spring-framework-part-1-6c364a885831)

### License

This API is licensed under the MIT License.
