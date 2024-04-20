package ro.fisa.ssm.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.fisa.ssm.enums.RoleEnum;
import ro.fisa.ssm.persistence.user.projection.UserSecurityDetailProjection;

import java.io.Serial;
import java.util.Collections;
import java.util.List;

/**
 * Created at 3/9/2024 by Darius
 **/
public class AppUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 2981621745051469607L;
    private final transient UserSecurityDetailProjection user;
    private final boolean hasAnyActiveContract;

    public AppUserDetails(final UserSecurityDetailProjection user) {
        this.user = user;
        this.hasAnyActiveContract = user.getRole().equals(RoleEnum.ADMIN);
    }

    public AppUserDetails(final UserSecurityDetailProjection user, final boolean hasAnyActiveContract) {
        this.user = user;
        this.hasAnyActiveContract = !user.getRole().equals(RoleEnum.ADMIN) && hasAnyActiveContract;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        final String role = this.user.getRole().name();
        final String authorityName = String.format("ROLE_%s", role);
        final SimpleGrantedAuthority sga = new SimpleGrantedAuthority(authorityName);
        return Collections.singletonList(sga);
    }

    public Long getId() {
        return this.user.getId();
    }

    public RoleEnum getRole() {
        return this.user.getRole();
    }

    public String getFirstName() {
        return this.user.getFirstName();
    }

    public String getLastname() {
        return this.user.getLastName();
    }

    public String getFullName() {
        return String.format("%s %s", this.getLastname(), this.getFirstName());
    }

    public String getNationality() {
        return this.user.getNationality();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasAnyActiveContract() {
        return this.hasAnyActiveContract;
    }
}
