package ms.swagger.wine;

import lombok.RequiredArgsConstructor;
import ms.swagger.exceptions.NotFoundException;
import ms.swagger.wine.models.WineDto;
import ms.swagger.wine.utilities.WineMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WineService {
	
	private final WineRepository wineRepository;
	
	public List<WineDto> findAll() {
		return wineRepository.findAll().stream()
				.map(WineMapper::toDto)
				.toList();
	}
	
	public WineDto get(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("Given UUID is null");
		}
		
		return WineMapper.toDto(
				wineRepository.findById(uuid)
						.orElseThrow(() -> new NotFoundException("Wine not found: " + uuid)));
	}
	
	public WineDto save(WineDto dto) {
		if (dto == null) {
			throw new IllegalArgumentException("New entities cannot be null!");
		}
		
		if (dto.getUuid() != null) {
			throw new IllegalArgumentException("New entities should not posses ids!");
		}
		
		return WineMapper.toDto(
				wineRepository.save(
						WineMapper.toEntity(dto)));
	}
	
	public WineDto update(WineDto dto) {
		if (dto == null) {
			throw new IllegalArgumentException("New entities cannot be null!");
		}
		
		if (dto.getUuid() == null) {
			throw new IllegalArgumentException("UUID needs to be given to update existing entity.");
		}
		
		if (!wineRepository.existsById(dto.getUuid())) {
			throw new NotFoundException("Wine: " + dto.getUuid() + " targeted by the update was not found.");
		}
		
		return WineMapper.toDto(
				wineRepository.save(
						WineMapper.toEntity(dto)));
	}
	
	public void delete(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("Given UUID is null");
		}
		
		if (!wineRepository.existsById(uuid)) {
			throw new NotFoundException("Wine: " + uuid + " targeted by the delete was not found.");
		}
		
		wineRepository.deleteById(uuid);
	}
}
