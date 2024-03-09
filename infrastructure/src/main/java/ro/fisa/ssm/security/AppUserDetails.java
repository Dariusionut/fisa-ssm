package ro.fisa.ssm.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.fisa.ssm.persistence.role.entity.RoleEntity;
import ro.fisa.ssm.persistence.user.entity.UserEntity;

import java.util.Collection;
import java.util.Collections;

/**
 * Created at 3/9/2024 by Darius
 **/
@RequiredArgsConstructor
public class AppUserDetails implements UserDetails {

    private final UserEntity user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final RoleEntity role = this.user.getRole();
        final String authorityName = String.format("Role_%s", role.getName());
        final SimpleGrantedAuthority sga = new SimpleGrantedAuthority(authorityName);
        return Collections.singletonList(sga);
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getCnp();
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
}
