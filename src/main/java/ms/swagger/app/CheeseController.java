package ms.swagger.app;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("cheese")
@RequiredArgsConstructor
public class CheeseController {
	
	private final CheeseService cheeseService;
	
	@GetMapping("all")
	public ResponseEntity<List<CheeseDto>> findAll() {
		return new ResponseEntity<>(
				cheeseService.findAll(),
				HttpStatus.FOUND);
	}
	
	@GetMapping("{uuid}")
	public ResponseEntity<CheeseDto> get(@PathVariable("uuid") UUID uuid) {
		return new ResponseEntity<>(
				cheeseService.get(uuid),
				HttpStatus.FOUND);
	}
	
	@PostMapping
	public ResponseEntity<CheeseDto> save(@RequestBody CheeseDto dto) {
		return new ResponseEntity<>(
				cheeseService.save(dto),
				HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<CheeseDto> update(@RequestBody CheeseDto dto) {
		return new ResponseEntity<>(
				cheeseService.update(dto),
				HttpStatus.OK);
	}
	
	@DeleteMapping("{uuid}")
	public ResponseEntity<Void> delete(@PathVariable("uuid") UUID uuid) {
		cheeseService.delete(uuid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}