package libraly.candelsmadebynikol.models.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderDTO {
    private UUID id;
    private UUID userId;
    private UUID candleId;
    private BigDecimal price;
    private String status;
    private String shippingAddress;
}