package vn.com.hust.identityservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.com.hust.identityservice.data.AdminUserPrinciple;

@Service
public class AdminUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Qualifier("corpAuthenticationService")
    AuthenticationService authenticationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return AdminUserPrinciple.from(authenticationService.findUser(username));
    }
}
