package ms.swagger.wine;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ms.swagger.wine.models.WineDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = " Wine API", description = "Used for a management of wine")
@RequestMapping("wine")
public interface WineAPI {
	
	@Operation(summary = "Get a list of all wines")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "302", description = "Wines were found")
	})
	@GetMapping("all")
	ResponseEntity<List<WineDto>> findAll();
	
	
	@Operation(summary = "Get a single wine by UUID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "302", description = "Wine with given UUID was found"),
			@ApiResponse(responseCode = "404", description = "Wine with given UUID was NOT found")
	})
	@GetMapping("{uuid}")
	ResponseEntity<WineDto> get(
			@PathVariable("uuid")
			@Parameter(name = "uuid", description = "UUID of the targeted Wine") UUID uuid);
	
	
	@Operation(summary = "Save a wine into the database. DTO must NOT contain UUID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Wine was added to the database"),
			@ApiResponse(responseCode = "400", description = "Wine contained UUID when received")
	})
	@PostMapping
	ResponseEntity<WineDto> save(
			@RequestBody
			@Parameter(name = "dto", description = "Entire structure of Wine without UUID") WineDto dto);
	
	
	@Operation(summary = "Updates already existing Wine in the database. Must contain UUID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Wine was updated successfully"),
			@ApiResponse(responseCode = "400", description = "Wine received did not posses UUID"),
			@ApiResponse(responseCode = "404", description = "Wine with given UUID was not found in the database")
	})
	@PutMapping
	ResponseEntity<WineDto> update(
			@RequestBody
			@Parameter(name = "dto", description = "Entire structure of Wine without UUID") WineDto dto);
	
	
	@Operation(summary = "Removes wine from the database. UUID must be present")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Wine was deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Wine with given UUID was not found in the database")
	})
	@DeleteMapping("{uuid}")
	ResponseEntity<Void> delete(
			@PathVariable("uuid")
			@Parameter(name = "uuid", description = "UUID of the targeted Wine") UUID uuid);
}
