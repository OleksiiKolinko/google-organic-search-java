package cz.inizio.demo.config;

import cz.inizio.demo.client.GoogleSearchClient;
import cz.inizio.demo.client.impl.GoogleSearchClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class GoogleHttpClientConfig {
    @Bean
    RestClient serpApiRestClient() {
        return RestClient.builder()
                .baseUrl("https://serpapi.com")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    GoogleSearchClient googleSearchClient(RestClient serpApiRestClient,
                                          @Value("${serpapi.api-key:}") String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Set SERPAPI_KEY or serpapi.api-key in application.properties.");
        }
        return new GoogleSearchClientImpl(serpApiRestClient, apiKey);
    }

}
