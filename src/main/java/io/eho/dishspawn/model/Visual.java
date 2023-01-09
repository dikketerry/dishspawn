package io.eho.dishspawn.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "visual")

// the class Visual represents the path to an image and is stored in the DB. The image itself will
// be stored outside the project src path
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

    @Column(name = "visual_file_name")
    private String fileName;

    @Column(name = "visual_location")
    private String fileLocation;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chef_id")
    private Chef chef;

    @Column(name = "love_count")
    private int loveCount;

    // transient property to allow checking if a chef loved a certain visual
    @Transient
    private boolean userLove;

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("File name of visual: " + this.fileName + " ");
        sb.append("located at: " + this.fileLocation + " ");
        sb.append("belongs to recipe: " + this.recipe.getName());
        sb.append("\n");
        sb.append("created at: " + timestampCreated);

        return sb.toString();
    }
}
