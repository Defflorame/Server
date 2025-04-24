package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private Enums.Roles roleName;


    // Связь с таблицей Users

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private Set<User> users = new HashSet<>();

    // Getters and Setters
    @Override
    public String toString()
    {
        return roleId + " " + roleName.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleId == role.roleId;
    }
    @Override
    public int hashCode() { return Objects.hash(roleId); }
}
