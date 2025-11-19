package com.josemiel.nicho_nativo_romeral.infrastructure.security;

import com.josemiel.nicho_nativo_romeral.infrastructure.config.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
// import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private final Key key;
    private final long expirationMs;

    // /**
    //  * Constructor para inicializar el servicio JWT.
    //  * @param secret
    //  * @param expirationMs
    //  */
    // public JwtService(
    //         @Value("${security.jwt.secret}") String secret,
    //         @Value("${security.jwt.expiration-ms:86400000}") long expirationMs) {
    //     this.key = Keys.hmacShaKeyFor(secret.getBytes());
    //     this.expirationMs = expirationMs;
    // }

    /**
     * Constructor para inicializar el servicio JWT.
     * @param props
     * 
     * Aquí usamos JwtProperties para obtener la configuración del JWT en lugar de
     * usar @Value directamente como en el constructor comentado arriba.
     */
    public JwtService(JwtProperties props) {
        this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes());
        this.expirationMs = props.getExpirationMs();
    }

    /**
     * Genera un token JWT para un usuario específico.
     * @param user
     * @param extraClaims
     * @return
     */
    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {
        var now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername()) // email
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el nombre de usuario del token JWT.
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        return parse(token).getBody().getSubject();
    }

    /**
     * Verifica la validez de un token JWT.
     * @param token
     * @param user
     * @return
     */
    public boolean isValid(String token, UserDetails user) {
        var body = parse(token).getBody();
        return user.getUsername().equals(body.getSubject()) && body.getExpiration().after(new Date());
    }

    /**
     * Analiza un token JWT y extrae sus claims.
     * @param token
     * @return
     */
    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}
