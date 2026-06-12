package ParcourComp.service;
import ParcourComp.model.KnockoutPrediction;
import ParcourComp.model.MatchPrediction;
import ParcourComp.repository.KnockoutPredictionRepository;
import ParcourComp.repository.MatchPredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final MatchPredictionRepository matchRepository;
    private final KnockoutPredictionRepository knockoutRepository;

    @Transactional
    public void saveMatchPrediction(String matchId, String homeTeam, String awayTeam,
                                    int homeScore, int awayScore, String groupName, String stage) {
        MatchPrediction prediction = matchRepository.findByMatchId(matchId)
                .orElse(new MatchPrediction());

        prediction.setMatchId(matchId);
        prediction.setHomeTeam(homeTeam);
        prediction.setAwayTeam(awayTeam);
        prediction.setHomeScore(homeScore);
        prediction.setAwayScore(awayScore);
        prediction.setIsPlayed(true);
        prediction.setGroupName(groupName);
        prediction.setStage(stage);

        matchRepository.save(prediction);
    }

    @Transactional
    public void saveKnockoutPrediction(String matchId, String winner, String round) {
        KnockoutPrediction prediction = knockoutRepository.findByMatchId(matchId)
                .orElse(new KnockoutPrediction());

        prediction.setMatchId(matchId);
        prediction.setWinner(winner);
        prediction.setRound(round);

        knockoutRepository.save(prediction);
    }

    public Map<String, Object> getAllPredictions() {
        Map<String, Object> predictions = new HashMap<>();

        List<MatchPrediction> matches = matchRepository.findAll();
        Map<String, Object> matchScores = new HashMap<>();
        for (MatchPrediction m : matches) {
            matchScores.put(m.getMatchId(), Map.of(
                    "home", m.getHomeScore(),
                    "away", m.getAwayScore(),
                    "played", m.getIsPlayed()
            ));
        }
        predictions.put("scores", matchScores);

        List<KnockoutPrediction> knockouts = knockoutRepository.findAll();
        Map<String, String> knockoutWinners = new HashMap<>();
        for (KnockoutPrediction k : knockouts) {
            knockoutWinners.put(k.getMatchId(), k.getWinner());
        }
        predictions.put("knockoutWinners", knockoutWinners);

        return predictions;
    }

    @Transactional
    public void resetAllPredictions() {
        matchRepository.deleteAll();
        knockoutRepository.deleteAll();
    }
}