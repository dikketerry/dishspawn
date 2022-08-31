package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {
    // all crud methods
    List<Chef> findAllByUserNameContainingOrderByUserNameAsc(String searchKey);
}
