package libraly.candelsmadebynikol.repository;

import libraly.candelsmadebynikol.models.entity.RoleEntity;
import libraly.candelsmadebynikol.models.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findByRole(UserRoleEnum role);
}
