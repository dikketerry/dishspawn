package io.eho.dishspawn.model;

// project imports
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.eho.dishspawn.model.util.visualproperties.IngredientCategory;

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
    private Long id;

    @Column(name="ingredient_name", unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="ingredient_category")
    private IngredientCategory category;

    @JsonIgnore
    @OneToMany(mappedBy = "ingredient", fetch = FetchType.EAGER) // no cascade
    // - RecipeIngredients are created with a recipe. not with an Ingredient
    private Set<RecipeIngredient> recipeIngredients;

    // constructor with args
    public Ingredient(String name, IngredientCategory category) {
        this.name = Objects.requireNonNull(name);
        this.category = Objects.requireNonNull(category);
    }

    // convenience method recipe ingredient
    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        if (recipeIngredients == null) {
            recipeIngredients = new HashSet<>();
        }
        this.recipeIngredients.add(recipeIngredient);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Ingredient's name: " + this.name);
        sb.append("\n");
        sb.append("Ingredient's category: " + this.category);
        sb.append("\n");

        return sb.toString();
    }

    // equals / hash

}

