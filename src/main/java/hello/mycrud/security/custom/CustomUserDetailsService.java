package hello.mycrud.security.custom;

import hello.mycrud.crud.domain.entity.User;
import hello.mycrud.crud.repository.jparepository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User findByUsername = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "유저를 찾지 못했습니다."));

        return new CustomUserDetails(findByUsername);
    }
}
