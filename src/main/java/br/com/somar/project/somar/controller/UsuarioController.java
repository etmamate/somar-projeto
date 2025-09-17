package br.com.somar.project.somar.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.somar.project.somar.model.Usuario;
import br.com.somar.project.somar.services.UsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/somar/api/v1", consumes = "application/json")
public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET
    @GetMapping(value = "/listar-usuarios")
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuario();
        // .stream().map(UsuarioResponseDTO::new);
    }

    // GETBYID
    @GetMapping("/buscar-usuario-id={id}")
    public Optional<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.listarUsuarioById(id);
        // .stream().map(UsuarioResponseDTO::new);
    }

    // POST
    @PostMapping(value = "/cadastro-usuario", consumes = "application/json")
    public Usuario cadastrarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.salvarUsuario(usuario);
    }

}
