package io.eho.dishspawn.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter @Setter
@Entity
@Table(name = "likes")
public class Like {

    @CreationTimestamp
    @Column(name="timestamp_created")
    @Setter(AccessLevel.NONE)
    private Timestamp timestampCreated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id") // results in recipe id in like table
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "chef_id") // results in chef id (who gave the like) in
    // like table
    private Chef chef;

    // toString
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Like id: " + this.id);
        sb.append(": ");
        sb.append("by " + this.chef.getUserName() + " ");
        sb.append("for recipe " + this.recipe.getName());

        return sb.toString();

    }


    // equals & hash

}
