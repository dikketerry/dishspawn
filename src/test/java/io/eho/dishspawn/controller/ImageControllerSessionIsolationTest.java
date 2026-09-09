package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.ImageService;
import io.eho.dishspawn.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
@AutoConfigureMockMvc(addFilters = false)   // this test is about controller+session logic, not security
class ImageControllerSessionIsolationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean private RecipeService recipeService;
    @MockBean private ImageService imageService;

    @Test
    void save_persistsTheImageFromItsOwnSession_notAnotherSessions() throws Exception {
        Recipe recipeA = new Recipe();
        byte[] bytesA = {1, 2, 3};
        byte[] bytesB = {9, 8, 7};

        when(recipeService.findRecipeById(1L)).thenReturn(recipeA);
        Visual saved = mock(Visual.class);
        when(saved.getId()).thenReturn(7L);
        when(imageService.saveVisual(any(), any())).thenReturn(saved);

        // Two users mid-flow: each generated a spawn, so each has its own bytes stashed in its own session.
        MockHttpSession sessionA = new MockHttpSession();
        sessionA.setAttribute(ImageController.PENDING_IMAGE, bytesA);
        MockHttpSession sessionB = new MockHttpSession();
        sessionB.setAttribute(ImageController.PENDING_IMAGE, bytesB);

        // User A clicks Save.
        mvc.perform(post("/spawn/spawn/1/save").session(sessionA))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/visual?visualId=7"));

        // A's image was saved — never B's; the redirect id is the persisted entity's id, not a pre-read value.
        verify(imageService).saveVisual(same(recipeA), same(bytesA));
    }
}