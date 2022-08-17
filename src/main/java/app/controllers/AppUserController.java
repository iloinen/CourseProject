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

    /**
     * WE HAVE NOT YET LEARNED THIS!
     *
     * Custom login page. It replaces the Spring Security's default login page.
     * We only have to set @GetMapping, but not @PostMapping.
     * So if the user wants to log in, our login.html will be returned.
     */
    @GetMapping(value = {"/login"})
    public String loginPage() {
        return "login";
    }

    /**
     * WE HAVE NOT YET LEARNED THIS!
     *
     * Custom login error page. If the user tries to log in with bad username or password,
     * this endpoint will be called.
     * Must be set in web security configuration - check configs.WebSecConfig for more details.
     */
    @GetMapping(value = {"/login-error"})
    public String loginErrorPage(Model model) {
        model.addAttribute("loginError", true);

        return "login";
    }

    /**
     * WE HAVE NOT YET LEARNED THIS!
     *
     * Successfully logged in page.
     * This endpoint method checks if the user has already logged in or not.
     * If not, the user will be redirected to the "change your password" page.
     * If the user has already changed the password, the username is added to the model and appears on the website.
     */
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

    /**
     * WE HAVE NOT YET LEARNED THIS!
     *
     * Register new user page.
     */
    @GetMapping(value = {"/register"})
    public String saveUserPage(Model model) {
        model.addAttribute("user", new AppUser());

        return "saveUser";
    }

    /**
     * WE HAVE NOT YET LEARNED THIS!
     *
     * POST endpoint for registering new user.
     *
     * IMPORTANT! There is no check if the username is taken or not! And this may cause problems...
     */
    @PostMapping(value = {"/register"})
    public String saveUser(AppUser user) {
        userService.saveUser(user);

        return "redirect:/login";
    }

    /**
     * WE HAVE NOT YET LEARNED THIS!
     *
     * Change password page.
     */
    @GetMapping(value = {"/users/profile"})
    public String newPasswordPage() {
        return "passwordChange";
    }

    /**
     * WE HAVE NOT YET LEARNED THIS!
     *
     * POST endpoint for changing the password.
     */
    @PostMapping(value = {"/users/profile"})
    public String newPassword(String newPassword) {
        userService.changePassword(newPassword);

        return "redirect:/success";
    }

}
