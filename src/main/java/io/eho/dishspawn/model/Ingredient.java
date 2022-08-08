package io.eho.dishspawn.model;

// project imports
import io.eho.dishspawn.util.IngredientCategory;

// lombok imports
import lombok.*;

// hibernate imports
import org.hibernate.annotations.CreationTimestamp;

// jpa imports
import javax.persistence.*;

// java imports
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor @Getter @Setter
@Entity
@Table(name = "ingredient")
public class Ingredient {

    @CreationTimestamp
    @Column(name="timestamp_created")
    private Timestamp timestampCreated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name="ingredient_id")
    private long id;

    @Column(name="ingredient_name", unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="ingredient_category")
    private IngredientCategory category;

    @OneToMany(mappedBy = "ingredient")
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    // constructor with args
    public Ingredient(String name, IngredientCategory category) {
        this.name = Objects.requireNonNull(name);
        this.category = Objects.requireNonNull(category);
    }

    // toString

    // equals / hash

}

