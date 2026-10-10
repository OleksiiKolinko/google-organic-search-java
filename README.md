# Google Organic Results

Java 17 / Spring Boot application that searches Google through SerpApi, returns only the `organic_results` list and lets the browser download it as JSON or CSV.

## Configure and run

Set your SerpApi key in the environment variable `SERPAPI_KEY`, or set `serpapi.api-key` in `src/main/resources/application.properties`. Do not commit a real key or include it in a source archive.

```bash
mvn spring-boot:run
```

Open http://localhost:8080. For Docker, set `SERPAPI_KEY` in the environment and run `docker compose up --build`.

## Deploy to Render

The included `render.yaml` defines a Docker web service and prompts for `SERPAPI_KEY` as a secret during Blueprint setup. Push this project to a GitHub repository, then in Render choose **New → Blueprint** and select that repository. Enter the SerpApi key when prompted and create the service. Render will build the Dockerfile and provide an `onrender.com` URL after deployment. The free plan may spin down after inactivity, so the first request can take longer.

## Tests

```bash
mvn test
```

Tests verify organic-result mapping and query validation. No live SerpApi request is made during tests.

## Request flow

1. `index.html` calls `GET /api/search?query=...`.
2. `SearchController` delegates to `SearchService`; `SearchServiceImpl` validates the query.
3. `GoogleSearchClientImpl` calls SerpApi's Google search endpoint and maps `organic_results` to `OrganicResult` objects.
4. The controller returns JSON; the browser renders the results and creates JSON/CSV downloads locally.

The API key stays on the backend. `GoogleHttpClientConfig` creates the shared `RestClient` and the `GoogleSearchClient` bean once at startup. Lombok `@RequiredArgsConstructor` is used for constructor injection.

## Package structure

```text
cz.inizio.demo
├── client                 # GoogleSearchClient interface
│   └── impl               # GoogleSearchClientImpl (SerpApi adapter)
├── config                 # RestClient and adapter beans
├── controller             # HTTP endpoint
├── exception              # Exceptions and HTTP error mapping
├── model                  # OrganicResult
└── service                # SearchService interface and implementation
```
