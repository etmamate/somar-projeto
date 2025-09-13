package br.com.somar.project.somar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.somar.project.somar.model.Usuario;
import br.com.somar.project.somar.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Listar
    public List<Usuario> listarUsuario() {
        return usuarioRepository.findAll();
    }

    // Salvar
    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // ListaPorID
    public Optional<Usuario> listarUsuarioById(Long id){
        return usuarioRepository.findById(id);
    }

    // Deletar
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
