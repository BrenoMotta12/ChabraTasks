package com.bm12.chabra.config.security;

import com.bm12.chabra.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe de configuração que define os beans de segurança para o sistema de autenticação.
 * Esta classe fornece a lógica para criptografia de senha, serviço de detalhes do usuário,
 * provedor de autenticação e gerenciador de autenticação.
 */
@Configuration
public class ApplicationConfiguration {

    // Repositório para buscar usuários do banco de dados
    private final UserRepository userRepository;

    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Bean responsável por definir o codificador de senhas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Retorna uma instância do codificador BCrypt.
    }

    /**
     * Bean que fornece o serviço de carregamento dos detalhes do usuário com base no e-mail.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Bean que configura o provedor de autenticação.
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // Define o serviço de usuários
        authProvider.setPasswordEncoder(passwordEncoder());       // Define o codificador de senhas
        return authProvider;
    }

    /**
     * Bean responsável por fornecer o gerenciador de autenticação
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Retorna o gerenciador de autenticação configurado.
    }
}
