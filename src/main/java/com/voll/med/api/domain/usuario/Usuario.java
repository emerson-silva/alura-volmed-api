package com.voll.med.api.domain.usuario;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long id;
	String login;
	String senha;
	private Boolean enabled;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

    public Usuario(UserRegistryDTO userData) {
		this.login = userData.login();
		this.senha = userData.password();
    }

    public void disableUser() {
		this.enabled = Boolean.FALSE;
    }

    public void updatePassword(UpdateUserDTO updateUserInfo) {
		if (StringUtils.equals(updateUserInfo.password(), updateUserInfo.passwordConfirmation())) {
            throw new RuntimeException ("Verifique os dados e tente novamente");
        }
		this.senha = updateUserInfo.password();
    }
}
