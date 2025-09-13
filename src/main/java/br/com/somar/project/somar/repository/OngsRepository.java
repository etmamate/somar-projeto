package br.com.somar.project.somar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.somar.project.somar.model.Ongs;

public interface OngsRepository extends JpaRepository<Ongs, Long>{
    
}
