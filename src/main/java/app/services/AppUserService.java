package app.services;

import app.models.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class AppUserService implements UserDetailsService {

    /**
     * I do not use any JPA Repository to execute SQL statements.
     * I like EntityManager - sorry.
     */
    @PersistenceContext
    private EntityManager em;

    private final PasswordEncoder encoder;

    public AppUserService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * WE USED JPA REPOSITORY FOR THIS.
     *
     * As I mentioned below: I like EntityManager. I find a user by username in this way.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return em.createQuery("SELECT u FROM AppUser u WHERE u.username = :name", AppUser.class)
                .setParameter("name", username)
                .getSingleResult();
    }

    /**
     * WE HAVE NOT YET LEARNED THIS!
     *
     * Spring Security has its own context. If a user logs in, Security puts the user to its context.
     * The logged in user can be obtained:
     *      SecurityContextHolder.getContext().getAuthentication().getPrincipal()
     */
    public AppUser getLoggedInUser() {
        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * WE USED JPA REPOSITORY FOR THIS.
     *
     * Saves the new user.
     * This method should check if the username is taken or not.
     */
    @Transactional
    public void saveUser(AppUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        em.persist(user);
    }

    /**
     * WE HAVE NOT YET LEARNED THIS!
     *
     * Changes the password of the user.
     * This method changes the password in the database.
     * Also sets the user's alreadyLoggedIn field to true. (Currently logged in user's field also has to be changed.)
     */
    @Transactional
    public void changePassword(String newPassword) {
        AppUser loggedInUser = getLoggedInUser();
        AppUser userFromDB = (AppUser) loadUserByUsername(loggedInUser.getUsername());

        userFromDB.setPassword(encoder.encode(newPassword));
        userFromDB.setAlreadyLoggedIn(true);

        loggedInUser.setAlreadyLoggedIn(true);
    }

}
