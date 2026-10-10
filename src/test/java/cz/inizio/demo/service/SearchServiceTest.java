package cz.inizio.demo.service;

import cz.inizio.demo.client.GoogleSearchClient;
import cz.inizio.demo.exception.InvalidQueryException;
import cz.inizio.demo.model.OrganicResult;
import cz.inizio.demo.service.impl.SearchServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchServiceTest {
    @Test
    void trimsQueryAndDelegatesToProvider() {
        GoogleSearchClient fakeProvider = query -> List.of(new OrganicResult(1, "Title", "https://example.com", "Snippet", "example.com"));
        SearchServiceImpl service = new SearchServiceImpl(fakeProvider);
        var result = service.search("  test phrase  ");
        assertEquals("Title", result.get(0).title());
    }

    @Test
    void rejectsBlankQueryBeforeCallingProvider() {
        GoogleSearchClient neverCall = query -> fail("Provider should not be called for an empty query");
        SearchServiceImpl service = new SearchServiceImpl(neverCall);
        assertThrows(InvalidQueryException.class, () -> service.search("  "));
    }
}
