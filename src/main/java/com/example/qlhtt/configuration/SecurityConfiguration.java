package com.example.qlhtt.configuration;

import com.example.qlhtt.Service.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    CustomerUserDetailService customerUserDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("Ly").password(passwordEncoder().encode("123456")).authorities("ROLE_USER");
        auth.userDetailsService(customerUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/home","/public/**","/product/**").permitAll();
        http.csrf().disable().authorizeRequests().antMatchers("/cart/**").hasRole("CUSTOMER")
                .antMatchers("/Employee/**").hasRole("EMPLOYEE").anyRequest()
                .authenticated().and().formLogin().loginPage("/login").permitAll().loginProcessingUrl("/login").usernameParameter("username")
                .passwordParameter("password").defaultSuccessUrl("/LoginHandel").failureUrl("/login?error=failed").and()
                .exceptionHandling().accessDeniedPage("/login?error=deny");
//        http.csrf().disable().authorizeRequests().antMatchers("/Employee/**").hasRole("EMPLOYEE").anyRequest()
//                        .authenticated().and().formLogin().loginPage("/login").permitAll().loginProcessingUrl("/login").usernameParameter("username")
//                        .passwordParameter("password").defaultSuccessUrl("/Employee").failureUrl("login?error=failed").and()
//                        .exceptionHandling().accessDeniedPage("/login?error=deny");
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/home");
    }
}
