package hu.otpmobile.ticketing.partner.web.rest;

import hu.otpmobile.ticketing.partner.web.dto.EventDetailsResponse;
import hu.otpmobile.ticketing.partner.web.dto.EventsResponse;
import hu.otpmobile.ticketing.partner.web.dto.ReservationResponse;
import hu.otpmobile.ticketing.partner.service.EventService;
import hu.otpmobile.ticketing.partner.web.dto.ReservationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/events")
@RequiredArgsConstructor
public class EventResource {

  private final EventService eventService;

  @GetMapping
  private EventsResponse getEvents() {
    return eventService.getEvents();
  }

  @GetMapping("/{id}")
  private EventDetailsResponse getEvent(@PathVariable Long id) {
    return eventService.getEvent(id);
  }

  @PostMapping("/reserve")
  private ReservationResponse reserveSeat(@RequestBody @Valid ReservationRequest reservationRequest) {
    return eventService.reserveSeat(reservationRequest);
  }

}
