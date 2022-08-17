package app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * WE HAVE NOT YET LEARNED THIS! Or not all of this...
     *
     * .formLogin().permitAll().loginPage("/login")
     *      this calls our login endpoint
     *      we can customize our login page
     * .defaultSuccessUrl("/success")
     *      this calls the success endpoint if the user successfully logged in
     * .failureUrl("/login-error")
     *      this calls the login-error endpoint if the user logged in was not successfully
     * .logout().logoutUrl("/logout")
     *      not really necessary, and Spring Security uses this endpoint for logout by default
     * .logoutSuccessUrl("/home)
     *      if the user successfully logged out, this endpoint will be called
     * .invalidateHttpSession(true)
     *      absolutely logs out the user, so Security does not keep the user in its context anymore
     * .authorizeRequests().antMatchers("/*.css", "/", "/home", "/register").permitAll()
     *      allows anybody to visit this endpoints - no need to login or else
     *      "/*.css"
     *          VERY IMPORTANT TO ADD THIS!!!
     *          if we do NOT write the CSS files here, our custom style will NOT be loaded!
     *          so without this, only logged in users can see our very nice style... :(
     * .anyRequest().authenticated()
     *      any other endpoints are available only for logged in users
     *      no matter what role the users have, they can visit for example "/success", "/logout"
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .permitAll()
                .loginPage("/login")
                .defaultSuccessUrl("/success", true)
                .failureUrl("/login-error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .and()
                .authorizeRequests()
                .antMatchers("/*.css", "/", "/home", "/register").permitAll()
                .anyRequest().authenticated();
    }

}
