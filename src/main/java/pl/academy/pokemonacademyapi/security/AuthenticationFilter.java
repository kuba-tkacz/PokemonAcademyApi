package pl.academy.pokemonacademyapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.academy.pokemonacademyapi.user.User;
import pl.academy.pokemonacademyapi.user.UserWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final long EXPIRE_TIME = 3_600_000L;
    private final String signature;
    private final String tokenType;
    private final String headerKey;
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(String headerKey, String signature, String tokenType, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.signature = signature;
        this.tokenType = tokenType;
        this.headerKey = headerKey;
        setFilterProcessesUrl("/user/signin");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Optional<User> user = Optional.empty();
        try {
            user = Optional.ofNullable(new ObjectMapper().readValue(request.getInputStream(), User.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        User userFromDb = user.orElseThrow(()->{
            throw new NoSuchElementException();
        });
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userFromDb.getName(),
                userFromDb.getPassword(),
                Collections.emptyList()
        ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .setSubject(((UserWrapper)(authResult.getPrincipal())).getUsername())
                .signWith(SignatureAlgorithm.HS512, signature)
                .compact();
        response.addHeader("Access-Control-Expose-Headers", headerKey);
        response.addHeader(headerKey, tokenType + " " + token);
    }
}

