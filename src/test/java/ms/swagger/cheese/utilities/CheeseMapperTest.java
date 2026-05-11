package ms.swagger.cheese.utilities;

import ms.swagger.cheese.models.CheeseDto;
import ms.swagger.cheese.models.CheeseEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CheeseMapperTest {
	
	@Test
	void toDtoWhenEverythingIsCorrect() {
		CheeseEntity entity = new CheeseEntity(
				UUID.randomUUID(),
				"Name",
				10L,
				"Pink");
		
		CheeseDto dto = CheeseMapper.toDto(entity);
		
		assertThat(dto)
				.isNotNull()
				.extracting(
					CheeseDto::getUuid,
					CheeseDto::getName,
					CheeseDto::getAge,
					CheeseDto::getColor)
				.containsExactly(
					entity.getUuid(),
					entity.getName(),
					entity.getAge(),
					entity.getColor());
	}
	
	@Test
	void toDtoWhenEntityIsNull() {
		assertThatThrownBy(() -> CheeseMapper.toDto(null))
				.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	void toDtoWhenSomeFieldsAreNull() {
	CheeseEntity entity = new CheeseEntity(
			UUID.randomUUID(),
			null,
			10L,
			null);
	
	CheeseDto dto = CheeseMapper.toDto(entity);
	
	assertThat(dto)
			.isInstanceOf(CheeseDto.class)
			.isNotNull()
			.extracting(
				CheeseDto::getUuid,
				CheeseDto::getName,
				CheeseDto::getAge,
				CheeseDto::getColor)
			.containsExactly(
				entity.getUuid(),
				null,
				10L,
				null);
	}
	
	@Test
	void toEntityWhenEverythingIsCorrect() {
		CheeseDto dto = new CheeseDto(
				UUID.randomUUID(),
				"Name",
				10L,
				"Pink");
		
		CheeseEntity entity = CheeseMapper.toEntity(dto);
		
		assertThat(entity)
				.isNotNull()
				.extracting(
					CheeseEntity::getUuid,
					CheeseEntity::getName,
					CheeseEntity::getAge,
					CheeseEntity::getColor)
				.containsExactly(
					dto.getUuid(),
					dto.getName(),
					dto.getAge(),
					dto.getColor());
	}
	
	@Test
	void toEntityWhenDtoIsNull() {
		assertThatThrownBy(() -> CheeseMapper.toEntity(null))
				.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	void toEntityWhenSomeFieldsAreNull() {
		CheeseDto dto = new CheeseDto(
				UUID.randomUUID(),
				null,
				10L,
				null);
		
		CheeseEntity entity = CheeseMapper.toEntity(dto);
		
		assertThat(entity)
				.isNotNull()
				.extracting(
					CheeseEntity::getUuid,
					CheeseEntity::getName,
					CheeseEntity::getAge,
					CheeseEntity::getColor)
				.containsExactly(
					dto.getUuid(),
					null,
					dto.getAge(),
					null);
	}
}