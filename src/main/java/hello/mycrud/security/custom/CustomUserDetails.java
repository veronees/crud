package hello.mycrud.security.custom;

import hello.mycrud.crud.domain.entity.Role;
import hello.mycrud.crud.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;
    //사용자의 특정한 권한에 대해서 리턴해주는 메서드

    /**
     * 해당 유저의 권한 목록
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                Role role = user.getRole();
                String authority = role.toString();

                return authority;
            }
        });
        return collection;
    }

    /**
     * 비밀번호
     * @return
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 계정의 고유한 값을 리턴해줄 수 있음. ex)PK값, 이메일, ID
     * 여기서는 username 즉, ID를 넘겨주고 있음
     * @return
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 계정 만료 여부
     * true: 만료 안됨
     * false: 만료
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부
     * true: 잠기지 않음
     * false: 잠김
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 비밀번호 만료 여부
     * true: 만료 안됨
     * false: 만료
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자 활성화 여부
     * true: 활성화
     * false: 비활성화
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
