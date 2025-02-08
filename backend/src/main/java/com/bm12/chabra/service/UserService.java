package com.bm12.chabra.service;

import com.bm12.chabra.config.validation.AlreadyExistsException;
import com.bm12.chabra.config.validation.NotFoundException;
import com.bm12.chabra.config.validation.UnauthorizedException;
import com.bm12.chabra.dto.user.*;
import com.bm12.chabra.model.User;
import com.bm12.chabra.model.enums.UserRole;
import com.bm12.chabra.repository.UserRepository;
import jakarta.activation.DataHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;

@Service
public class UserService implements UserDetailsService {

    // Dependências
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            Optional<User> optional = this.userRepository.findByEmail(username);

            return optional.map(user -> {
                // Constrói o objeto UserDetails com authorities derivadas do campo role
                Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

                return new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        authorities // Adiciona as permissões extraídas do User
                );
            }).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        } catch (Exception e) {
            throw new RuntimeException("Error loading user", e);
        }
    }

    /**
     * Cria um novo usuário
     *
     * @param saveUser DTO com os dados do usuário para cadastro
     * @return GetUser com os dados do usuário criado
     */
    public ResponseEntity<GetUser> signUp(SaveUser saveUser) {


        // Verifica se o usuário já existe
        if (this.userRepository.existsByEmail(saveUser.getEmail())) {
            throw new AlreadyExistsException("Já existe um usuário com esse email");
        }

        User user = new User(
                saveUser.getName(),
                saveUser.getEmail().toLowerCase(),
                this.passwordEncoder.encode(saveUser.getPassword()),
                saveUser.getRole() != null ? saveUser.getRole(): UserRole.USER
        );
        try {
            // Cria um novo usuário
            user = this.userRepository.save(user);
            return ResponseEntity.created(URI.create("/users/" + user.getId())).body(new GetUser(user));
        } catch (Exception e) {
            throw new RuntimeException("Error creating user");
        }

    }

    /**
     * Retorna uma lista com todos os usuários
     *
     * @return Lista de GetUser com os dados dos usuários
     */
    public ResponseEntity<List<GetUser>> getAllUsers() {
        try {

            // Retorna a lista de users do banco de dados
            List<User> listUser = this.userRepository.findAll();

            // Converte a lista de users para uma lista de GetUser
            List<GetUser> listGetUser = new ArrayList<>();
            for (User l : listUser) {
                listGetUser.add(new GetUser(l));
            }

            return ResponseEntity.ok(listGetUser)
                    ;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching users");
        }
    }

    /**
     * Retorna um usuário pelo ID
     *
     * @param id ID do usuário
     * @return GetUser com os dados do usuário
     */
    public ResponseEntity<GetUser> getUserById(UUID id) {

        // Carrega o usuário
        User user = findUserById(id);

        // Verifica se o usuário está permitido a acessar essa funcionalidade
        VerifyUserPermission(user);

        return ResponseEntity.ok(new GetUser(user));

    }




    /**
     * Atualiza um usuário
     *
     * @param updateUser Objeto com os dados do usuário para atualizar
     * @return GetUser com os dados do usuário atualizado
     */
    public ResponseEntity<GetUser> updateUser(UpdateUser updateUser) {

        // Carrega o usuário
        User user = findUserById(updateUser.getId());

        // Verifica se já existe outro usuário com esse email cadastrado
        Optional<User> userOptional = this.userRepository.findByEmail(updateUser.getEmail());
        if (userOptional.isPresent() && !Objects.equals(userOptional.get().getId(), updateUser.getId())) {
            throw new AlreadyExistsException("There is already a user with this email");
        }

        // Seta o novo nome e novo email do usuário
        user.setName(updateUser.getName());
        user.setEmail(updateUser.getEmail());
        user.setRole(updateUser.getRole());

        if (updateUser.getPassword() != null & !updateUser.getPassword().isEmpty()) {
            user.setPassword(this.passwordEncoder.encode(updateUser.getPassword()));
        }

        if (updateUser.getDeleted()) {
            user.setDeletedAt(LocalDateTime.now());
        } else {
            user.setDeletedAt(null);
        }


        try {
            user = this.userRepository.save(user);
            return ResponseEntity.ok(new GetUser(user));
        } catch (Exception e) {
            throw new RuntimeException("Error updating user");

        }
    }



    /**
     * Autentica um usuário
     *
     * @param authUser Objeto com email e senha
     * @return Token JWT
     */
    public ResponseEntity<GetAuthUser> auth(AuthUser authUser) {
        User user = this.userRepository.findByEmail(authUser.getEmail()).orElseThrow(() -> {
            return new NotFoundException("Usuário não encontrado");
        });

        if (user.getDeletedAt() != null) {
            throw new UnauthorizedException("Usuário desativado");
        }

        // Verifica se a senha é valida
        if (!this.passwordEncoder.matches(authUser.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Senha inválida");
        }

        // Gera o token do usuário
        String token = this.jwtService.generateToken(user);
        return ResponseEntity.ok(new GetAuthUser(user, token));
    }


    /**
     * Busca um usuário pelo ID
     *
     * @param userId ID do usuário
     * @return User
     */
    public User findUserById(UUID userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> {
            return new NotFoundException("Usuário não encontrado");
        });
    }


    // Verifica se o usuário é administrador ou se ele é o usuário proprietário do token
    public static void VerifyUserPermission(User user) {
        org.springframework.security.core.userdetails.User userDetails = getUserDetails();

        if (userDetails == null) {
            throw new UnauthorizedException("Usuário não autenticado");
        } else if (
                !(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")))
                        && !userDetails.getUsername().equals(user.getEmail())
        ) {
            throw new UnauthorizedException("Usuário não autenticado");
        }
    }

    public static org.springframework.security.core.userdetails.User GetUserIfNotAdminOrModerator() {

        org.springframework.security.core.userdetails.User userDetails = getUserDetails();

        if (userDetails == null) {
            throw new UnauthorizedException("Usuário não autenticado");
        } else if (userDetails.getAuthorities().stream().anyMatch(auth ->
                auth.getAuthority().equals("ROLE_ADMIN")
                || auth.getAuthority().equals("ROLE_MODERATOR")))
        {
            return null;
        }
        return userDetails;

    }

    private static org.springframework.security.core.userdetails.User getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        }

        return null;
    }

}
