Feature: Applicable price query
  As an e-commerce client
  I want to know the price that applies to a product at a given moment
  So that I can show the correct final price

  Scenario Outline: Return the applicable tariff for the mandated test cases
    When I request the price for product 35455 and brand 1 at "<applicationDate>"
    Then the response status is 200
    And the applied tariff is <priceList> with price <price> "EUR"
    And the tariff validity goes from "<startDate>" to "<endDate>"

    Examples:
      | applicationDate     | priceList | price | startDate           | endDate             |
      | 2020-06-14T10:00:00 | 1         | 35.50 | 2020-06-14T00:00:00 | 2020-12-31T23:59:59 |
      | 2020-06-14T16:00:00 | 2         | 25.45 | 2020-06-14T15:00:00 | 2020-06-14T18:30:00 |
      | 2020-06-14T21:00:00 | 1         | 35.50 | 2020-06-14T00:00:00 | 2020-12-31T23:59:59 |
      | 2020-06-15T10:00:00 | 3         | 30.50 | 2020-06-15T00:00:00 | 2020-06-15T11:00:00 |
      | 2020-06-16T21:00:00 | 4         | 38.95 | 2020-06-15T16:00:00 | 2020-12-31T23:59:59 |

  Scenario: No tariff applies at the requested date
    When I request the price for product 35455 and brand 1 at "2020-06-13T10:00:00"
    Then the response status is 404

  Scenario: The request is rejected when the date is malformed
    When I request the price for product 35455 and brand 1 at "not-a-date"
    Then the response status is 400
