package Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "Buyers")
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "buyer_address")
    private String buyerAddress;

    @Column(name = "buyer_phone")
    private String buyerPhone;

    // Связь с таблицей Users
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
    @Override
    public String toString()
    {
        return user.getUserName() + " " + buyerAddress + " " + buyerPhone;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buyer buyer = (Buyer) o;
        return userId== buyer.userId;
    }
    @Override
    public int hashCode() { return Objects.hash(userId); }
}
