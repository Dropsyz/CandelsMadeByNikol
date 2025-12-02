package libraly.candelsmadebynikol.init;

import libraly.candelsmadebynikol.models.entity.RoleEntity;
import libraly.candelsmadebynikol.models.enums.UserRoleEnum;
import libraly.candelsmadebynikol.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.Arrays;

@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        if (roleRepository.count() == 0) {
            Arrays.stream(UserRoleEnum.values())
                    .forEach(roleEnum ->{
                        RoleEntity role =  new RoleEntity();
                        role.setRole(roleEnum);
                        roleRepository.save(role);
                    });
        }



    }
}
