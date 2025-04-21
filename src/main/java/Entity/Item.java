package Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Setter
@Getter
@Entity
@Table(name = "Items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private double price;

    // Связь с таблицей Order_Items
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Order_Item> orderItems = new HashSet<>();

    // Getters and Setters
    @Override
    public String toString()
    {
        return name + " - " + price + "₽";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == itemId;
    }
    @Override
    public int hashCode() { return Objects.hash(itemId); }
}