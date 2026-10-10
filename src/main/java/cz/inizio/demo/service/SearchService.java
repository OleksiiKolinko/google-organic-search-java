package cz.inizio.demo.service;

import cz.inizio.demo.model.OrganicResult;
import java.util.List;

public interface SearchService {
    List<OrganicResult> search(String query);
}
