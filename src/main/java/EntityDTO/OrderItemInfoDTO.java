package EntityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemInfoDTO {
    private LocalDateTime orderDate;
    private String itemName;
    private double itemPrice;
    private int itemCount;

    @Override
    public String toString()
    {
        return orderDate + " " + itemName + " " + itemPrice + " " + itemCount;
    }
}
