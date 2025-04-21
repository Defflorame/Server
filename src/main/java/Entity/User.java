package Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Setter
@Getter
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;


    // Связь с таблицей Roles
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    // Связь с таблицей Buyers
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Buyer buyer;

    // Связь с таблицей Orders
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Order> orders = new HashSet<>();

    // Getters and Setters
    @Override
    public String toString()
    {
        return userId + " " + userName + " " + role.getRoleName().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }
    @Override
    public int hashCode() { return Objects.hash(userId); }
}
