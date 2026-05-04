package ms.swagger.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheeseService {
	
	private final CheeseRepository cheeseRepository;
}
