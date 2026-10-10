package cz.inizio.demo.service.impl;

import cz.inizio.demo.client.GoogleSearchClient;
import cz.inizio.demo.exception.InvalidQueryException;
import cz.inizio.demo.model.OrganicResult;
import cz.inizio.demo.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final GoogleSearchClient googleSearchClient;

    @Override
    public List<OrganicResult> search(String query) {
        String normalizedQuery = query == null ? "" : query.trim();
        if (normalizedQuery.isBlank()) throw new InvalidQueryException();
        return googleSearchClient.search(normalizedQuery);
    }
}
