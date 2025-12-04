package libraly.candelsmadebynikol.services;

import jakarta.transaction.Transactional;
import libraly.candelsmadebynikol.common.exceptions.ExceptionMessages;
import libraly.candelsmadebynikol.models.entity.RoleEntity;
import libraly.candelsmadebynikol.models.entity.UserEntity;
import libraly.candelsmadebynikol.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static libraly.candelsmadebynikol.common.exceptions.ExceptionMessages.USERNAME_NOTFOUND_EXCEPTION;

@Service
public class CandleShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CandleShopUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        return userRepository.findByUsername(username)
                .map(this::mapToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOTFOUND_EXCEPTION, username)));

    }

    public  UserDetails mapToUserDetails(UserEntity userEntity) {
        return User
                .withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(mapAuthorities(userEntity.getRoles()))
                .build();

    }

    private List<GrantedAuthority> mapAuthorities(Set<RoleEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
                .collect(Collectors.toList());
    }
}
