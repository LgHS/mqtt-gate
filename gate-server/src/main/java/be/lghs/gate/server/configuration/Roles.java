package be.lghs.gate.server.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class Roles {

    private static final String PREFIX = "ROLE_";

    public static final String ROLE_MEMBER = PREFIX + "MEMBER";
    public static final GrantedAuthority MEMBER_AUTHORITY = new SimpleGrantedAuthority(ROLE_MEMBER);

    public static final String ROLE_ADMIN = PREFIX + "ADMIN";
    public static final GrantedAuthority ADMIN_AUTHORITY = new SimpleGrantedAuthority(ROLE_ADMIN);

    public static GrantedAuthority fromString(String role) {
        switch (role) {
            case ROLE_MEMBER: return MEMBER_AUTHORITY;
            case ROLE_ADMIN: return ADMIN_AUTHORITY;
            default: throw new IllegalArgumentException("Invalid role: " + role);
        }
    }


    private Roles() {}
}
