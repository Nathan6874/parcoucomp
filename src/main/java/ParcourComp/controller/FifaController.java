package ParcourComp.controller;

import ParcourComp.service.FifaApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fifa")
@CrossOrigin(origins = "http://localhost:8080")
public class FifaController {

    private final FifaApiService fifaService;

    public FifaController(FifaApiService fifaService) {
        this.fifaService = fifaService;
    }

    @GetMapping("/matches")
    public Map<String, Object> getAllMatches() {
        return fifaService.getWorldCupMatches();
    }

    @GetMapping("/groups")
    public Map<String, List<Map<String, Object>>> getGroups() {
        return fifaService.getGroupedMatches();
    }

    @GetMapping("/matches/stage/{stage}")
    public List<Map<String, Object>> getMatchesByStage(@PathVariable String stage) {
        return fifaService.getMatchesByStage(stage);
    }

    @GetMapping("/today")
    public List<Map<String, Object>> getTodayMatches() {
        String today = java.time.LocalDate.now().toString();
        Map<String, Object> allMatches = fifaService.getWorldCupMatches();
        List<Map<String, Object>> matches = (List) allMatches.get("matches");

        return matches.stream()
                .filter(m -> ((String) m.get("utcDate")).startsWith(today))
                .toList();
    }

    @GetMapping("/matches/finished")
    public ResponseEntity<?> getFinishedMatches() {
        try {
            Map<String, Object> allMatches = fifaService.getWorldCupMatches();
            List<Map<String, Object>> matches = (List) allMatches.get("matches");

            if (matches == null) {
                return ResponseEntity.ok(Map.of("matches", List.of(), "count", 0));
            }

            List<Map<String, Object>> finished = matches.stream()
                    .filter(m -> "FINISHED".equals(m.get("status")))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(Map.of(
                    "matches", finished,
                    "count", finished.size(),
                    "timestamp", System.currentTimeMillis()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}