package ms.swagger.app;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class CheeseMapper {
	
	public static CheeseDto toDto(CheeseEntity entity) {
		return CheeseDto.builder()
				.uuid(entity.getUuid())
				.name(entity.getName())
				.age(entity.getAge())
				.color(entity.getColor())
				.build();
	}
	
	public static CheeseEntity toEntity(CheeseDto dto) {
		return new CheeseEntity(
				dto.getUuid(),
				dto.getName(),
				dto.getAge(),
				dto.getColor());
	}
}