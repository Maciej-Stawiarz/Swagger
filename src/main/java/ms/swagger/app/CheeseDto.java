package ms.swagger.app;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CheeseDto {
	
	private UUID uuid;
	
	@NotBlank
	private String name;
	@NotNull
	private Long age;
	@NotBlank
	private String color;
}