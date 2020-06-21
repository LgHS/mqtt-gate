package be.lghs.gate.server.services;

import be.lghs.gate.server.configuration.OAuth2UserImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// @Component
public class UserService {

    public static Optional<OAuth2UserImpl> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }
        return Optional.ofNullable((OAuth2UserImpl) authentication.getPrincipal());
    }
}
