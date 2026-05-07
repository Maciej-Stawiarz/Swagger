package ms.swagger.cheese;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ms.swagger.cheese.models.CheeseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Cheese API", description = "Used for a management of cheese")
@RequestMapping("cheese")
public interface CheeseAPI {
	
	@Operation(summary = "Get a list of all cheeses")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "302", description = "Cheeses were found")
	})
	@GetMapping("all")
	ResponseEntity<List<CheeseDto>> findAll();
	
	
	@Operation(summary = "Get a single cheese by UUID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "302", description = "Cheese with given UUID was found"),
			@ApiResponse(responseCode = "404", description = "Cheese with given UUID was NOT found")
	})
	@GetMapping("{uuid}")
	ResponseEntity<CheeseDto> get(
			@PathVariable("uuid")
			@Parameter(name = "uuid", description = "UUID of the targeted Cheese") UUID uuid);
	
	
	@Operation(summary = "Save a cheese into the database. DTO must NOT contain UUID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cheese was added to the database"),
			@ApiResponse(responseCode = "400", description = "Cheese contained UUID when received")
	})
	@PostMapping
	ResponseEntity<CheeseDto> save(
			@RequestBody
			@Parameter(name = "dto", description = "Entire structure of Cheese without UUID") CheeseDto dto);
	
	
	@Operation(summary = "Updates already existing Cheese in the database. Must contain UUID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cheese was updated successfully"),
			@ApiResponse(responseCode = "400", description = "Cheese received did not posses UUID"),
			@ApiResponse(responseCode = "404", description = "Cheese with given UUID was not found in the database")
	})
	@PutMapping
	ResponseEntity<CheeseDto> update(
			@RequestBody
			@Parameter(name = "dto", description = "Entire structure of Cheese without UUID") CheeseDto dto);
	
	
	@Operation(summary = "Removes cheese from the database. UUID must be present")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cheese was deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Cheese with given UUID was not found in the database")
	})
	@DeleteMapping("{uuid}")
	ResponseEntity<Void> delete(
			@PathVariable("uuid")
			@Parameter(name = "uuid", description = "UUID of the targeted Cheese") UUID uuid);
}
