package ParcourComp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "knockout_predictions")
@Data
@NoArgsConstructor
public class KnockoutPrediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String matchId;

    private String winner;
    private String round;
}
