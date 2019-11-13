package edu.bachelor.trainer.security;

import edu.bachelor.trainer.configuration.SecurityConstants;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        if (authenticationToken == null) {
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String requestToken = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (requestToken != null && requestToken.startsWith(SecurityConstants.TOKEN_PREFIX)){
            try{

                byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();


                Jws<Claims> claimsJws = Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(requestToken.replace(SecurityConstants.TOKEN_PREFIX,""));

                String username = claimsJws.getBody().getSubject();
//                Set<?> authoraities = ((Set<?>) claimsJws.getBody()
//                        .get("role"));

                List<SimpleGrantedAuthority> authorities = ((List<?>) claimsJws.getBody()
                        .get("role")).stream()
                        .map(authority -> new SimpleGrantedAuthority((String) authority))
                        .collect(Collectors.toList());

                if (username != null) return new UsernamePasswordAuthenticationToken(username, null, authorities);

            } catch (ExpiredJwtException exception) {
                log.warn("Request to parse expired JWT : {} failed : {}", requestToken, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", requestToken, exception.getMessage());
            } catch (MalformedJwtException exception) {
                log.warn("Request to parse invalid JWT : {} failed : {}", requestToken, exception.getMessage());
            } catch (SignatureException exception) {
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", requestToken, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", requestToken, exception.getMessage());
            }
        }

        return null;
    }
}
