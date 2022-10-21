package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.Visual;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VisualRepository extends JpaRepository<Visual, Long> {

    @Query(value = "SELECT next_val FROM dishspawn_db.hibernate_sequence", nativeQuery = true)
    Long getNextValSequence();

    List<Visual> findAllByOrderByTimestampCreatedDesc();

    List<Visual> findTop200ByOrderByTimestampCreatedDesc();

    Visual findFirstByOrderByTimestampCreatedDesc();

    List<Visual> findByChefOrderByTimestampCreatedDesc(Chef chef);

    // TODO - custom query for pagination
//    List<Visual> findByChefOrderByTimestampCreatedDesc(Chef chef, Pageable pageable);

    Visual findTop1ByChefOrderByTimestampCreatedDesc(Chef chef);

    Visual findTop1ByRecipeOrderByTimestampCreatedDesc(Recipe recipe);

    List<Visual> findTop200ByChefOrderByTimestampCreatedDesc(Chef chef);
}
