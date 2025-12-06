package libraly.orderservice.model.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderDTO {
    private UUID id;
    private UUID userId;
    private UUID candleId;
    private BigDecimal price;
    private String shippingAddress;
    private String status;
    private LocalDateTime orderDate;
}