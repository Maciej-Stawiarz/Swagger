package ms.swagger.app;

import lombok.RequiredArgsConstructor;
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
		return CheeseMapper.toDto(
				cheeseRepository.save(
						CheeseMapper.toEntity(dto)));
	}
	
	public CheeseDto update(CheeseDto dto) {
		if (!cheeseRepository.existsById(dto.getUuid())) {
			throw new NotFoundException("Cheese not found: " + dto.getUuid());
		}
		
		return CheeseMapper.toDto(
				cheeseRepository.save(
						CheeseMapper.toEntity(dto)));
	}
	
	public void delete(UUID uuid) {
		if (!cheeseRepository.existsById(uuid)) {
			throw new NotFoundException("Cheese not found: " + uuid);
		}
		
		cheeseRepository.deleteById(uuid);
	}
}