package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Visual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisualRepository extends JpaRepository<Visual, Long> {

    // all crud methods

}
