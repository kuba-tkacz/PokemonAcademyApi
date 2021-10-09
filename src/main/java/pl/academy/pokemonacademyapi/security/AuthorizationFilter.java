package pl.academy.pokemonacademyapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final String signature;
    private final String tokenType;
    private final String headerKey;

    public AuthorizationFilter(String headerKey, String signature, String tokenType,
                               AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.signature = signature;
        this.headerKey = headerKey;
        this.tokenType = tokenType;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(headerKey);
        if (header == null || !header.startsWith(tokenType)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            SecurityContextHolder.getContext().setAuthentication(validateToken(header));
        } catch (SignatureException | MalformedJwtException e){
            e.printStackTrace();
        } finally {
            chain.doFilter(request, response);
        }
    }

    private UsernamePasswordAuthenticationToken validateToken(String header) {
        String userName = Jwts.parser()
                .setSigningKey(signature)
                .parseClaimsJws(header.replace(tokenType + " ", ""))
                .getBody().getSubject();
        return userName != null ? new UsernamePasswordAuthenticationToken(userName, null, Collections.emptyList()) : null;
    }
}
