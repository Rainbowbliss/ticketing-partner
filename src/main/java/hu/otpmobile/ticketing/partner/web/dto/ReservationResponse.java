package hu.otpmobile.ticketing.partner.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {

  private Long reservationId;
  private boolean success;

}
