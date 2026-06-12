package ParcourComp.controller;
import ParcourComp.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/predictions")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping("/match")
    public ResponseEntity<?> saveMatchPrediction(@RequestBody Map<String, Object> data) {
        predictionService.saveMatchPrediction(
                (String) data.get("matchId"),
                (String) data.get("homeTeam"),
                (String) data.get("awayTeam"),
                (int) data.get("homeScore"),
                (int) data.get("awayScore"),
                (String) data.get("groupName"),
                (String) data.get("stage")
        );
        return ResponseEntity.ok(Map.of("status", "success"));
    }

    @PostMapping("/knockout")
    public ResponseEntity<?> saveKnockoutPrediction(@RequestBody Map<String, String> data) {
        predictionService.saveKnockoutPrediction(
                data.get("matchId"),
                data.get("winner"),
                data.get("round")
        );
        return ResponseEntity.ok(Map.of("status", "success"));
    }

    @GetMapping("/load")
    public ResponseEntity<?> loadPredictions() {
        return ResponseEntity.ok(predictionService.getAllPredictions());
    }

    @DeleteMapping("/reset")
    public ResponseEntity<?> resetPredictions() {
        predictionService.resetAllPredictions();
        return ResponseEntity.ok(Map.of("status", "reset"));
    }
}