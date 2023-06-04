# Cash Dispensing Application

A Cash Dispensing Application is a Java-based application that simulates a cash dispenser used in an ATM or similar device.

## Features

- Initialize the cash dispenser with the initial count of $20 and $50 notes.
- Add additional $20 and $50 notes to the cash dispenser.
- Remove $20 and $50 notes from the cash dispenser.
- Check the availability of $20 and $50 notes in the cash dispenser.
- Dispense cash based on requested amounts.

## Technologies Used

- Java
- Spring Boot
- Maven

## Libraries Used

- Spring Web: For building the RESTful API endpoints.
- Spring Data JPA: For data access and persistence.
- Hibernate Validator: For validating request payloads.
- JUnit 5: For unit testing.
- Mockito: For mocking dependencies in unit tests.

## Prerequisites

- Java JDK 17 or above
- Maven 3.9

## Getting Started

1. Clone the repository:
git clone https://github.com/chaminiPrashakthi/cashDispenser/tree/master

2. Build the application using Maven:
cd cash-dispenser-application
mvn clean package

3. Run the application:

java -jar target/cash-dispenser-application.jar
4. Access the application at `http://localhost:8080/api/cashdispenser`.

## API Endpoints

- `POST /api/cashdispenser/initialize`: Initialize the cash dispenser with the initial count of $20 and $50 notes.
- `GET /api/cashdispenser/available`: Get the available count of $20 and $50 notes in the cash dispenser.
- `POST /api/cashdispenser/add`: Add additional $20 and $50 notes to the cash dispenser.
- `POST /api/cashdispenser/remove`: Remove $20 and $50 notes from the cash dispenser.
- `POST /api/cashdispenser/dispense?amount=<requested_amount>`: Dispense cash based on the requested amount.

## Unit Testing

Unit tests have been implemented for the service and controller classes. To run the unit tests, use the following command:
mvn test
