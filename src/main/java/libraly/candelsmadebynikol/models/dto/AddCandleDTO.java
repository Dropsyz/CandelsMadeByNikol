package libraly.candelsmadebynikol.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import static libraly.candelsmadebynikol.common.messages.RequiredMessages.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AddCandleDTO {

    @NotBlank(message = CANDLE_NAME_REQUIRED)
    private String candleName;
    @NotBlank(message = DESCRIPTION_REQUIRED)
    private String description;
    @NotNull(message = PRICE_REQUIRED)
    @Positive(message = PRICE_POSITIVE_REQUIRED)
    private BigDecimal price;
    private MultipartFile image;
    private String imageUrl;
    @NotBlank(message = SKU_REQUIRED)
    private String sku;
    @NotNull(message = CATEGORY_REQUIRED)
    private UUID categoryId;
}
