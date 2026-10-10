package cz.inizio.demo.client.impl;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoogleSearchClientImplTest {
    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    @Test
    void mapsOnlyOrganicResultsFromSerpApiResponse() throws Exception {
        var json = jsonMapper.readTree("""
                {
                  "organic_results": [
                    {"position": 1, "title": "Organic title", "link": "https://example.com", "snippet": "Description", "displayed_link": "example.com"}
                  ],
                  "ads": [{"title": "Paid ad", "link": "https://ad.example"}]
                }
                """);

        var results = GoogleSearchClientImpl.mapOrganicResults(json.path("organic_results"));

        assertEquals(1, results.size());
        assertEquals(1, results.get(0).position());
        assertEquals("Organic title", results.get(0).title());
        assertEquals("https://example.com", results.get(0).link());
        assertEquals("Description", results.get(0).snippet());
        assertEquals("example.com", results.get(0).displayedLink());
    }
}
