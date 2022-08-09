package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    // all crud methods

}
