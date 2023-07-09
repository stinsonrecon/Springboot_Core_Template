package vn.com.hust.identityservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.com.hust.identityservice.data.AdminUserPrinciple;
import vn.com.hust.identityservice.data.UserData;
import vn.com.hust.identityservice.services.AuthenticationService;

import java.util.Objects;

/**
 * @author TienLM20
 * @since 08/07/2023
 */

@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    @Qualifier("corpAuthenticationService")
    AuthenticationService authenticationService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserData userData = authenticationService.findUser(username);
        if (!passwordEncoder.encode(password).equals(userData.getPassword()))
            throw new BadCredentialsException("Bad credentials");
        if (Objects.nonNull(userData.getStatus()) && userData.getStatus() == 0)
            throw new LockedException("Locked account");
        AdminUserPrinciple principal = AdminUserPrinciple.from(userData);
        return new UsernamePasswordAuthenticationToken(principal, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
