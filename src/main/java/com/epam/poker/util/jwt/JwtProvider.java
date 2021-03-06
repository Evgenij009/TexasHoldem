package com.epam.poker.util.jwt;

import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.epam.poker.util.constant.Attribute;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class JwtProvider {
    private static final JwtProvider instance = new JwtProvider() ;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String LINE_SECRET_KEY = ConfigReaderJwt.getSecretKey();
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(LINE_SECRET_KEY));
    private static final long TOKEN_LIFETIME = ConfigReaderJwt.getAccessTokenLifeTime();

    private JwtProvider() {
    }

    public static JwtProvider getInstance() {
        return instance;
    }

    public String generateToken(Map<String, String> claims) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Instant instant = Instant.now();
        return Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(instant.plus(TOKEN_LIFETIME, ChronoUnit.MINUTES)))
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (ExpiredJwtException expEx) {
            LOGGER.info("Token expired: " + expEx);
        } catch (UnsupportedJwtException unsEx) {
            LOGGER.info("Unsupported jwt: " + unsEx);
        } catch (MalformedJwtException mjEx) {
            LOGGER.info("Malformed jwt: " + mjEx);
        } catch (SignatureGenerationException sEx) {
            LOGGER.info("Invalid signature: " + sEx);
        } catch (Exception e) {
            LOGGER.info("invalid token: " + e);
        }
        return false;
    }

    public Jws<Claims> getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .setAllowedClockSkewSeconds(10)
                .build()
                .parseClaimsJws(token);
    }

    public Optional<Cookie> getTokenFromCookies(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(Attribute.ACCESS_TOKEN))
                .findFirst();
    }
}
