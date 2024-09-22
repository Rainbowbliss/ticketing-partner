package hu.otpmobile.ticketing.partner.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hu.otpmobile.ticketing.partner.config.CustomLocalDateTimeDeserializer;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventResponse {

  private Long eventId;
  private String title;
  private String location;
  @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
  private LocalDateTime startTimeStamp;
  @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
  private LocalDateTime endTimeStamp;
}
