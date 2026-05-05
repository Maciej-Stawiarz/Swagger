package ms.swagger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<String> handlerForNotFoundException(NotFoundException exception) {
		return new ResponseEntity<>(
			exception.getMessage(),
			HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handlerForIllegalArgumentException(IllegalArgumentException exception) {
		return new ResponseEntity<>(
			exception.getMessage(),
			HttpStatus.BAD_REQUEST
		);
	}
}