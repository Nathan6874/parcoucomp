package ParcourComp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class FifaApiService {

    @Value("${football.api.key}")
    private String apiKey;

    @Value("${football.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public FifaApiService() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> getWorldCupMatches() {
        String url = apiUrl + "/competitions/WC/matches?season=2026";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, Map.class
        );

        return response.getBody();
    }

    public List<Map<String, Object>> getMatchesByStage(String stage) {
        Map<String, Object> allMatches = getWorldCupMatches();
        List<Map<String, Object>> matches = (List) allMatches.get("matches");

        return matches.stream()
                .filter(m -> stage.equals(m.get("stage")))
                .toList();
    }

    public Map<String, List<Map<String, Object>>> getGroupedMatches() {
        Map<String, List<Map<String, Object>>> grouped = new HashMap<>();
        Map<String, Object> allMatches = getWorldCupMatches();
        List<Map<String, Object>> matches = (List) allMatches.get("matches");

        for (Map<String, Object> match : matches) {
            String group = (String) match.get("group");
            if (group != null && !group.isEmpty()) {
                grouped.computeIfAbsent(group, k -> new ArrayList<>()).add(match);
            }
        }
        return grouped;
    }
}