package hu.otpmobile.ticketing.partner.web.dto;

import hu.otpmobile.ticketing.partner.enumeration.Currency;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SeatResponse {

  private String id;
  private BigDecimal price;
  private Currency currency;
  private boolean reserved;
}
