package libraly.candelsmadebynikol.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static libraly.candelsmadebynikol.common.messages.RequiredMessages.*;

@Data
public class UserRegistrationDTO {

    @NotBlank(message = USERNAME_REQUIRED)
    @Size(min = 3, max = 20, message = REQUIRED_USERNAME_LENGTH)
    private String username;
    @NotBlank(message = PASSWORD_REQUIRED)
    @Size(min = 5, message = PASSWORD_LENGTH)
    private String password;
    @NotBlank(message = CONFIRM_PASSWORD_REQUIRED)
    private String confirmPassword;
    @NotBlank(message = EMAIL_REQUIRED)
    @Email(message = VALID_EMAIL)
    private String email;
}
