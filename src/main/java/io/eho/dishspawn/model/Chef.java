package io.eho.dishspawn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.Set;

@NoArgsConstructor @Getter @Setter
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
    @Size(max = 27, message = "first name cannot contain more than 27 characters")
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 27, message = "first name cannot contain more than 27 characters")
    private String lastName;

    @Column(name = "username", unique = true) // TODO: exception handling
    @NotBlank(message = "Username is a required field")
    @Size(min = 2, max = 27, message = "first name cannot contain more than 27 characters")
    private String userName;

    @Column(name = "email")
    @NotBlank(message = "Email is a required field")
    @Pattern(regexp = "^(.+)@(\\S+)$", message="Invalid email format")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Password is a required field")
    private String password;

    @Column(name = "avatar_path")
    private String avatarPath;

    // this boolean needs to ensure a chef can only generate a meal once a
    // day. It needs logic in the service coming from the timestamp for
    // his/her last generated dish
    @Column(name = "daily_slot")
    private boolean dailySlot = true;                           // default value

    // toString
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Chef's username: " + this.userName);
        sb.append("\n");
        sb.append("Avatar path: " + this.avatarPath);
        sb.append("\n");
        sb.append("Has used daily slot: " + this.dailySlot);

        return sb.toString();

    }
    // equals / hash
}