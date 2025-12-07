package libraly.candelsmadebynikol.models.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddReviewDTO {

    @NotBlank(message = "Review text cannot be empty!")
    @Size(min = 5, max = 500, message = "Review must be between 5 and 500 characters.")
    private String text;

    @Min(value = 1, message = "Rating must be at least 1.")
    @Max(value = 5, message = "Rating must be at most 5.")
    private int rating;
}