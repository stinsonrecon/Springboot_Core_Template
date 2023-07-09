package vn.com.hust.identityservice.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomIpAuthenticationProvider implements AuthenticationProvider {

    Set<String> whitelist = new HashSet<String>();

    public CustomIpAuthenticationProvider() {
        super();
        whitelist.add("11.11.11.11");
        whitelist.add("127.0.0.1");
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
        String userIp = details.getRemoteAddress();
        if (!whitelist.contains(userIp)) {
            throw new BadCredentialsException("Invalid IP Address");
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
