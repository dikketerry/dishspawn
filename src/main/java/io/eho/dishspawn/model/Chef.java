package io.eho.dishspawn.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // todo detail cascade types
    @OneToMany(mappedBy = "chef", fetch=FetchType.LAZY)
    private Set<Recipe> recipes = new HashSet<>();

    // this boolean needs to ensure a chef can only generate a meal once a
    // day. It needs logic in the service coming from the timestamp for
    // his/her last generated dish
    @Column(name = "daily_slot")
    private boolean dailySlot = true;                           // default value

    // toString

    // equals / hash

}