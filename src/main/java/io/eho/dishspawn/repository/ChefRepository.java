package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {
    // all crud methods
}
