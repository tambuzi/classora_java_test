# Prices API

Spring Boot service that returns the price applicable to a product of a brand at a given date, resolving overlapping tariffs by highest priority. Backed by an in-memory H2 database seeded with the exercise's example data.

## Architecture

Hexagonal architecture with DDD building blocks and CQS (query side only — the service is read-only):

```
com.classora.prices
├── PricesApplication.java
├── domain
│   ├── entity
│   │   └── Price.java
│   ├── valueobject
│   │   ├── PriceId.java
│   │   ├── BrandId.java  ProductId.java  PriceList.java
│   │   └── Priority.java  Money.java  DateRange.java
│   ├── model
│   │   └── PriceFinder.java
│   ├── service
│   │   └── FindApplicablePriceService.java
│   └── exception
│       └── PriceNotFoundException.java
├── application
│   ├── bus
│   │   ├── Query.java
│   │   ├── QueryHandler.java
│   │   ├── QueryBus.java
│   │   └── NoQueryHandlerException.java
│   ├── query
│   │   ├── FindApplicablePriceQuery.java
│   │   ├── FindApplicablePriceQueryHandler.java
│   │   └── dto
│   │       └── FindApplicablePriceResult.java
│   └── exception
│       └── InvalidQueryException.java
└── infrastructure
    ├── userinterface/http
    │   ├── controllers
    │   │   └── FindApplicablePriceController.java
    │   ├── exception
    │   │   └── GlobalExceptionHandler.java
    │   ├── dto
    │   │   ├── FindApplicablePriceRequest.java
    │   │   └── FindApplicablePriceResponse.java
    │   └── routes
    │       └── Routes.java
    ├── persistence
    │   └── PriceDatabaseFinder.java
    ├── bus
    │   └── InMemoryQueryBus.java
    ├── mapper
    │   ├── PriceQueryMapper.java
    │   ├── PriceRestMapper.java
    │   └── PriceRowMapper.java
    └── config
        ├── ServiceBeanConfiguration.java
        └── QueryHandlerBeanConfiguration.java

src/main/resources
├── application.yml
├── schema.sql
└── data.sql
```


## API

```
GET /api/v1/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1
```

| Status | Body |
|--------|------|
| 200 | `{"productId":35455,"brandId":1,"priceList":1,"startDate":"2020-06-14T00:00:00","endDate":"2020-12-31T23:59:59","price":35.50,"currency":"EUR"}` |
| 404 | RFC-7807 problem detail when no tariff applies |
| 400 | RFC-7807 problem detail for missing/malformed/non-positive parameters |

Interactive API docs (springdoc-openapi) when the app is running: Swagger UI at `/swagger-ui/index.html`, spec at `/v3/api-docs`.

## Run

With Gradle (wrapper included, needs Java 21):

```bash
./gradlew bootRun
```

With Docker:

```bash
docker compose up --build
```

Try it:

```bash
curl "http://localhost:8080/api/v1/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

## Tests

```bash
./gradlew check
```

Tests are grouped by type under `src/test/java/com/classora/prices/`:

- **`unit/`** — JUnit 5 + Mockito + AssertJ for domain, application, mappers and error handling; a `@JdbcTest` slice that verifies the real SQL + `RowMapper` + seed scripts against H2; the `@SpringBootTest` + MockMvc integration test covering the 5 mandated cases plus error scenarios; and the ArchUnit architecture rules.
- **`cucumber/`** — split into `config/` (runner + Spring context), `steps/` (step definitions) and `features/` (`prices.feature`), executable Gherkin for the same cases.
- **`karate/`** — split into `config/` (the `KarateTestRunner`, which runs every feature in `features/`) and `features/` (`prices.feature`), black-box HTTP tests against the app on a random port.

`./gradlew check` runs them all in a single pass, plus **JaCoCo verification** — the build fails below 100% line and branch coverage (only the Spring Boot launcher class is excluded).

**Mutation testing** (on demand, kept out of `check` because it is slower):

```bash
./gradlew pitest   # report in build/reports/pitest — currently 100% mutation score on the core logic
```

**CI**: `.github/workflows/ci.yml` runs `./gradlew check` on every push and pull request to `main`.

Mandated cases:

| Test | Application date | Expected tariff | Expected price |
|------|------------------|-----------------|----------------|
| 1 | 2020-06-14 10:00 | 1 | 35.50 EUR |
| 2 | 2020-06-14 16:00 | 2 | 25.45 EUR |
| 3 | 2020-06-14 21:00 | 1 | 35.50 EUR |
| 4 | 2020-06-15 10:00 | 3 | 30.50 EUR |
| 5 | 2020-06-16 21:00 | 4 | 38.95 EUR |
