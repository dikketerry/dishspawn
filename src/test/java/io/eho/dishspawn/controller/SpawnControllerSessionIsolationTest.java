package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.service.IngredientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc   // security filter chain ON: the header fragment uses #authorization / sec:authorize.
                       // /spawn/** is permitAll and GETs need no CSRF token, so anonymous requests pass.
class SpawnControllerSessionIsolationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IngredientService ingredientService;

    @Test
    void twoSessionsDoNotShareABasket() throws Exception {
        Ingredient a = new Ingredient();   // no equals() on entities (finding A5) -> identity compare, fine
        Ingredient b = new Ingredient();
        when(ingredientService.findIngredientById(1L)).thenReturn(a);
        when(ingredientService.findIngredientById(2L)).thenReturn(b);

        MockHttpSession chefA = new MockHttpSession();
        MockHttpSession chefB = new MockHttpSession();

        // chef A drops ingredient 1 in the basket; chef B drops ingredient 2
        mvc.perform(get("/spawn/add/1").session(chefA));
        mvc.perform(get("/spawn/add/2").session(chefB));

        // each chef's /spawn shows only their own pick
        mvc.perform(get("/spawn").session(chefA))
                .andExpect(model().attribute("ingredientSpawnSet", contains(a)));
        mvc.perform(get("/spawn").session(chefB))
                .andExpect(model().attribute("ingredientSpawnSet", contains(b)));
    }
}