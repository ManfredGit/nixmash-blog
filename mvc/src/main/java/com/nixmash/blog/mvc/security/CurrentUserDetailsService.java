
package com.nixmash.blog.mvc.security;

import com.nixmash.blog.jpa.model.CurrentUser;
import com.nixmash.blog.jpa.model.User;
import com.nixmash.blog.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    static final String USER_IS_DISABLED = "User is disabled";


    @Autowired
    public CurrentUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CurrentUser loadUserByUsername(String username)
            throws UsernameNotFoundException  {
        User user = userRepository.findByUsername(username);
        if (!user.isEnabled())
            throw new  DisabledException(USER_IS_DISABLED);
        return new CurrentUser(user);
    }

}
