package ms.swagger.wine.utilities;

import ms.swagger.wine.models.WineDto;
import ms.swagger.wine.models.WineEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WineMapperTest {
	
	@Test
	void toDtoWhenEverythingIsCorrect() {
		WineEntity entity = new WineEntity(
				UUID.randomUUID(),
				10L,
				"Name",
				"Sweet");
		
		WineDto dto = WineMapper.toDto(entity);
		
		assertThat(dto)
				.isNotNull()
				.extracting(
						WineDto::getUuid,
						WineDto::getAge,
						WineDto::getName,
						WineDto::getTaste)
				.containsExactly(
						entity.getUuid(),
						entity.getAge(),
						entity.getName(),
						entity.getTaste());
	}
	
	@Test
	void toDtoWhenEntityIsNull() {
		assertThatThrownBy(() -> WineMapper.toDto(null))
				.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	void toDtoWhenSomeFieldsAreNull() {
		WineEntity entity = new WineEntity(
				UUID.randomUUID(),
				10L,
				null,
				null);
		
		WineDto dto = WineMapper.toDto(entity);
		
		assertThat(dto)
				.isInstanceOf(WineDto.class)
				.isNotNull()
				.extracting(
						WineDto::getUuid,
						WineDto::getAge,
						WineDto::getName,
						WineDto::getTaste)
				.containsExactly(
						entity.getUuid(),
						10L,
						null,
						null);
	}
	
	@Test
	void toEntityWhenEverythingIsCorrect() {
		WineDto dto = new WineDto(
				UUID.randomUUID(),
				10L,
				"name",
				"Sour");
		
		WineEntity entity = WineMapper.toEntity(dto);
		
		assertThat(entity)
				.isNotNull()
				.extracting(
						WineEntity::getUuid,
						WineEntity::getAge,
						WineEntity::getName,
						WineEntity::getTaste)
				.containsExactly(
						dto.getUuid(),
						dto.getAge(),
						dto.getName(),
						dto.getTaste());
	}
	
	@Test
	void toEntityWhenDtoIsNull() {
		assertThatThrownBy(() -> WineMapper.toEntity(null))
				.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	void toEntityWhenSomeFieldsAreNull() {
		WineDto dto = new WineDto(
				UUID.randomUUID(),
				10L,
				null,
				null);
		
		WineEntity entity = WineMapper.toEntity(dto);
		
		assertThat(entity)
				.isNotNull()
				.extracting(
						WineEntity::getUuid,
						WineEntity::getAge,
						WineEntity::getName,
						WineEntity::getTaste)
				.containsExactly(
						dto.getUuid(),
						dto.getAge(),
						null,
						null);
	}
}
