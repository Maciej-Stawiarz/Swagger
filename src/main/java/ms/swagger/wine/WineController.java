package ms.swagger.wine;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WineController {
	
	private final WineService wineService;
}
