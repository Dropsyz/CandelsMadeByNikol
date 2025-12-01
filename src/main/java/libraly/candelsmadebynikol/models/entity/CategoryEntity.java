package libraly.candelsmadebynikol.models.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter @Setter
@NoArgsConstructor
public class CategoryEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Връзка към свещите, но без да я зареждаме веднага (Lazy)
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<CandleEntity> candles;
}