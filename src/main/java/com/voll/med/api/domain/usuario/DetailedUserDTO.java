package com.voll.med.api.domain.usuario;

public record DetailedUserDTO(
    Long id,
    String login
) {
    public DetailedUserDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getLogin());
    }
    
}
