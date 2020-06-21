package be.lghs.gate.server.configuration;

import be.lghs.gate.server.model.enums.UserRole;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class OAuth2UserImpl extends DefaultOAuth2User {

    private final UUID id;

    public OAuth2UserImpl(UserRole role, Map<String, Object> attributes) {
        super(Set.of(Roles.fromString(role.getLiteral())), attributes, "username");
        this.id = UUID.fromString((String) attributes.get("uuid"));
    }

    public UUID getId() {
        return id;
    }
}
