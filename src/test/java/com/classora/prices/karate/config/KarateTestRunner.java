package com.classora.prices.karate.config;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KarateTestRunner {

    @LocalServerPort
    private int port;

    @Karate.Test
    Karate allFeatures() {
        return Karate.run("classpath:com/classora/prices/karate/features")
                .systemProperty("baseUrl", "http://localhost:" + port);
    }
}
