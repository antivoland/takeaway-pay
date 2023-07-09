# Takeaway Pay

[![Build Status](https://github.com/antivoland/jet-test/workflows/build/badge.svg)](https://github.com/antivoland/jet-test/actions/workflows/build.yml)

The task description can be found [here](TASK.md). We need to handle payments between customers and restaurants. For this I implemented the transactional [method](src/main/kotlin/antivoland/jet/service/CustomerService.kt#L30) utilizing optimistic locking on updates.

There is a simple [schema](src/main/resources/schema.sql) and some [data](src/main/resources/data.sql) that are used both at runtime and in tests.

## Run

Run the application as follows, for instance:

```shell
mvn spring-boot:run
```