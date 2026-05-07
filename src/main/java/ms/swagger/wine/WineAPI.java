package ms.swagger.wine;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = " Wine API", description = "Used for a management of wine")
@RequestMapping("wine")
public interface WineAPI {
}
