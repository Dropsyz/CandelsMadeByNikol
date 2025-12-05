package libraly.candelsmadebynikol.models.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CandleViewDTO {

    private UUID id;
    private String name;
    private BigDecimal price;
    private String imageUrl;


}
