package EntityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerDTO {
    private int userId;
    private String buyerAddress;
    private String buyerPhone;
    private UserDTO userDTO; // Опционально, можно исключить чтобы избежать циклической зависимости
}
