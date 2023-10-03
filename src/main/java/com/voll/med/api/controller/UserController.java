package com.voll.med.api.controller;

import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.voll.med.api.domain.usuario.DetailedUserDTO;
import com.voll.med.api.domain.usuario.UpdateUserDTO;
import com.voll.med.api.domain.usuario.UserListDetailsDTO;
import com.voll.med.api.domain.usuario.UserRegistryDTO;
import com.voll.med.api.domain.usuario.Usuario;
import com.voll.med.api.domain.usuario.UsuarioRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DetailedUserDTO> registryUser (@RequestBody @Valid UserRegistryDTO userData, UriComponentsBuilder uriBuilder) {
        var passwordAsHash = passwordEncoder.encode(userData.password());
        var usuario = new Usuario(userData.withUpdatedPassword(passwordAsHash));
        repository.save(usuario);

        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailedUserDTO(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedUserDTO> getUser (@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);
        return ResponseEntity.ok().body(new DetailedUserDTO(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<UserListDetailsDTO>> getAllUsers (@PageableDefault(size = 10, sort = "login") Pageable pageable) {
        Page<UserListDetailsDTO> users = repository.findByEnabled(Boolean.TRUE, pageable).map(UserListDetailsDTO::new);
        return ResponseEntity.ok(users);        
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UserListDetailsDTO> updatePassword (@RequestBody @Valid UpdateUserDTO updateUserInfo) {
        // get loggedUser
        // if is not the same login throw an exception
        if (!StringUtils.equals(updateUserInfo.password(), updateUserInfo.passwordConfirmation())) {
            throw new RuntimeException ("Verifique os dados e tente novamente");
        }
        passwordEncoder.encode(updateUserInfo.password());
        var user = repository.getReferenceById(updateUserInfo.id());
        user.updatePassword(updateUserInfo);

        return ResponseEntity.ok(new UserListDetailsDTO(user));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity disableUser (@PathVariable Long id) {
        var user = repository.getReferenceById(id);
        user.disableUser();
        return ResponseEntity.noContent().build();
    }
}
