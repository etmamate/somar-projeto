package br.com.somar.project.somar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.somar.project.somar.dto.CampanhaRequestDTO;
import br.com.somar.project.somar.dto.CampanhaResponseDTO;
import br.com.somar.project.somar.model.Campanha;
import br.com.somar.project.somar.repository.CampanhaRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/campanha")
@RequiredArgsConstructor
public class CampanhaController {
    private final CampanhaRepository campanhaRepository;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastrar(@RequestBody CampanhaRequestDTO body){
        
        Campanha newCampanha = new Campanha();
        newCampanha.setTitulo(body.titulo());
        newCampanha.setDescricao(body.descricao());
        newCampanha.setMeta(body.meta());
        newCampanha.setLocalizacao(body.localizacao());

        this.campanhaRepository.save(newCampanha);

        return ResponseEntity.ok().build();
    }
}
