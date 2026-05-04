package ms.swagger.app;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CheeseDto {
	
	private UUID uuid;
	private String name;
	private Long age;
	private String color;
}
