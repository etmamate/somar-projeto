package br.com.somar.project.somar.services;

import org.springframework.stereotype.Service;

import br.com.somar.project.somar.infra.security.TokenService;
import br.com.somar.project.somar.model.Usuario;
import br.com.somar.project.somar.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public Usuario getUsuarioFromToken(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) {
            throw new RuntimeException("Token não encontrado");
        }

        String userEmail = tokenService.validateToken(token);
        return usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

}
