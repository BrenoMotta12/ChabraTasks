package com.bm12.chabra.config.security;



import com.bm12.chabra.model.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuração de segurança do Spring Security.
 * Esta classe define as políticas de autenticação, autorização, e integração
 * com o filtro de autenticação JWT.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationProvider provider;

    public SpringSecurityConfig(AuthenticationFilter authenticationFilter, AuthenticationProvider provider) {
        this.authenticationFilter = authenticationFilter;
        this.provider = provider;
    }

    // Configuração de filtros de segurança do Spring Security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)  // Desabilita proteção CSRF (Cross-Site Request Forgery)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Desabilita proteção CORS (Cross-Origin Resource Sharing)
                .authenticationProvider(provider)  // Configura o provedor de autenticação personalizado
                .authorizeHttpRequests( // Configura as permissões para as requisições HTTP
                        auth -> auth
                                .requestMatchers( // Permissões de acesso publico:
                                        "/v3/api-docs/**", // Documentação do Swagger (v3)
                                        "/v2/api-docs/yaml",        // Documentação do Swagger (v2)
                                        "/swagger-ui/**",           // Interface do Swagger
                                        "/swagger-ui.html",         // Arquivo HTML do Swagger
                                        "/users/signUp",            // Endpoint de cadastro de usuário
                                        "/users/auth"              // Endpoint de autenticação do usuário
                                ).permitAll()// As rotas especificadas acima são acessíveis sem autenticação
                                .requestMatchers("/users", "/users/update", "/status", "/priority").hasRole(String.valueOf(UserRole.ADMIN))
                                .requestMatchers(HttpMethod.DELETE, "/space/{id}").hasRole(String.valueOf(UserRole.ADMIN))
                                .requestMatchers(HttpMethod.DELETE, "/list/{id}").hasRole(String.valueOf(UserRole.ADMIN))
                                .requestMatchers(HttpMethod.DELETE, "/task/{id}").hasRole(String.valueOf(UserRole.ADMIN))
                                .requestMatchers(HttpMethod.DELETE, "/status/{id}").hasRole(String.valueOf(UserRole.ADMIN))
                                .requestMatchers(HttpMethod.DELETE, "/priority/{id}").hasRole(String.valueOf(UserRole.ADMIN))
                                .requestMatchers(HttpMethod.PUT, "/space").hasRole(String.valueOf(UserRole.ADMIN))
                                .requestMatchers(HttpMethod.PUT, "/list").hasRole(String.valueOf(UserRole.ADMIN))
                                .requestMatchers(HttpMethod.POST, "/space").hasRole(String.valueOf(UserRole.ADMIN))
                                .requestMatchers(HttpMethod.POST, "/list").hasRole(String.valueOf(UserRole.ADMIN))
                                .anyRequest().authenticated()  // Todas as outras requisições precisam ser autenticadas
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Define a política de sessão como STATELESS (sem estado)
                  // Configura o provedor de autenticação personalizado
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)  // Adiciona o filtro JWT antes do filtro padrão de autenticação de usuário/senha
                .build();  // Constrói e retorna o filtro de segurança configurado
    }
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}


