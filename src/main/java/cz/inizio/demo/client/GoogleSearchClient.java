package cz.inizio.demo.client;

import cz.inizio.demo.model.OrganicResult;
import java.util.List;

/** Outbound port: the application service does not depend on Google or an API vendor. */
public interface GoogleSearchClient {
    List<OrganicResult> search(String query);
}
