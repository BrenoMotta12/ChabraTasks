package com.bm12.chabra.config.security;

import com.bm12.chabra.service.JwtService;
import com.bm12.chabra.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Filtro de autenticação JWT responsável por interceptar todas as requisições HTTP
 * e verificar se o token está presente e válido. Caso esteja, a autenticação
 * do usuário é carregada
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthenticationFilter(HandlerExceptionResolver handlerExceptionResolver, JwtService jwtService, UserService userService) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * Método que realiza a filtragem das requisições HTTP, verificando o token no cabeçalho de autenticação.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Verifica se o cabeçalho está presente e se começa com "Bearer " (indicando um token JWT)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Se não houver o token ou ele estiver malformado, passa o controle ao próximo filtro
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        final String userEmail = jwtService.extractUsername(jwt);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Se o e-mail de usuário foi obtido e não há autenticação existente
        if (userEmail != null && authentication == null) {

            // Carrega os detelhes do usuário com base no e-mail extraído do token
            UserDetails userDetails = this.userService.loadUserByUsername(userEmail);

            // Verifica se o token é válido
            if (jwtService.isTokenValid(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Configura o token de autenticação
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
