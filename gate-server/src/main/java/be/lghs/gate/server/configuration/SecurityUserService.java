package be.lghs.gate.server.configuration;

import be.lghs.gate.server.model.tables.records.UsersRecord;
import be.lghs.gate.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        Map<String, Object> attributes = user.getAttributes();

        UsersRecord usersRecord = userRepository.ensureUserExists(
            (int) attributes.get("id"),
            UUID.fromString((String) attributes.get("uuid")),
            (String) attributes.get("name"),
            (String) attributes.get("username"),
            (String) attributes.get("email"));

        return new OAuth2UserImpl(
            usersRecord.getRole(),
            attributes
        );
    }
}
