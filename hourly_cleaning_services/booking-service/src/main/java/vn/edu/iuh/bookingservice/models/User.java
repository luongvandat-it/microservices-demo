package vn.edu.iuh.bookingservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @JsonIgnore
    private String firstName;
    @Column(nullable = false)
    @JsonIgnore
    private String lastName;
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private String phone;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @JsonIgnore
    private boolean gender;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<Role> roles;
}