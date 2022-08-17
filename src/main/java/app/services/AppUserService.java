package app.services;

import app.models.AppUser;
import app.models.exceptions.UsernameTakenException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class AppUserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    private final PasswordEncoder encoder;

    public AppUserService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return em.createQuery("SELECT u FROM AppUser u WHERE u.username = :name", AppUser.class)
                .setParameter("name", username)
                .getSingleResult();
    }

    public AppUser getLoggedInUser() {
        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional
    public void saveUser(AppUser user) throws UsernameTakenException {
        if (!isUsernameTaken(user.getUsername())) {
            user.setPassword(encoder.encode(user.getPassword()));
            em.persist(user);
        } else {
            throw new UsernameTakenException();
        }
    }

    private boolean isUsernameTaken(String username) {
        try {
            loadUserByUsername(username);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Transactional
    public void changePassword(String newPassword) {
        AppUser loggedInUser = getLoggedInUser();
        AppUser userFromDB = (AppUser) loadUserByUsername(loggedInUser.getUsername());

        userFromDB.setPassword(encoder.encode(newPassword));
        userFromDB.setAlreadyLoggedIn(true);

        loggedInUser.setAlreadyLoggedIn(true);
    }

}
