package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Visual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisualRepository extends JpaRepository<Visual, Long> {

    List<Visual> findAllByOrderByTimestampCreatedDesc();

    List<Visual> findTop200ByOrderByTimestampCreatedDesc();

    Visual findFirstByOrderByTimestampCreatedDesc();

//    List<Visual> findAllVisualsByChefSortByTimestampCreated(Long chefId);

    List<Visual> findByChefOrderByTimestampCreatedDesc(Chef chef);

    Visual findTop1ByChefOrderByTimestampCreated(Chef chef);
}
