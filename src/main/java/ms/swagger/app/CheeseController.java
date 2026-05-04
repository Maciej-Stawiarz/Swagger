package ms.swagger.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CheeseController {
	
	private final CheeseService cheeseService;
}
