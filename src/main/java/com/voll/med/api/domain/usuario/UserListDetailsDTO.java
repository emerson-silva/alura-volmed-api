package com.voll.med.api.domain.usuario;

public record UserListDetailsDTO(Long id, String login) {
    public UserListDetailsDTO (Usuario user) {
        this(user.getId(), user.getLogin());
    }
}
