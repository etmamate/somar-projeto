package br.com.somar.project.somar.controller;

import java.lang.foreign.Linker.Option;
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
            Usuario usuarioLogado = authenticationService.getUsuarioFromToken(request);

            if (usuarioLogado.getTipo().equals("doador")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Apenas Ongs podem criar campanhas");
            }

            Campanha newCampanha = new Campanha();
            newCampanha.setTitulo(body.titulo());
            newCampanha.setDescricao(body.descricao());
            newCampanha.setMeta(body.meta());
            newCampanha.setLocalizacao(body.localizacao());
            newCampanha.setUsuario(usuarioLogado);
            newCampanha.setCategory(body.category());

            this.campanhaRepository.save(newCampanha);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new CampanhaResponseDTO(
                            newCampanha.getId(),
                            newCampanha.getTitulo(),
                            newCampanha.getDescricao(),
                            newCampanha.getMeta(),
                            newCampanha.getLocalizacao(),
                            usuarioLogado.getId(),
                            usuarioLogado.getNome(),
                            newCampanha.getCategory()));

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
    public List<CampanhaComOngDTO> listarCampanha() {
        return campanhaRepository.findAll().stream()
                .map(campanha -> new CampanhaComOngDTO(
                        campanha.getId(),
                        campanha.getTitulo(),
                        campanha.getDescricao(),
                        campanha.getMeta(),
                        campanha.getLocalizacao(),
                        campanha.getUsuario().getId(),
                        campanha.getUsuario().getNome(), // Nome da ONG
                        campanha.getCategory(),
                        campanha.getUsuario().getEmail() // Email da ONG
                ))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/listar/{id}")
    public ResponseEntity<?> listarMinhasCampanhas(@PathVariable Long id, HttpServletRequest request) {
        try {
            // Usuario usuarioLogado = authenticationService.getUsuarioFromToken(request);
            System.out.println("Buscando pelo id: " + id);
            Optional<Campanha> campanhaOptional = campanhaRepository.findById(id);

            if (campanhaOptional.isPresent()) {
                Campanha campanhas = campanhaOptional.get();
                System.out.println("Campanha Encontrada: " + campanhas.getTitulo());

                CampanhaResponseDTO.CampanhasListagemDTO response = new CampanhaResponseDTO.CampanhasListagemDTO(
                        campanhas.getId(),
                        campanhas.getTitulo(),
                        campanhas.getDescricao(),
                        campanhas.getMeta(),
                        campanhas.getLocalizacao(),
                        campanhas.getCategory(),
                        campanhas.getUsuario().getId());

                return ResponseEntity.ok(response);
            } else {
                System.out.println("❌ Campanha não encontrada para ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Campanha não encontrada");
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar campanhas: " + e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao buscar campanha: " + e.getMessage());
        }
    }

    @GetMapping(value = "/listar/ong/{ong_id}")
    public ResponseEntity<?> listarCampanhasPorOng(@PathVariable Long ong_id) {
        try {
            System.out.println("🔍 Buscando campanhas da ONG ID: " + ong_id);

            List<Campanha> campanhas = campanhaRepository.findCampanhasByOngId(ong_id);
            System.out.println("📊 Campanhas encontradas! ".length());

            if (campanhas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Nenhuma campanha encontrada para esta ONG");
            }

            List<CampanhaResponseDTO.CampanhasListagemDTO> response = campanhas.stream()
                    .map(campanha -> new CampanhaResponseDTO.CampanhasListagemDTO(
                            campanha.getId(),
                            campanha.getTitulo(),
                            campanha.getDescricao(),
                            campanha.getMeta(),
                            campanha.getLocalizacao(),
                            campanha.getCategory(),
                            campanha.getUsuario().getId()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("💥 Erro ao buscar campanhas da ONG: " + e.getMessage());
            return ResponseEntity.badRequest()
                    .body("Erro ao buscar campanhas: " + e.getMessage());
        }
    }
}
