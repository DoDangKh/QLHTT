package com.example.qlhtt.Service;

import com.example.qlhtt.Entity.UserLogin;
import com.example.qlhtt.Repos.UserLoginRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    UserLoginRepos userLoginRepos;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin userLogin=userLoginRepos.getUserName(username);

        if(userLogin!=null){
            System.out.print("xxxxxxxxxx USERLOGIN xxxxxxxxxxx "+userLogin.getUsername()+" XXXXXXX");
            UserDetails userDetails= new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    List<GrantedAuthority> grantlist=new ArrayList<GrantedAuthority>();
                    GrantedAuthority authority;
                    if(userLogin.getRole_id()==2) {
                        authority = new SimpleGrantedAuthority("ROLE_EMPLOYEE");
                        grantlist.add(authority);
                    }
                    return grantlist;
                }

                @Override
                public String getPassword() {
                    return userLogin.getPassword();
                }

                @Override
                public String getUsername() {
                    return userLogin.getUsername();
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
            };
            return userDetails;
        }
        else{
            new UsernameNotFoundException("Login Fail!");
        }
        return null;
    }
}
