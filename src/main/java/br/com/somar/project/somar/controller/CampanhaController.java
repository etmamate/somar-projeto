package br.com.somar.project.somar.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.sql.ast.tree.cte.CteMaterialization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.somar.project.somar.dto.CampanhaRequestDTO;
import br.com.somar.project.somar.dto.CampanhaResponseDTO;
import br.com.somar.project.somar.model.Campanha;
import br.com.somar.project.somar.repository.CampanhaRepository;
import br.com.somar.project.somar.services.CampanhaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/campanha")
@RequiredArgsConstructor
public class CampanhaController {
    private final CampanhaRepository campanhaRepository;
    private final CampanhaService campanhaService;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastrar(@RequestBody CampanhaRequestDTO body){
        
        Campanha newCampanha = new Campanha();
        newCampanha.setTitulo(body.titulo());
        newCampanha.setDescricao(body.descricao());
        newCampanha.setMeta(body.meta());
        newCampanha.setLocalizacao(body.localizacao());

        this.campanhaRepository.save(newCampanha);

        return ResponseEntity.ok(newCampanha.getTitulo());
    }

    @GetMapping(value = "/listar")
    public List<Campanha> listarCampanha(Campanha campanha){

        return campanhaService.listarCampanhas();
    }

    @GetMapping(value = "/listar/{id}")
    public Optional<Campanha> listarCampanhaById(@PathVariable Long id){
        System.out.println("Id procurado:" + id);
        return campanhaService.listarCampanhaById(id);
    }
}
