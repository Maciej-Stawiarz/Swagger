package ms.swagger.cheese;

import ms.swagger.cheese.models.CheeseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CheeseRepository extends JpaRepository<CheeseEntity, UUID> {
}
