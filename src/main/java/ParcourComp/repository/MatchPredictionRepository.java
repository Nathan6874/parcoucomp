package ParcourComp.repository;
import ParcourComp.model.MatchPrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MatchPredictionRepository extends JpaRepository<MatchPrediction, Long> {
    Optional<MatchPrediction> findByMatchId(String matchId);
}