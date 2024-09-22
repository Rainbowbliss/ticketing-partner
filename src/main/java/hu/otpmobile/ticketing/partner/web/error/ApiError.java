package hu.otpmobile.ticketing.partner.web.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

  private String code;
  private String message;
  private Map<String, String> errorItems;

  public void addErrorItem(String key, String message) {
    if (errorItems == null) {
      errorItems = new HashMap<>();
    }

    errorItems.put(key, message);
  }

}
