package libraly.orderservice.model.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID candleId;

    @Column(nullable = false)
    private String shippingAddress;


    @Column(nullable = false)
    private BigDecimal totalPrice;

    private String status;
}