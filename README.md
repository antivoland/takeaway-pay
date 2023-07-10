# Takeaway Pay

[![Build Status](https://github.com/antivoland/jet-test/workflows/build/badge.svg)](https://github.com/antivoland/jet-test/actions/workflows/build.yml)

The task description can be found [here](TASK.md). We need to handle payments between customers and restaurants. For this I implemented the transactional [method](src/main/kotlin/antivoland/jet/service/CustomerService.kt#L30) utilizing optimistic locking on updates. And there are also a simple [schema](src/main/resources/schema.sql) and some [data](src/main/resources/data.sql) that are used both at runtime and in the [main test](src/test/kotlin/antivoland/jet/SlurmessoTest.kt).

Run the application as follows, for instance:

```shell
mvn spring-boot:run
```

Let's now make a payment:

```shell
curl -i -X POST localhost:8080/customers/fry/pay \
  -H "Content-Type: application/json" \
  -d '{"restaurant_id": "slurm-dispensing-unit", "amount": 0.25}'

HTTP/1.1 201 
Content-Length: 0
Date: Sun, 09 Jul 2023 16:27:06 GMT
```

Then check the customer's balance:

```shell
curl -i localhost:8080/customers/fry/balance

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 09 Jul 2023 16:28:59 GMT

9.75
```

The restaurant's balance is now updated as well:

```shell
curl -i localhost:8080/restaurants/slurm-dispensing-unit/balance

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 09 Jul 2023 16:30:26 GMT

0.25
```

Swagger docs are available at http://localhost:8080/api.