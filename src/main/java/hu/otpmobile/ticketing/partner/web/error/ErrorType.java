package hu.otpmobile.ticketing.partner.web.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

  READ_FILE_ERROR("00001", "Hiba a fájl beolvasása közben!", HttpStatus.INTERNAL_SERVER_ERROR),
  EVENT_DOES_NOT_EXISTS("90001", "Nem létezik ilyen esemény!", HttpStatus.BAD_REQUEST),
  EVENT_EXPIRED("90011", " Olyan eseményre ami már elkezdődött nem lehet jegyet eladni!",
      HttpStatus.BAD_REQUEST),
  SEAT_DOES_NOT_EXISTS("20002", " Nem létezik ilyen szék!", HttpStatus.BAD_REQUEST),
  RESERVED_SEAT("20002", "Már lefoglalt székre nem lehet jegyet eladni!", HttpStatus.BAD_REQUEST);


  private final String code;
  private final String message;
  private final HttpStatus httpStatus;

  ErrorType(String code, String message, HttpStatus httpStatus) {
    this.code = code;
    this.message = message;
    this.httpStatus = httpStatus;
  }
}
