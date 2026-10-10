package cz.inizio.demo.client.impl;

import cz.inizio.demo.client.GoogleSearchClient;
import cz.inizio.demo.model.OrganicResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.ArrayList;

@RequiredArgsConstructor
public class GoogleSearchClientImpl implements GoogleSearchClient {

    private static final int MAX_RESULTS = 10;
    private final RestClient serpApiRestClient;
    private final String apiKey;
    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    @Override
    public List<OrganicResult> search(String query) {
        String response = serpApiRestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search.json")
                        .queryParam("engine", "google")
                        .queryParam("q", query)
                        .queryParam("api_key", apiKey)
                        .queryParam("num", MAX_RESULTS)
                        .queryParam("hl", "cs")
                        .queryParam("gl", "cz")
                        .build())
                .retrieve()
                .body(String.class);

        if (response == null || response.isBlank()) {
            return List.of();
        }
        try {
            JsonNode json = jsonMapper.readTree(response);
            if (json.hasNonNull("error")) {
                throw new IllegalStateException("SerpApi error: " + json.path("error").asText());
            }
            return mapOrganicResults(json.path("organic_results"));
        } catch (tools.jackson.core.JacksonException exception) {
            throw new IllegalStateException("SerpApi returned an invalid JSON response.", exception);
        }
    }

    static List<OrganicResult> mapOrganicResults(JsonNode organicResults) {
        List<OrganicResult> results = new ArrayList<>();
        if (organicResults == null || !organicResults.isArray()) return results;

        for (JsonNode item : organicResults) {
            if (results.size() == MAX_RESULTS) break;
            results.add(new OrganicResult(
                    item.path("position").asInt(results.size() + 1),
                    item.path("title").asText(""),
                    item.path("link").asText(""),
                    item.path("snippet").asText(""),
                    item.path("displayed_link").asText("")));
        }
        return List.copyOf(results);
    }
}
