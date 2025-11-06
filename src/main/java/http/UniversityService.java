package http;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class UniversityService {
    private static final Logger log = Logger.getLogger(UniversityService.class.getName());
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public UniversityService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public List<University> getUniversity() throws UniversityHTTPException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("http://universities.hipolabs.com/search?name=middle"))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new UniversityHTTPException("Error Http: " + response.statusCode());
            }

            String data = response.body();
            log.info(String.format("Get json: %s", data));
            return objectMapper.readValue(data, new TypeReference<List<University>>() {
            });
        } catch (Exception e) {
            log.warning(String.format("Error of getting: %s", e));
            throw new UniversityHTTPException("Error: " + e.getMessage(), e);
        }
    }
}
