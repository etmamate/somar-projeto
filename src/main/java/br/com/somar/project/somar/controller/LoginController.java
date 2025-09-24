package br.com.somar.project.somar.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.somar.project.somar.dto.LoginRequestDTO;
import br.com.somar.project.somar.dto.RegisterRequestDTO;
import br.com.somar.project.somar.dto.ResponseDTO;
import br.com.somar.project.somar.infra.security.TokenService;
import br.com.somar.project.somar.model.Usuario;
import br.com.somar.project.somar.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class LoginController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        Usuario usuario = this.usuarioRepository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        if (passwordEncoder.matches( body.senha(), usuario.getSenha())) {
            String token = this.tokenService.generateToken(usuario);
            return ResponseEntity.ok(new ResponseDTO(usuario.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<Usuario> usuario = this.usuarioRepository.findByEmail(body.email());

        if (usuario.isEmpty()) {
            Usuario newUsuario = new Usuario();
            newUsuario.setSenha(passwordEncoder.encode(body.senha()));
            newUsuario.setEmail(body.email());
            newUsuario.setNome(body.nome());
            this.usuarioRepository.save(newUsuario);

            String token = this.tokenService.generateToken(newUsuario);
            return ResponseEntity.ok(new ResponseDTO(newUsuario.getNome(), token));

        }

        return ResponseEntity.badRequest().build();
    }
}
