package br.com.somar.project.somar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.somar.project.somar.model.Campanha;
import br.com.somar.project.somar.model.Usuario;

public interface CampanhaRepository extends JpaRepository<Campanha, Long> {

    // 🔍 Buscar campanhas por usuário (ONG)
    Optional<Campanha> findById(Long id);

    @Query(value = "SELECT c.id, c.titulo, c.descricao, c.meta, c.localizacao, c.category, c.ong_id FROM campanhas c WHERE c.ong_id = :ongId", nativeQuery = true)
    Optional<Campanha> findCampanhasByOngId(@Param("ongId") Long ongId);

    // ✅ Buscar campanhas por ID do usuário (ong_id)
    // @Query("SELECT c FROM Campanha c WHERE c.ong.id = :ongId")
    // List<Campanha> findCampanhasByOngId(@Param("ongId") Long ongId);

    // Opcional: Buscar por tipo de usuário

    // @Query("SELECT c.id, c.titulo, c.descricao, c.meta, c.localizacao,
    // c.category, c.ong_Id FROM campanhas c WHERE c.ong_id = :id ")
    // Optional<Campanha> findByOngId(Long id);
}
