package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Like;
import io.eho.dishspawn.model.Visual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

//    Like save(Like like);
    Like findLikeByVisualAndChef(Visual visual, Chef chef);
    List<Like> findAllByVisual(Visual visual);

}
