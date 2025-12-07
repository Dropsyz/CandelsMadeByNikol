package libraly.candelsmadebynikol.services.interfaces;

import libraly.candelsmadebynikol.models.dto.UserRegistrationDTO;

public interface UserService {
    void registerUser(UserRegistrationDTO registrationDTO);
}
