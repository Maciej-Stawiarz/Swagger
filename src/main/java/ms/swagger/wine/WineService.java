package ms.swagger.wine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WineService {
	
	private final WineRepository wineRepository;
}
