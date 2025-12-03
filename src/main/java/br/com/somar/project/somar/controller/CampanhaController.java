package br.com.somar.project.somar.controller;

// import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.sql.ast.tree.cte.CteMaterialization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import br.com.somar.project.somar.dto.CampanhaRequestDTO;
import br.com.somar.project.somar.dto.CampanhaResponseDTO;
import br.com.somar.project.somar.dto.CampanhaResponseDTO.CampanhaComOngDTO;
import br.com.somar.project.somar.dto.CampanhaResponseDTO.CampanhasListagemDTO;
import br.com.somar.project.somar.model.Campanha;
import br.com.somar.project.somar.model.Usuario;
import br.com.somar.project.somar.repository.CampanhaRepository;
import br.com.somar.project.somar.repository.CampanhaRepository.CampanhaOngProjection;
import br.com.somar.project.somar.services.AuthenticationService;
import br.com.somar.project.somar.services.CampanhaService;
import io.micrometer.core.ipc.http.HttpSender.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/campanha")
@RequiredArgsConstructor
public class CampanhaController {
    private final CampanhaRepository campanhaRepository;
    private final CampanhaService campanhaService;
    private final AuthenticationService authenticationService;
    // @PostMapping(value = "/cadastrar")
    // public ResponseEntity cadastrar(@RequestBody CampanhaRequestDTO body) {

    // Campanha newCampanha = new Campanha();
    // newCampanha.setTitulo(body.titulo());
    // newCampanha.setDescricao(body.descricao());
    // newCampanha.setMeta(body.meta());
    // newCampanha.setLocalizacao(body.localizacao());
    // newCampanha.setCategory(body.category());

    // this.campanhaRepository.save(newCampanha);

    // return ResponseEntity.ok(newCampanha.getTitulo());
    // }

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrar(@RequestBody CampanhaRequestDTO body, HttpServletRequest request) {
        try {

            Campanha newCampanha = new Campanha();
            newCampanha.setTitulo(body.titulo());
            newCampanha.setDescricao(body.descricao());
            newCampanha.setMeta(body.meta());
            newCampanha.setLocalizacao(body.localizacao());
            newCampanha.setOngId(body.ongId());
            newCampanha.setCategory(body.category());

            this.campanhaRepository.save(newCampanha);

            return ResponseEntity.status(HttpStatus.CREATED).body("Campanha criada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar campanha: " + e.getMessage());
        }

    }

    // @PostMapping(value = "/teste")
    // public Campanha cadastrarCampanha(Campanha campanha) {

    // System.out.println(
    // campanha.getTitulo() + campanha.getDescricao() + campanha.getLocalizacao() +
    // campanha.getMeta());
    // return campanhaService.salvarCampanha(campanha);
    // }

    // @GetMapping(value = "/listar")
    // public List<Campanha> listarCampanha() {
    // return campanhaService.listarCampanhas();
    // }

    // @GetMapping(value = "/listar/{id}")
    // public Optional<Campanha> listarCampanhaById(@PathVariable Long id) {
    // return campanhaService.listarCampanhaById(id);
    // }

    @GetMapping(value = "/listar")
    public ResponseEntity<?> listarCampanha() {

       List<CampanhaOngProjection> campanhas = campanhaRepository.findAllCampanhasComOng().stream().collect(Collectors.toList());
    
    if (campanhas.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Nenhuma campanha encontrada");
    }
    
    List<CampanhaResponseDTO.CampanhasListagemDTO> campanhasDTO = campanhas.stream()
            .map(campanha -> new CampanhaResponseDTO.CampanhasListagemDTO(
                    campanha.getId(),
                    campanha.getTitulo(),
                    campanha.getDescricao(),
                    campanha.getMeta(),
                    campanha.getLocalizacao(),
                    campanha.getOngId(),
                    campanha.getOngNome(), // Buscar nome da ONG
                    campanha.getCategory() // Buscar email da ONG
            ))
            .collect(Collectors.toList());
    
    return ResponseEntity.ok(campanhasDTO);

    }

    @GetMapping(value = "/listar/ong/{ong_id}")
    public ResponseEntity<?> listarCampanhasPorOng(@PathVariable Long ong_id) {
        
        List<CampanhaOngProjection> campanhas = campanhaRepository.findCampanhasByOngId(ong_id).stream().collect(Collectors.toList());
        
        List<CampanhaResponseDTO.CampanhasListagemDTO> campanhasDTO = campanhas.stream()
            .map(campanha -> new CampanhaResponseDTO.CampanhasListagemDTO(
                    campanha.getId(),
                    campanha.getTitulo(),
                    campanha.getDescricao(),
                    campanha.getMeta(),
                    campanha.getLocalizacao(),
                    campanha.getOngId(),
                    campanha.getOngNome(), // Buscar nome da ONG
                    campanha.getCategory() // Buscar email da ONG
            ))
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(campanhasDTO);
        
    }
}
