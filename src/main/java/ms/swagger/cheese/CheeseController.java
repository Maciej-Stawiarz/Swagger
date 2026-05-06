package ms.swagger.cheese;

import lombok.RequiredArgsConstructor;
import ms.swagger.cheese.models.CheeseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CheeseController implements CheeseAPI {
	
	private final CheeseService cheeseService;
	
	public ResponseEntity<List<CheeseDto>> findAll() {
		return new ResponseEntity<>(
				cheeseService.findAll(),
				HttpStatus.FOUND);
	}
	
	public ResponseEntity<CheeseDto> get(@PathVariable("uuid") UUID uuid) {
		return new ResponseEntity<>(
				cheeseService.get(uuid),
				HttpStatus.FOUND);
	}
	
	public ResponseEntity<CheeseDto> save(@RequestBody CheeseDto dto) {
		return new ResponseEntity<>(
				cheeseService.save(dto),
				HttpStatus.CREATED);
	}
	
	public ResponseEntity<CheeseDto> update(@RequestBody CheeseDto dto) {
		return new ResponseEntity<>(
				cheeseService.update(dto),
				HttpStatus.OK);
	}
	
	public ResponseEntity<Void> delete(@PathVariable("uuid") UUID uuid) {
		cheeseService.delete(uuid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}