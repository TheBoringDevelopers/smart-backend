package ru.the_boring_developers.auth.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.the_boring_developers.auth.entity.IdentifiableUser;
import ru.the_boring_developers.common.entity.user.User;
import ru.the_boring_developers.common.repository.user.UserRepository;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(username);
        if (user != null) {
            return new IdentifiableUser(
                    user.getId(),
                    username,
                    user.getPassword(),
                    createAuthorityList("CLIENT")
            );
        } else {
            throw new UsernameNotFoundException("Не найден пользователь " + username);
        }
    }
}
