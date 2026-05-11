package ms.swagger.wine.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class WineDto {
	
	private UUID uuid;
	@NotNull
	private Long age;
	@NotBlank
	private String name;
	@NotBlank
	private String taste;
}