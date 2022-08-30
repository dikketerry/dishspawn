package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.repository.IngredientRepository;
import io.eho.dishspawn.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService {
    private IngredientRepository ingredientRepository;

    public IngredientServiceImpl() {

    }

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void saveIngredient(Ingredient ingredient) {
        this.ingredientRepository.save(ingredient);
    }

    @Override
    public List<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public Set<Ingredient> findAllIngredientsByNameContaining(String searchKey) {
        return ingredientRepository.findAllByNameContaining(searchKey);
    }

    @Override
    public Page<Ingredient> findPageIngredientsByNameContaining(String searchKey, int pageNr) {
        Pageable pageable = PageRequest.of(pageNr, 3, Sort.Direction.ASC, "name");
        return ingredientRepository.findAllByNameContaining(searchKey, pageable);
    }

    @Override
    public Page<Ingredient> findPageIngredients(int pageNr) {

        int pageSize = 5;

//        if (pageNumber == 1) {
//            pageSize = 4;
//        } else pageSize = 3;

        Pageable pageable = PageRequest.of(pageNr, pageSize,
                                           Sort.by(Sort.Direction.ASC, "name"));
        return ingredientRepository.findAll(pageable);

    }

    @Override
    public Ingredient findIngredientById(Long id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);

        Ingredient ingredient = null;
        if (optionalIngredient.isPresent()) {
            ingredient = optionalIngredient.get();
        } else {
            // ingredient not found
            throw new RuntimeException("ingredient with id " + id + " not " +
                                               "found");
        }

        return ingredient;
    }

}
