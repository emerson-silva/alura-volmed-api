package com.voll.med.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.voll.med.api.domain.usuario.DetailedUserDTO;
import com.voll.med.api.domain.usuario.UpdateUserDTO;
import com.voll.med.api.domain.usuario.UserListDetailsDTO;
import com.voll.med.api.domain.usuario.UserRegistryDTO;
import com.voll.med.api.domain.usuario.Usuario;
import com.voll.med.api.domain.usuario.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository repository;

    @PostMapping
    public ResponseEntity<DetailedUserDTO> registryUser (@RequestBody UserRegistryDTO userData, UriComponentsBuilder uriBuilder) {
        passwordEncoder.encode(userData.password());
        var usuario = new Usuario(userData);
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
        Page<UserListDetailsDTO> users = repository.getByEnabled(Boolean.TRUE, pageable).map(UserListDetailsDTO::new);
        return ResponseEntity.ok(users);        
    }

    @PutMapping
    public ResponseEntity<UserListDetailsDTO> updatePassword (Long id, @RequestBody @Valid UpdateUserDTO updateUserInfo) {
        // get loggedUser
        // if is not the same login throw an exception
        var user = repository.getReferenceById(id);
        user.updatePassword(updateUserInfo);

        return ResponseEntity.ok(new UserListDetailsDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity disableUser (@PathVariable Long id) {
        var user = repository.getReferenceById(id);
        user.disableUser();
        return ResponseEntity.noContent().build();
    }
}
