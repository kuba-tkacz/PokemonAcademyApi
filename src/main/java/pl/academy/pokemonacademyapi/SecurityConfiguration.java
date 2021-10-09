package pl.academy.pokemonacademyapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.academy.pokemonacademyapi.security.AuthenticationFilter;
import pl.academy.pokemonacademyapi.security.AuthorizationFilter;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final String headerKey;
    private final String signature;
    private final String tokenType;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(@Value("${paa.authorization-key}") String headerKey,
                                 @Value("${paa.signature}") String signature,
                                 @Value("${paa.token-type}") String tokenType,
                                 UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.headerKey = headerKey;
        this.signature = signature;
        this.tokenType = tokenType;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/pokemon/**").authenticated()
                .antMatchers("/user").permitAll()
                .and()
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new AuthenticationFilter(headerKey, signature, tokenType, authenticationManager()))
                .addFilter(new AuthorizationFilter(headerKey, signature, tokenType, authenticationManager()))
                .headers().frameOptions().disable();//obłsuga h2
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }
}