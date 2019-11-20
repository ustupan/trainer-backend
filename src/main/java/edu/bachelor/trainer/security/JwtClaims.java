package edu.bachelor.trainer.security;

import edu.bachelor.trainer.configuration.SecurityConstants;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JwtClaims {

    private static final Logger log = LoggerFactory.getLogger(JwtClaims.class);
    private Jws<Claims> claimsJws;

    public JwtClaims(String JwtToken) {
        try{
            byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();


            claimsJws = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(JwtToken.replace(SecurityConstants.TOKEN_PREFIX,""));

        } catch (ExpiredJwtException exception) {
            log.warn("Request to parse expired JWT : {} failed : {}", JwtToken, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", JwtToken, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.warn("Request to parse invalid JWT : {} failed : {}", JwtToken, exception.getMessage());
        } catch (SignatureException exception) {
            log.warn("Request to parse JWT with invalid signature : {} failed : {}", JwtToken, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", JwtToken, exception.getMessage());
        }
    }

    public Long getUserId(){
        return Long.parseLong(claimsJws.getBody().getId());
    }
    public String getUserUsername() {return claimsJws.getBody().getSubject(); }
}
