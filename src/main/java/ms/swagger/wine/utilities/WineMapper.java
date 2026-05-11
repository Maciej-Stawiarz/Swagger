package ms.swagger.wine.utilities;

import lombok.NoArgsConstructor;
import ms.swagger.wine.models.WineDto;
import ms.swagger.wine.models.WineEntity;

@NoArgsConstructor
public class WineMapper {
	
	public static WineDto toDto(WineEntity entity) {
		return WineDto.builder()
				.uuid(entity.getUuid())
				.age(entity.getAge())
				.name(entity.getName())
				.taste(entity.getTaste())
				.build();
	}
	
	public static WineEntity toEntity(WineDto dto) {
		return new WineEntity(
				dto.getUuid(),
				dto.getAge(),
				dto.getName(),
				dto.getTaste());
	}
}