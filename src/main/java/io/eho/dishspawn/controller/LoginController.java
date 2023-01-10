package io.eho.dishspawn.controller;

import io.eho.dishspawn.web.FormLoginChef;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm(Model model) {
        // check if user is already logged in, and only if not, go to login page
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            model.addAttribute("pageTitle", "Login");
            model.addAttribute("formLoginData", new FormLoginChef());
            return "login";
        } else {
            return "redirect:/";
        }
    }

    // a PostMapping is not required as Spring Security provides this per default at /login endpoint
}
