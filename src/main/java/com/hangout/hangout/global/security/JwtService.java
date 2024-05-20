package com.hangout.hangout.global.security;

import static com.hangout.hangout.global.common.domain.entity.Constants.AUTH_EXCEPTION;
import static com.hangout.hangout.global.common.domain.entity.Constants.AUTH_HEADER;

import com.hangout.hangout.domain.auth.entity.Token;
import com.hangout.hangout.domain.auth.entity.TokenType;
import com.hangout.hangout.domain.auth.repository.TokenRepository;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final TokenRepository tokenRepository;

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(
        UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails,
        long expiration
    ) {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String getUserEmailFromJWT(String token) {

        Claims claims = Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }


    public boolean validateToken(String authToken, HttpServletRequest request) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            request.setAttribute(AUTH_EXCEPTION, new AuthException(ResponseType.JWT_MALFORMED));
        } catch (ExpiredJwtException ex) {
            request.setAttribute(AUTH_EXCEPTION, new AuthException(ResponseType.JWT_EXPIRED));
        } catch (UnsupportedJwtException ex) {
            request.setAttribute(AUTH_EXCEPTION, new AuthException(ResponseType.JWT_UNSUPPORTED));
        } catch (IllegalArgumentException ex) {
            request.setAttribute(AUTH_EXCEPTION, new AuthException(ResponseType.JWT_NULL_OR_EMPTY));
        } catch (PrematureJwtException ex) {
            request.setAttribute(AUTH_EXCEPTION, new AuthException(ResponseType.JWT_PREMATURE));
        } catch (SignatureException ex){
            request.setAttribute(AUTH_EXCEPTION, new AuthException(ResponseType.JWT_SIGNATURE));
        }
        return false;
    }

    public Claims extractAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
        tokenRepository.save(token);
    }

    @Transactional
    public void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        for (Token token : validUserTokens) {
            token.setExpired(true);
            token.setRevoked(true);
        }
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}