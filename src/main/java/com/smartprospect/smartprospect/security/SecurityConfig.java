package com.smartprospect.smartprospect.security;

import com.smartprospect.smartprospect.security.filter.CustomAuthenticationFilter;
import com.smartprospect.smartprospect.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Autowired
    public void globalConfig(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception
    {
        auth.jdbcAuthentication()
                .dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder)
                .usersByUsernameQuery("select LOGIN as principal, PASSWORD as credentials, ACTIVE from USER_ACCOUNT where LOGIN = ?")
                .authoritiesByUsernameQuery("select LOGIN as principal, ROLE_NAME as role from USER_ACCOUNT where LOGIN = ?")
                .rolePrefix("ROLE_");
    }

//    @SuppressWarnings("deprecation")
//    @Bean
//    public NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login")
//                .permitAll()
//                .defaultSuccessUrl("/");
//        http.headers().defaultsDisabled().cacheControl();
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("login");
        http.csrf().disable();
        http.headers().defaultsDisabled().cacheControl();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.authorizeRequests().antMatchers("login/**").permitAll(); //The path inside this antMatchers will be accessible by anyone, even non-authenticated people
        http.authorizeRequests().antMatchers(GET, "user/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST, "user/admin/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
