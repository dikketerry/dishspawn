package io.eho.dishspawn.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "chef")
public class Chef {

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
    @NotBlank(message = "Username is a required field")
    private String userName;

    @Column(name = "email")
    @NotBlank(message = "Email is a required field")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Password is a required field")
    private String password;

    @Column(name = "avatar_path")
    private String avatarPath;

    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="recipe_chef",
            joinColumns=@JoinColumn(name="chef_id"),
            inverseJoinColumns = @JoinColumn(name="recipe_id"))
    private List<Recipe> recipes;

    // this boolean needs to ensure a chef can only generate a meal once a
    // day. It needs logic in the service coming from the timestamp for
    // his/her last generated dish
    @Column(name = "daily_slot")
    private boolean dailySlot = true;                           // default value

    // toString

    // equals / hash

}