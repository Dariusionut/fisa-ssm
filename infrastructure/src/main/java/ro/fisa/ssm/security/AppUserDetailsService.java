package ro.fisa.ssm.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.fisa.ssm.enums.ContractStatusEnum;
import ro.fisa.ssm.persistence.user.JpaUserRepository;

import java.util.List;

/**
 * Created at 3/9/2024 by Darius
 **/

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.jpaUserRepository.findDetailsByCnp(username)
                .map(details -> {
                    final List<ContractStatusEnum> statuses = this.jpaUserRepository.fetchContractStatuses(username);
                    final boolean hasActiveContract = statuses.parallelStream().anyMatch(s -> s.equals(ContractStatusEnum.ACTIVE));
                    return details;
                })
                .map(AppUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
    }
}
