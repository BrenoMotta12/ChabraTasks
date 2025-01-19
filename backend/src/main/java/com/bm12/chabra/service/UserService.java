package com.bm12.chabra.service;

import com.bm12.chabra.config.validation.AlreadyExistsException;
import com.bm12.chabra.config.validation.NotFoundException;
import com.bm12.chabra.config.validation.UnauthorizedException;
import com.bm12.chabra.dto.user.AuthUser;
import com.bm12.chabra.dto.user.GetUser;
import com.bm12.chabra.dto.user.SaveUser;
import com.bm12.chabra.dto.user.UpdateUser;
import com.bm12.chabra.model.User;
import com.bm12.chabra.model.UserRole;
import com.bm12.chabra.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.*;

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
            }).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
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
            throw new AlreadyExistsException("There is already a user with this email");
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
            return ResponseEntity.created(URI.create("/users/" + user.getId())).body(GetUser.converter(user));
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
                listGetUser.add(GetUser.converter(l));
            }

            return ResponseEntity.ok(listGetUser);
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

        return ResponseEntity.ok(GetUser.converter(user));

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

        // Verifica se a senha está correta para evitar que um usuário consiga alterar a senha de outro
        if (!this.passwordEncoder.matches(updateUser.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid password");
        }

        // Verifica se já existe outro usuário com esse email cadastrado
        Optional<User> userOptional = this.userRepository.findByEmail(updateUser.getEmail());
        if (userOptional.isPresent() && !Objects.equals(userOptional.get().getId(), updateUser.getId())) {
            throw new AlreadyExistsException("There is already a user with this email");
        }

        // Seta o novo nome e novo email do usuário
        user.setName(updateUser.getName());
        user.setEmail(updateUser.getEmail());

        // Se o usuário informar uma nova senha, a mesma é atualizada
        if (updateUser.getNewPassword() != null) {
            user.setPassword(this.passwordEncoder.encode(updateUser.getNewPassword()));
        }
        try {
            user = this.userRepository.save(user);
            return ResponseEntity.ok(GetUser.converter(user));
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
    public ResponseEntity<String> auth(AuthUser authUser) {
        User user = this.userRepository.findByEmail(authUser.getEmail()).orElseThrow(() -> {
            return new NotFoundException("User not found");
        });

        // Verifica se a senha é valida
        if (!this.passwordEncoder.matches(authUser.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid password");
        }

        // Gera o token do usuário
        String token = this.jwtService.generateToken(user);
        return ResponseEntity.ok(token);
    }


    /**
     * Busca um usuário pelo ID
     *
     * @param userId ID do usuário
     * @return User
     */
    public User findUserById(UUID userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> {
            return new NotFoundException("User not found");
        });
    }
}
