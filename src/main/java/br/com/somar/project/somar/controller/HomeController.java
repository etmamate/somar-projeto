package br.com.somar.project.somar.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public ResponseEntity<String> getUsuario() {
        return ResponseEntity.ok("sucesso!");
    }
    
}
