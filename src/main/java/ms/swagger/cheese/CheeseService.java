package ms.swagger.cheese;

import lombok.RequiredArgsConstructor;
import ms.swagger.cheese.models.CheeseDto;
import ms.swagger.cheese.utilities.CheeseMapper;
import ms.swagger.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheeseService {
	
	private final CheeseRepository cheeseRepository;
	
	public List<CheeseDto> findAll() {
		return cheeseRepository.findAll().stream()
				.map(CheeseMapper::toDto)
				.toList();
	}
	
	public CheeseDto get(UUID uuid) {
		return CheeseMapper.toDto(
				cheeseRepository.findById(uuid)
						.orElseThrow(() -> new NotFoundException("Cheese not found: " + uuid)));
				
	}
	
	public CheeseDto save(CheeseDto dto) {
		if (dto.getUuid() != null) {
			throw new IllegalArgumentException("New entities should not posses ids!");
		}
		
		return CheeseMapper.toDto(
				cheeseRepository.save(
						CheeseMapper.toEntity(dto)));
	}
	
	public CheeseDto update(CheeseDto dto) {
		if (dto.getUuid() == null) {
			throw new IllegalArgumentException("UUID needs to be given to update existing entity.");
		}
		
		if (!cheeseRepository.existsById(dto.getUuid())) {
			throw new NotFoundException("Cheese: " + dto.getUuid() + " targeted by the update was not found.");
		}
		
		return CheeseMapper.toDto(
				cheeseRepository.save(
						CheeseMapper.toEntity(dto)));
	}
	
	public void delete(UUID uuid) {
		if (!cheeseRepository.existsById(uuid)) {
			throw new NotFoundException("Cheese: " + uuid + " targeted by the delete was not found.");
		}
		
		cheeseRepository.deleteById(uuid);
	}
}