package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Love;
import io.eho.dishspawn.model.Visual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LoveRepository extends JpaRepository<Love, Long> {

//    Love save(Love like);
    Love findLoveByVisualAndChef(Visual visual, Chef chef);
    List<Love> findAllByVisual(Visual visual);

}
