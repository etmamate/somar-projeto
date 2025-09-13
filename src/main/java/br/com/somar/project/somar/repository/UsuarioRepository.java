package br.com.somar.project.somar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.somar.project.somar.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
