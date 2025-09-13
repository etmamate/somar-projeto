package br.com.somar.project.somar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.somar.project.somar.model.Ongs;
import br.com.somar.project.somar.services.OngsService;


@RestController
@RequestMapping(value = "/somarapiv1", consumes = "application/json")
public class OngsController {
    private OngsService ongsService;

    public OngsController(OngsService ongsService) {
        this.ongsService = ongsService;
    }

    // GET
    @GetMapping(value = "/listar-ongs")
    public List<Ongs> getOngs() {
        return ongsService.listarOngs();
    }

    // GETBYID
    @GetMapping(value = "/buscar-ongs-id={id}")
    public Optional<Ongs> getOngsById(@PathVariable Long id) {
        return ongsService.listarPorId(id);
    }

    //POST
    @PostMapping("/cadastrar-ongs")
    public Ongs postOngs(@RequestBody Ongs ongs) {
        return ongsService.salvarOngs(ongs);
    }

}
