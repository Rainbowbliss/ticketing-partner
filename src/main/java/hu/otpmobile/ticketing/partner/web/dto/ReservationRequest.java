package hu.otpmobile.ticketing.partner.web.dto;

import lombok.Data;

@Data
public class ReservationRequest {

  private Long eventId;
  private String seatId;
}
