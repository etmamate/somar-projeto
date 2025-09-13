package br.com.somar.project.somar.services;

import br.com.somar.project.somar.model.Ongs;
import br.com.somar.project.somar.repository.OngsRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class OngsService {
    
    private OngsRepository ongsRepository;

    public OngsService(OngsRepository ongsRepository) {
        this.ongsRepository = ongsRepository;
    }

    //Cadastrar
    public Ongs salvarOngs(Ongs ongs){
        return ongsRepository.save(ongs);
    } 

    //Listar
    public List<Ongs> listarOngs(){
        return ongsRepository.findAll();
    }

    //Pesquisar por Id

    public Optional<Ongs> listarPorId(Long id){
        return ongsRepository.findById(id);
    }

    //Deletar
    public void deletarOng(Long id){
        ongsRepository.deleteById(id);
    }
}
