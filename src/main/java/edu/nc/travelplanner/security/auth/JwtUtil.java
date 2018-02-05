package edu.nc.travelplanner.security.auth;

import edu.nc.travelplanner.table.Client;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil implements Serializable {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_ID = "id";
    private static final String CLAIM_KEY_ROLE = "role";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${auth.secret}")
    private String secret;

    @Value("${auth.expires}")
    private Long expiration;

    public Long getUserIdFromToken(String token) {
        Long id = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            id = Long.valueOf((Integer) claims.get(CLAIM_KEY_ID));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return id;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreationDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Client client = (Client) userDetails;
        claims.put(CLAIM_KEY_ID, client.getClientId());
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_ROLE, userDetails.getAuthorities());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        Client client = (Client) userDetails;
        final String username = getUsernameFromToken(token);
        if(username == null) {
            return false;
        } else {
            return username.equals(client.getUsername()) && !isTokenExpired(token);
        }
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
