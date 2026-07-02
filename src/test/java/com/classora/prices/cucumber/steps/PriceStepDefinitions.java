package com.classora.prices.cucumber.steps;

import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceStepDefinitions {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private ResponseEntity<JsonNode> response;

    @When("I request the price for product {long} and brand {long} at {string}")
    public void iRequestThePrice(Long productId, Long brandId, String applicationDate) {
        String uri = UriComponentsBuilder.fromPath("/api/v1/prices")
                .queryParam("applicationDate", applicationDate)
                .queryParam("productId", productId)
                .queryParam("brandId", brandId)
                .toUriString();
        response = testRestTemplate.getForEntity(uri, JsonNode.class);
    }

    @Then("the response status is {int}")
    public void theResponseStatusIs(int status) {
        assertThat(response.getStatusCode().value()).isEqualTo(status);
    }

    @Then("the applied tariff is {long} with price {bigdecimal} {string}")
    public void theAppliedTariffIs(Long priceList, BigDecimal price, String currency) {
        JsonNode body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.get("priceList").asLong()).isEqualTo(priceList);
        assertThat(new BigDecimal(body.get("price").asText())).isEqualByComparingTo(price);
        assertThat(body.get("currency").asText()).isEqualTo(currency);
    }

    @Then("the tariff validity goes from {string} to {string}")
    public void theTariffValidityGoesFrom(String startDate, String endDate) {
        JsonNode body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.get("startDate").asText()).isEqualTo(startDate);
        assertThat(body.get("endDate").asText()).isEqualTo(endDate);
    }
}
