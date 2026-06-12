package ParcourComp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "match_predictions")
@Data
@NoArgsConstructor
public class MatchPrediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String matchId;

    private String homeTeam;
    private String awayTeam;
    private Integer homeScore = 0;
    private Integer awayScore = 0;
    private Boolean isPlayed = false;
    private String groupName;
    private String stage;
}
