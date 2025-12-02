package libraly.candelsmadebynikol.services;

import libraly.candelsmadebynikol.models.dto.UserRegistrationDTO;

public interface UserService {
    void registerUser(UserRegistrationDTO registrationDTO);
}
