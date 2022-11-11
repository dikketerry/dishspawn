package io.eho.dishspawn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter @Setter
@Entity
@Table(name = "chef")
public class Chef {

    @CreationTimestamp
    @Column(name="timestamp_created")
    @Setter(AccessLevel.NONE)
    private Timestamp timestampCreated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name = "chef_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username", unique = true)
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar_path")
    private String avatarPath;

    @Column(name = "daily_slot")
    private boolean dailySlot;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "chef_role",
            joinColumns = @JoinColumn(name = "chef_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "role_id")
//    private Role role;

    protected Chef() {
    }

    public Chef(String firstName,
                String lastName,
                String userName,
                String email,
                String password,
                String avatarPath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.avatarPath = (avatarPath == null) ? "/img/default-avatar.png" : avatarPath;
        this.dailySlot = true;
    }

    // toString
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Chef's username: " + this.userName);
        sb.append("\n");
        sb.append("Avatar path: " + this.avatarPath);
        sb.append("\n");
        sb.append("Has daily slot: " + this.dailySlot);

        return sb.toString();
    }
    // equals / hash
}