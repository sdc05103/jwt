package hello.hellospring.service;

import hello.hellospring.response.BaseException;
import hello.hellospring.response.ResponseStatus;
import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{

    //private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Qualifier("jdbcMemberRepository")
    private final MemberRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        log.info("[loadUserByUsername] loadUserByUsername 수행: username = {}", userEmail);

        Member user = null;

        try {
            user = userRepository.findById(userEmail)
                    .orElseThrow(() -> new BaseException(ResponseStatus.NOT_FOUND_USER));
        } catch (BaseException e) {

            throw new RuntimeException(e);
        }

        return user;
    }

}
