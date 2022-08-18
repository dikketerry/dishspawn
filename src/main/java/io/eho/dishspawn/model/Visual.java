package io.eho.dishspawn.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter @Setter
@Entity
@Table(name = "visual")

public class Visual {

    @CreationTimestamp
    @Column(name="timestamp_created")
    @Setter(AccessLevel.NONE)
    private Timestamp timestampCreated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name = "visual_id")
    private Long id;

    @Column(name = "visual_name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER is default, but hey..
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Override
    public String toString() {
        // name
        // belongs to
        StringBuilder sb = new StringBuilder();

        sb.append("Name of visual file: " + this.name + " ");
        sb.append("belongs to recipe: " + this.recipe.getName());

        return sb.toString();

    }
}
