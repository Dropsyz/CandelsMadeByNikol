package libraly.candelsmadebynikol.services;

import libraly.candelsmadebynikol.models.dto.UserRegistrationDTO;
import libraly.candelsmadebynikol.models.entity.RoleEntity;
import libraly.candelsmadebynikol.models.entity.UserEntity;
import libraly.candelsmadebynikol.models.enums.UserRoleEnum;
import libraly.candelsmadebynikol.repository.RoleRepository;
import libraly.candelsmadebynikol.repository.UserRepository;
import libraly.candelsmadebynikol.services.implementation.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void testRegisterUser_Success() {

        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setUsername("testuser");
        dto.setEmail("test@test.com");
        dto.setPassword("password");
        dto.setConfirmPassword("password");

        RoleEntity userRole = new RoleEntity();
        userRole.setRole(UserRoleEnum.USER);

        when(roleRepository.findByRole(UserRoleEnum.USER)).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("password")).thenReturn("encoded_password");


        userService.registerUser(dto);

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userCaptor.capture());

        UserEntity savedUser = userCaptor.getValue();
        Assertions.assertEquals("testuser", savedUser.getUsername());
        Assertions.assertEquals("encoded_password", savedUser.getPassword());
        Assertions.assertTrue(savedUser.getRoles().contains(userRole));
    }
}