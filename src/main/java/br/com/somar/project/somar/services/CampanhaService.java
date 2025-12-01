package br.com.somar.project.somar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.somar.project.somar.model.Campanha;
import br.com.somar.project.somar.repository.CampanhaRepository;

@Service
public class CampanhaService {
    private final CampanhaRepository campanhaRepository;

    public CampanhaService(CampanhaRepository campanhaRepository) {
        this.campanhaRepository = campanhaRepository;
    }

    //Listar
    public List<Campanha> listarCampanhas(){
        return campanhaRepository.findAll();
    }

    //Listar por id
    public Optional<Campanha> listarCampanhaById(Long id){
        return campanhaRepository.findById(id);
    }

    //Salvar
    public Campanha salvarCampanha(Campanha campanha){
        return campanhaRepository.save(campanha);
    }

    

}
