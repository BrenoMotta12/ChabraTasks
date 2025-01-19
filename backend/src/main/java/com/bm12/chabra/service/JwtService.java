package com.bm12.chabra.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/**
 * Serviço responsável por gerar, extrair e validar tokens.
 */
@Service
public class JwtService {

    private final String SECRET_KEY;
    private final long EXPIRATION_TIME;

    public JwtService(@Value("${jwt.secreteKey}") String secretKey,
                      @Value("${jwt.expirationTime}") Long expirationTime) {
        this.SECRET_KEY = secretKey;
        this.EXPIRATION_TIME = expirationTime;
    }

    /**
     * Extrai o nome de usuário (subject) do token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrai um determinado campo (claim) do token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Gera um token para um usuário.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Chama o metodo de contrução de token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails);
    }

    /**
     * Retorna o tempo de expiração do token.
     */
    public Long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    /**
     * Constrói o token com claims adicionais e as informações do usuário.
     */
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Verifica se o token JWT é válido para o usuário fornecido.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Verifica se o token está expirado.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrai a data de expiração do token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrai todos os claims presentes no token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    /**
     * Retorna a chave secreta utilizada para assinar e verificar o token.
     * A chave é convertida a partir de uma string codificada em Base64.
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
