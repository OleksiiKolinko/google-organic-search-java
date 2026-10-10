package cz.inizio.demo.controller;

import cz.inizio.demo.model.OrganicResult;
import cz.inizio.demo.service.SearchService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/search")
    public List<OrganicResult> search(@RequestParam String query) {
        return searchService.search(query);
    }
}
