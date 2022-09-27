package rmit.sec.webstorepmicroservice.security;

import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import rmit.sec.webstorepmicroservice.Account.model.Account;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final Logger logger = LogManager.getLogger(this.getClass());

    //Generate the token

    public String generateToken(Authentication authentication){
        Account account = (Account)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + SecurityConstant.EXPIRATION_TIME);

        String userId = Long.toString(account.getId());

        Map<String,Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(account.getId())));
        claims.put("username", account.getUsername());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.SECRET)
                .compact();
    }

    //Validate the token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SecurityConstant.SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            logger.error("Invalid JWT Signature");
            logger.error(e);
        }catch (MalformedJwtException e){
            logger.error("Invalid JWT Token");
            logger.error(e);
        }catch (ExpiredJwtException e){
            logger.error("Expired JWT token");
            logger.error(e);
        }catch (UnsupportedJwtException e){
            logger.error("Unsupported JWT token");
            logger.error(e);
        }catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty");
            logger.error(e);
        }
        return false;
    }

    // Get User ID from token
    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(SecurityConstant.SECRET).parseClaimsJws(token).getBody();
        String id = (String)claims.get("id");

        return Long.parseLong(id);
    }

}

