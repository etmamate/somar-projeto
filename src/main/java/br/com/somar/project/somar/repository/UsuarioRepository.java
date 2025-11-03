package br.com.somar.project.somar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.somar.project.somar.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
