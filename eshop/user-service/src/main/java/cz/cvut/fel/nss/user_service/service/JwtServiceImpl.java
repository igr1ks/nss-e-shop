package cz.cvut.fel.nss.user_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Implementation of JwtService
 */
@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey signingKey;
    private final long expirationMs;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret,
                          @Value("${jwt.expiration-ms}") long expirationMs) {
        // we need a key of at least 256 bits for HS256, so we use the secret string as a key by encoding it as UTF-8 bytes
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        // Roles are stored as a claim so other services (Product/Order)
        // can read them without a DB lookup
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .subject(userDetails.getUsername())   // username = email
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String emailFromToken = extractEmail(token);
            return emailFromToken.equals(userDetails.getUsername()) && !isExpired(token);
        } catch (Exception ex) {
            // Bad signature, malformed token or etc. = not valid
            return false;
        }
    }

    // Helpers
    private boolean isExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }
}