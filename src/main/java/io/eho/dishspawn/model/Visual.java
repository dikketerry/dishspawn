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
    // the class Visual is there to represent the path to an image that will
    // be stored outside the src path (in the end..)

    @CreationTimestamp
    @Column(name="timestamp_created")
    @Setter(AccessLevel.NONE)
    private Timestamp timestampCreated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name = "visual_id")
    private Long id;

    @Column(name = "visual_file_name")
    private String fileName;

    @Column(name = "visual_location")
    private String location;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER is default, but hey..
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chef_id")
    private Chef chef;

    @Override
    public String toString() {
        // name
        // belongs to
        StringBuilder sb = new StringBuilder();

        sb.append("File name of visual: " + this.fileName + " ");
        sb.append("located at: " + this.location + " ");
        sb.append("belongs to recipe: " + this.recipe.getName());

        return sb.toString();
    }
}
