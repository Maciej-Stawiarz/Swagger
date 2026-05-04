package ms.swagger.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CheeseRepository extends JpaRepository<CheeseEntity, UUID> {
}
