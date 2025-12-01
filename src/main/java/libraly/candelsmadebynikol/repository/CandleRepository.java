package libraly.candelsmadebynikol.repository;

import libraly.candelsmadebynikol.models.entity.CandleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CandleRepository extends JpaRepository<CandleEntity, UUID> {

    Optional<CandleEntity> findBySku(String sku);
}
