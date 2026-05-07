package ms.swagger.wine;

import ms.swagger.wine.models.WineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WineRepository extends JpaRepository<WineEntity, UUID> {
}