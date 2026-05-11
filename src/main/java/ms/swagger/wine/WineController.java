package ms.swagger.wine;

import lombok.RequiredArgsConstructor;
import ms.swagger.wine.models.WineDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WineController implements WineAPI {
	
	private final WineService wineService;
	
	@Override
	public ResponseEntity<List<WineDto>> findAll() {
		return new ResponseEntity<>(
				wineService.findAll(),
				HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<WineDto> get(UUID uuid) {
		return new ResponseEntity<>(
				wineService.get(uuid),
				HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<WineDto> save(WineDto dto) {
		return new ResponseEntity<>(
				wineService.save(dto),
				HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<WineDto> update(WineDto dto) {
		return new ResponseEntity<>(
				wineService.update(dto),
				HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Void> delete(UUID uuid) {
		wineService.delete(uuid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
