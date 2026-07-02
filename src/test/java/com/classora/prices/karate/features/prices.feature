Feature: Prices API

  Background:
    * url karate.properties['baseUrl']
    * path 'api', 'v1', 'prices'

  Scenario Outline: Test <case>: price at <applicationDate> for product 35455 and brand 1
    Given param applicationDate = '<applicationDate>'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 200
    And match response ==
      """
      {
        productId: 35455,
        brandId: 1,
        priceList: <priceList>,
        startDate: '<startDate>',
        endDate: '<endDate>',
        price: <price>,
        currency: 'EUR'
      }
      """

    Examples:
      | case | applicationDate     | priceList | price | startDate           | endDate             |
      | 1    | 2020-06-14T10:00:00 | 1         | 35.50 | 2020-06-14T00:00:00 | 2020-12-31T23:59:59 |
      | 2    | 2020-06-14T16:00:00 | 2         | 25.45 | 2020-06-14T15:00:00 | 2020-06-14T18:30:00 |
      | 3    | 2020-06-14T21:00:00 | 1         | 35.50 | 2020-06-14T00:00:00 | 2020-12-31T23:59:59 |
      | 4    | 2020-06-15T10:00:00 | 3         | 30.50 | 2020-06-15T00:00:00 | 2020-06-15T11:00:00 |
      | 5    | 2020-06-16T21:00:00 | 4         | 38.95 | 2020-06-15T16:00:00 | 2020-12-31T23:59:59 |

  Scenario: No tariff applies at the requested date
    Given param applicationDate = '2020-06-13T10:00:00'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 404
    And match response.status == 404

  Scenario: Missing parameter is rejected
    Given param applicationDate = '2020-06-14T10:00:00'
    And param productId = 35455
    When method get
    Then status 400

  Scenario: Malformed date is rejected
    Given param applicationDate = 'not-a-date'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 400
