package ms.swagger.wine;

import ms.swagger.exceptions.NotFoundException;
import ms.swagger.wine.models.WineDto;
import ms.swagger.wine.models.WineEntity;
import ms.swagger.wine.utilities.WineMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WineServiceTest {
	
	@Mock
	private WineRepository wineRepository;
	@InjectMocks
	private WineService wineService;
	
	@Test
	void findAllWhenThereAreObjectsToReturn() {
		List<WineDto> wineDtoList = generateDto(3);
		
		when(wineRepository.findAll())
				.thenReturn(wineDtoList.stream().map(WineMapper::toEntity).toList());
		
		List<WineDto> response = wineService.findAll();
		
		verify(wineRepository, times(1))
				.findAll();
		
		assertThat(response)
				.isNotNull()
				.isNotEmpty()
				.hasSize(3)
				.usingRecursiveComparison()
				.isEqualTo(wineDtoList);
	}
	
	@Test
	void findAllWhenThereIsNothingToReturn() {
		when(wineRepository.findAll())
				.thenReturn(new ArrayList<>());
		
		List<WineDto> response = wineService.findAll();
		
		verify(wineRepository, times(1))
				.findAll();
		
		assertThat(response)
				.isNotNull()
				.isEmpty();
	}
	
	@Test
	void getWhenUUIDWasGivenAndThereIsData() {
		UUID uuid = UUID.randomUUID();
		
		WineDto dto = new WineDto();
		dto.setUuid(uuid);
		
		when(wineRepository.findById(uuid))
				.thenReturn(Optional.of(WineMapper.toEntity(dto)));
		
		WineDto response = wineService.get(uuid);
		
		verify(wineRepository, times(1))
				.findById(uuid);
		
		assertThat(response)
				.isNotNull()
				.extracting(WineDto::getUuid)
				.isEqualTo(uuid);
	}
	
	@Test
	void getWhenUUIDWasNotGiven() {
		assertThatThrownBy(() -> wineService.get(null))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Given UUID is null");
	}
	
	@Test
	void getWhenUUIDWasGivenButThereIsNoData() {
		UUID uuid = UUID.randomUUID();
		
		assertThatThrownBy(() -> wineService.get(uuid))
				.isInstanceOf(NotFoundException.class)
				.hasMessage("Wine not found: " + uuid);
	}
	
	@Test
	void saveWhenDataWasGivenAndThereIsNoUUID() {
		WineDto dto = new WineDto();
		
		when(wineRepository.save(any(WineEntity.class)))
				.thenReturn(WineMapper.toEntity(dto));
		
		WineDto response = wineService.save(dto);
		
		verify(wineRepository, times(1))
				.save(any(WineEntity.class));
		
		assertThat(response)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(dto);
	}
	
	@Test
	void saveWhenDataWasGivenAndThereIsUUID() {
		UUID uuid = UUID.randomUUID();
		WineDto dto = new WineDto().toBuilder().uuid(uuid).build();
		
		assertThatThrownBy(() -> wineService.save(dto))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("New entities should not posses ids!");
	}
	
	@Test
	void saveWhenDataWasNotGiven() {
		assertThatThrownBy(() -> wineService.save(null))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("New entities cannot be null!");
	}
	
	@Test
	void updateWhenDataWasGivenAndThereIsUUIDAndEntityExists() {
		UUID uuid = UUID.randomUUID();
		
		WineDto dto = new WineDto().toBuilder().uuid(uuid).build();
		WineEntity entity = WineMapper.toEntity(dto);
		
		when(wineRepository.existsById(uuid))
				.thenReturn(true);
		when(wineRepository.save(any(WineEntity.class)))
				.thenReturn(entity);
		
		WineDto response = wineService.update(dto);
		
		verify(wineRepository, times(1))
				.existsById(uuid);
		verify(wineRepository, times(1))
				.save(any(WineEntity.class));
		
		assertThat(response)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(dto);
	}
	
	@Test
	void updateWhenDataWasGivenAndThereIsUUIDButEntityDoesntExist() {
		UUID uuid = UUID.randomUUID();
		
		WineDto dto = new WineDto().toBuilder().uuid(uuid).build();
		
		when(wineRepository.existsById(uuid))
				.thenReturn(false);
		
		assertThatThrownBy(() -> wineService.update(dto))
				.isInstanceOf(NotFoundException.class)
				.hasMessage("Wine: " + dto.getUuid() + " targeted by the update was not found.");
	}
	
	@Test
	void updateWhenDataWasGivenButThereIsNoUUID() {
		WineDto dto = new WineDto();
		
		assertThatThrownBy(() -> wineService.update(dto))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("UUID needs to be given to update existing entity.");
	}
	
	@Test
	void updateWhenDataWasNotGiven() {
		assertThatThrownBy(() -> wineService.update(null))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("New entities cannot be null!");
	}
	
	@Test
	void deleteWhenUUIDWasGivenAndEntityExists() {
		UUID uuid = UUID.randomUUID();
		
		when(wineRepository.existsById(uuid))
				.thenReturn(true);
		
		wineService.delete(uuid);
		
		verify(wineRepository, times(1))
				.existsById(uuid);
		verify(wineRepository, times(1))
				.deleteById(uuid);
	}
	
	@Test
	void deleteWhenUUIDWasGivenButEntityDoesntExist() {
		UUID uuid = UUID.randomUUID();
		
		when(wineRepository.existsById(uuid))
				.thenReturn(false);
		
		assertThatThrownBy(() -> wineService.delete(uuid))
				.isInstanceOf(NotFoundException.class)
				.hasMessage("Wine: " + uuid + " targeted by the delete was not found.");
	}
	
	@Test
	void deleteWhenUUIDWasNotGiven() {
		assertThatThrownBy(() -> wineService.delete(null))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Given UUID is null");
	}
	
	private List<WineDto> generateDto(int amountToBeGenerated) {
		List<WineDto> list = new ArrayList<>(amountToBeGenerated);
		
		for (int i = 0; i < amountToBeGenerated; i++) {
			WineDto dto = new WineDto();
			list.add(dto);
		}
		
		return list;
	}
}
