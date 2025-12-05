package libraly.candelsmadebynikol.services.implementation;

import libraly.candelsmadebynikol.models.dto.UserRegistrationDTO;
import libraly.candelsmadebynikol.models.entity.UserEntity;
import libraly.candelsmadebynikol.models.enums.UserRoleEnum;
import libraly.candelsmadebynikol.repository.RoleRepository;
import libraly.candelsmadebynikol.repository.UserRepository;
import libraly.candelsmadebynikol.services.interfaces.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static libraly.candelsmadebynikol.common.exceptions.ExceptionMessages.USER_ROLE_NOTFOUND_EXCEPTION;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserRegistrationDTO registrationDTO) {

        String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());

        var useRole = roleRepository.findByRole(UserRoleEnum.USER)
                .orElseThrow(() -> new IllegalArgumentException(USER_ROLE_NOTFOUND_EXCEPTION));

        UserEntity newUser = UserEntity.builder()
                .username(registrationDTO.getUsername())
                .email(registrationDTO.getEmail())
                .password(encodedPassword)
                .roles(Set.of(useRole))
                .build();

        userRepository.save(newUser);

    }
}
