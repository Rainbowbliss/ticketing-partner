package hu.otpmobile.ticketing.partner.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.otpmobile.ticketing.partner.web.dto.EventDetailsResponse;
import hu.otpmobile.ticketing.partner.web.dto.EventsResponse;
import hu.otpmobile.ticketing.partner.web.dto.ReservationRequest;
import hu.otpmobile.ticketing.partner.web.dto.ReservationResponse;
import hu.otpmobile.ticketing.partner.web.error.ErrorType;
import hu.otpmobile.ticketing.partner.web.error.exception.TicketingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
public class EventService {

  private final ObjectMapper mapper = new ObjectMapper();
  private final Random random = new Random();

  public EventsResponse getEvents() {
    try {
      File file = new File(
          this.getClass().getClassLoader().getResource("getEvents.json").getFile()
      );
      return mapper.readValue(file, EventsResponse.class);
    } catch (IOException e) {
      log.error("Hiba a getEvents.json fájl beolvasása közben!", e);
      throw new TicketingException(ErrorType.READ_FILE_ERROR);
    }
  }

  public EventDetailsResponse getEvent(Long id) {
    try {
      var resource = this.getClass().getClassLoader().getResource("getEvent" + id + ".json");
      if (resource == null) {
        log.error("A következő id-val nem létezik esemény: {}", id);
        throw new TicketingException(ErrorType.EVENT_DOES_NOT_EXISTS);
      }
      File file = new File(resource.getFile());
      return mapper.readValue(file, EventDetailsResponse.class);
    } catch (IOException e) {
      log.error("Hiba a getEvents.json fájl beolvasása közben!", e);
      throw new TicketingException(ErrorType.READ_FILE_ERROR);
    }
  }

  public ReservationResponse reserveSeat(ReservationRequest reservationRequest) {
    var events = getEvents();
    var eventOptional = events.getData().stream()
        .filter(event -> event.getEventId().equals(reservationRequest.getEventId())).findFirst();
    if (eventOptional.isEmpty()) {
      log.error("A következő id-val nem létezik esemény: {}", reservationRequest.getEventId());
      throw new TicketingException(ErrorType.EVENT_DOES_NOT_EXISTS);
    }
    var event = eventOptional.get();

    if (event.getStartTimeStamp().isBefore(LocalDateTime.now())) {
      log.error("Olyan eseményre ami már elkezdődött nem lehet jegyet eladni!");
      throw new TicketingException(ErrorType.EVENT_EXPIRED);
    }

    var eventDetails = getEvent(reservationRequest.getEventId());

    var seatOptional = eventDetails.getData().getSeats().stream()
        .filter(seat -> seat.getId().equals(reservationRequest.getSeatId())).findFirst();

    if (seatOptional.isEmpty()) {
      log.error(" Nem létezik ilyen szék!");
      throw new TicketingException(ErrorType.SEAT_DOES_NOT_EXISTS);
    }

    var seat = seatOptional.get();

    if (seat.isReserved()) {
      log.error("Már lefoglalt székre nem lehet jegyet eladni!");
      throw new TicketingException(ErrorType.RESERVED_SEAT);
    }

    var reservationId = Math.abs(random.nextLong());

    return new ReservationResponse(reservationId, true);
  }
}
