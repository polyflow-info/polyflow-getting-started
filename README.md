[![example](https://img.shields.io/badge/lifecycle-EXAMPLE-blue.svg)](https://github.com/holisticon#open-source-lifecycle)
[![CI for branches](https://github.com/polyflow-info/polyflow-getting-started/actions/workflows/ci.yml/badge.svg)](https://github.com/polyflow-info/polyflow-getting-started/actions/workflows/ci.yml)

![Logo](https://github.com/polyflow-info/.github/blob/388d58c75fce0fad0956f4c5b5ac8b22b6e3223d/profile/assets/Positive%402x.png)

## Getting Started with Polyflow

> Polyflow is a component library for building enterprise-wide process platforms with
> multiple process engines like Camunda Platform.



This repository is an example for settings and configurations to demonstrate the minimal
steps to integrate an existing process application based on Spring Boot with Polyflow.

For doing so, we used a simple example from Camunda Platform 7 - the load approval process.

<img src="src/main/resources/loanApproval.png" alt="Loan Approval" title="Loan Approval" width="400">


Fo more information on Camunda Platform 7 with Spring Boot please check the
official [Camunda getting started](https://docs.camunda.org/get-started/spring-boot/)
guide.

## How to build

```bash
./mvnw clean package
```

## How to start

```bash
./mvnw spring-boot:run
```

## How to start from IDE

Please run the `PolyflowMinimalApplication` inside your IDE.

## Access the H2 SQL Console

Open the following address in your browser: http://localhost:8080/h2-console/, click
on `Connect` and inspect the database tables available. The `PLF_`-prefixed tables
are parts of the Polyflow View part. For example to check for currently available tasks,
execute the following SQL statement:

```sql
SELECT *
FROM PLF_TASK;
```

### License

This library is developed under

[![Apache 2.0 License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](/LICENSE)

### Sponsors and Customers

[![sponsored](https://img.shields.io/badge/sponsoredBy-Holisticon-red.svg)](https://holisticon.de/)
