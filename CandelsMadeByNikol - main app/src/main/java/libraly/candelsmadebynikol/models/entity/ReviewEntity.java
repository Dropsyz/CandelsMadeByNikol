package libraly.candelsmadebynikol.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@Getter @Setter
@NoArgsConstructor
public class ReviewEntity extends BaseEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "candle_id", nullable = false)
    private CandleEntity candle;
}