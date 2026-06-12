package ParcourComp.repository;
import ParcourComp.model.KnockoutPrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KnockoutPredictionRepository extends JpaRepository<KnockoutPrediction, Long> {
    Optional<KnockoutPrediction> findByMatchId(String matchId);
}