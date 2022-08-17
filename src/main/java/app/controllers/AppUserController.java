package app.controllers;

import app.models.AppUser;
import app.services.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppUserController {

    private final AppUserService userService;

    public AppUserController(AppUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/login"})
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = {"/login-error"})
    public String loginErrorPage(Model model) {
        model.addAttribute("loginError", true);

        return "login";
    }

    @GetMapping(value = {"/success"})
    public String successLogin(Model model) {
        String username;
        try {
            AppUser principal = userService.getLoggedInUser();
            username = principal.getUsername();

            if (!principal.isAlreadyLoggedIn()) {
                return "redirect:/users/profile";
            }
        } catch (Exception e) {
            username = "anonymous";
        }

        model.addAttribute("username", username);

        return "success";
    }

    // -----------------------------------------------------------------

    @GetMapping(value = {"/register"})
    public String saveUserPage(Model model) {
        model.addAttribute("user", new AppUser());

        return "saveUser";
    }

    @PostMapping(value = {"/register"})
    public String saveUser(AppUser user) {
        userService.saveUser(user);

        return "redirect:/login";
    }

    @GetMapping(value = {"/users/profile"})
    public String newPasswordPage() {
        return "passwordChange";
    }

    @PostMapping(value = {"/users/profile"})
    public String newPassword(String newPassword) {
        userService.changePassword(newPassword);

        return "redirect:/success";
    }

}
