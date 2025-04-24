package EntityDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private int orderId;
    private LocalDateTime orderDate;
    private UserDTO userDTO;
    private Set<Order_ItemDTO> orderItems;
}
