package ms.swagger.cheese;

import ms.swagger.cheese.models.CheeseDto;
import ms.swagger.cheese.models.CheeseEntity;
import ms.swagger.cheese.utilities.CheeseMapper;
import ms.swagger.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheeseServiceTest {
	
	@Mock
	private CheeseRepository cheeseRepository;
	@InjectMocks
	private CheeseService cheeseService;
	
	@Test
	void findAllWhenThereAreObjectsToReturn() {
		List<CheeseDto> cheeseDtoList = generateDto(3);
		
		when(cheeseRepository.findAll())
				.thenReturn(cheeseDtoList.stream().map(CheeseMapper::toEntity).toList());
		
		List<CheeseDto> response = cheeseService.findAll();
		
		verify(cheeseRepository, times(1))
				.findAll();
		
		assertThat(response)
				.isNotNull()
				.isNotEmpty()
				.hasSize(3)
				.usingRecursiveComparison()
				.isEqualTo(cheeseDtoList);
	}
	
	@Test
	void findAllWhenThereIsNothingToReturn() {
		when(cheeseRepository.findAll())
				.thenReturn(new ArrayList<>());
		
		List<CheeseDto> response = cheeseService.findAll();
		
		verify(cheeseRepository, times(1))
				.findAll();
		
		assertThat(response)
				.isNotNull()
				.isEmpty();
	}
	
	@Test
	void getWhenUUIDWasGivenAndThereIsData() {
		UUID uuid = UUID.randomUUID();
		
		CheeseDto dto = new CheeseDto();
		dto.setUuid(uuid);
		
		when(cheeseRepository.findById(uuid))
			.thenReturn(Optional.of(CheeseMapper.toEntity(dto)));
		
		CheeseDto response = cheeseService.get(uuid);
		
		verify(cheeseRepository, times(1))
				.findById(uuid);
		
		assertThat(response)
				.isNotNull()
				.extracting(CheeseDto::getUuid)
				.isEqualTo(uuid);
	}
	
	@Test
	void getWhenUUIDWasNotGiven() {
		assertThatThrownBy(() -> cheeseService.get(null))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Given UUID is null");
	}
	
	@Test
	void getWhenUUIDWasGivenButThereIsNoData() {
		UUID uuid = UUID.randomUUID();
		
		assertThatThrownBy(() -> cheeseService.get(uuid))
				.isInstanceOf(NotFoundException.class)
				.hasMessage("Cheese not found: " + uuid);
	}
	
	@Test
	void saveWhenDataWasGivenAndThereIsNoUUID() {
		CheeseDto dto = new CheeseDto();
		
		when(cheeseRepository.save(any(CheeseEntity.class)))
				.thenReturn(CheeseMapper.toEntity(dto));
		
		CheeseDto response = cheeseService.save(dto);
		
		verify(cheeseRepository, times(1))
				.save(any(CheeseEntity.class));
		
		assertThat(response)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(dto);
	}
	
	@Test
	void saveWhenDataWasGivenAndThereIsUUID() {
		UUID uuid = UUID.randomUUID();
		CheeseDto dto = new CheeseDto().toBuilder().uuid(uuid).build();
		
		assertThatThrownBy(() -> cheeseService.save(dto))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("New entities should not posses ids!");
	}
	
	@Test
	void saveWhenDataWasNotGiven() {
		assertThatThrownBy(() -> cheeseService.save(null))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("New entities cannot be null!");
	}
	
	@Test
	void updateWhenDataWasGivenAndThereIsUUIDAndEntityExists() {
		UUID uuid = UUID.randomUUID();
		
		CheeseDto dto = new CheeseDto().toBuilder().uuid(uuid).build();
		CheeseEntity entity = CheeseMapper.toEntity(dto);
		
		when(cheeseRepository.existsById(uuid))
				.thenReturn(true);
		when(cheeseRepository.save(any(CheeseEntity.class)))
				.thenReturn(entity);
		
		CheeseDto response = cheeseService.update(dto);
		
		verify(cheeseRepository, times(1))
				.existsById(uuid);
		verify(cheeseRepository, times(1))
				.save(any(CheeseEntity.class));
		
		assertThat(response)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(dto);
	}
	
	@Test
	void updateWhenDataWasGivenAndThereIsUUIDButEntityDoesntExist() {
		UUID uuid = UUID.randomUUID();
		
		CheeseDto dto = new CheeseDto().toBuilder().uuid(uuid).build();
		
		when(cheeseRepository.existsById(uuid))
				.thenReturn(false);
		
		assertThatThrownBy(() -> cheeseService.update(dto))
				.isInstanceOf(NotFoundException.class)
				.hasMessage("Cheese: " + dto.getUuid() + " targeted by the update was not found.");
	}
	
	@Test
	void updateWhenDataWasGivenButThereIsNoUUID() {
		CheeseDto dto = new CheeseDto();
		
		assertThatThrownBy(() -> cheeseService.update(dto))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("UUID needs to be given to update existing entity.");
	}
	
	@Test
	void updateWhenDataWasNotGiven() {
		assertThatThrownBy(() -> cheeseService.update(null))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("New entities cannot be null!");
	}
	
	@Test
	void deleteWhenUUIDWasGivenAndEntityExists() {
		UUID uuid = UUID.randomUUID();
		
		when(cheeseRepository.existsById(uuid))
				.thenReturn(true);
		
		cheeseService.delete(uuid);
		
		verify(cheeseRepository, times(1))
				.existsById(uuid);
		verify(cheeseRepository, times(1))
				.deleteById(uuid);
	}
	
	@Test
	void deleteWhenUUIDWasGivenButEntityDoesntExist() {
		UUID uuid = UUID.randomUUID();
		
		when(cheeseRepository.existsById(uuid))
				.thenReturn(false);
		
		assertThatThrownBy(() -> cheeseService.delete(uuid))
				.isInstanceOf(NotFoundException.class)
				.hasMessage("Cheese: " + uuid + " targeted by the delete was not found.");
	}
	
	@Test
	void deleteWhenUUIDWasNotGiven() {
		assertThatThrownBy(() -> cheeseService.delete(null))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Given UUID is null");
	}
	
	private List<CheeseDto> generateDto(int amountToBeGenerated) {
		List<CheeseDto> list = new ArrayList<>(amountToBeGenerated);
		
		for (int i = 0; i < amountToBeGenerated; i++) {
			CheeseDto dto = new CheeseDto();
			list.add(dto);
		}
		
		return list;
	}
}