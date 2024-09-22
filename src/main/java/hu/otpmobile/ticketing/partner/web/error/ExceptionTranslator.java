package hu.otpmobile.ticketing.partner.web.error;

import hu.otpmobile.ticketing.partner.web.error.exception.TicketingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

  @ExceptionHandler(TicketingException.class)
  public ResponseEntity<ApiError> handleTicketingException(TicketingException ex) {
    return createResponse(ex.getErrorType());
  }

  private ResponseEntity<ApiError> createResponse(ErrorType errorSpecification) {
    return ResponseEntity.status(errorSpecification.getHttpStatus())
        .body(fromErrorType(errorSpecification));
  }

  private ApiError fromErrorType(ErrorType errorSpecification) {
    return fromErrorType(errorSpecification, null);
  }

  private ApiError fromErrorType(ErrorType errorSpecification, Map<String, String> errorItems) {
    return new ApiError(errorSpecification.getCode(), errorSpecification.getMessage(), errorItems);
  }
}
