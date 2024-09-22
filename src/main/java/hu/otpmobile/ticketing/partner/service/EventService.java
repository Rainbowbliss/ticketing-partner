package hu.otpmobile.ticketing.partner.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.otpmobile.ticketing.partner.web.dto.EventDetailsResponse;
import hu.otpmobile.ticketing.partner.web.dto.EventsResponse;
import hu.otpmobile.ticketing.partner.web.dto.ReservationRequest;
import hu.otpmobile.ticketing.partner.web.dto.ReservationResponse;
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
      throw new RuntimeException();
    }
  }

  public EventDetailsResponse getEvent(Long id) {
    try {
      var resource = this.getClass().getClassLoader().getResource("getEvent" + id + ".json");
      if (resource == null) {
        log.error("Ilyen esemény nem létezik");
        throw new RuntimeException(); // TODO saját exception hibakóddal
      }
      File file = new File(resource.getFile());
      return mapper.readValue(file, EventDetailsResponse.class);
    } catch (IOException e) {
      log.error("Hiba a getEvents.json fájl beolvasása közben!", e);
      throw new RuntimeException();
    }
  }

  public ReservationResponse reserveSeat(ReservationRequest reservationRequest) {
    var events = getEvents();
    var eventOptional = events.getData().stream()
        .filter(event -> event.getEventId().equals(reservationRequest.getEventId())).findFirst();
    if (eventOptional.isEmpty()) {
      log.error("Nem létezik ilyen esemény!");
      throw new RuntimeException(); // TODO saját exception hibakóddal
    }
    var event = eventOptional.get();

    if (event.getStartTimeStamp().isBefore(LocalDateTime.now())) {
      log.error("Olyan eseményre ami már elkezdődött nem lehet jegyet eladni!");
      throw new RuntimeException(); // TODO saját exception hibakóddal
    }

    var eventDetails = getEvent(reservationRequest.getEventId());

    var seatOptional = eventDetails.getData().getSeats().stream()
        .filter(seat -> seat.getId().equals(reservationRequest.getSeatId())).findFirst();

    if (seatOptional.isEmpty()) {
      log.error("Nem létező ülés!");
      throw new RuntimeException(); // TODO saját exception hibakóddal
    }

    var seat = seatOptional.get();

    if (seat.isReserved()) {
      log.error("Ez az ülés már foglalt!");
      throw new RuntimeException(); // TODO saját exception hibakóddal
    }

    var reservationId = Math.abs(random.nextLong());

    return new ReservationResponse(reservationId, true);
  }
}
