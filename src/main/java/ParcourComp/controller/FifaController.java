package ParcourComp.controller;

import ParcourComp.service.FifaApiService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

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
}