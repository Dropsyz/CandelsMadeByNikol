package libraly.candelsmadebynikol.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static libraly.candelsmadebynikol.common.messages.RequiredMessages.CATEGORY_NAME_REQUIRED;
import static libraly.candelsmadebynikol.common.messages.RequiredMessages.CATEGORY_SIZE_NAME_REQUIRED;

@Data
public class AddCategoryDTO {

    @NotBlank(message = CATEGORY_NAME_REQUIRED)
    @Size(min = 3, max = 20, message = CATEGORY_SIZE_NAME_REQUIRED)
    private String name;

    private String description;
}
