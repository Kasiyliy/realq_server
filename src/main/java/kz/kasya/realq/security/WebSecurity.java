package kz.kasya.realq.security;

import kz.kasya.realq.services.WorkerService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static kz.kasya.realq.security.SecurityConstants.SIGN_UP_URL;
import static kz.kasya.realq.security.SecurityConstants.SOCKET_URL;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private WorkerService workerService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public WebSecurity(WorkerService workerService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.workerService = workerService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(SOCKET_URL).permitAll()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .antMatcher("/**").cors().disable()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()));
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(workerService).passwordEncoder(bCryptPasswordEncoder);
    }
}