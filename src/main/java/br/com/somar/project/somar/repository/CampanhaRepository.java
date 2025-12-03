package br.com.somar.project.somar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.somar.project.somar.dto.CampanhaResponseDTO;
import br.com.somar.project.somar.dto.CampanhaResponseDTO.CampanhaComOngDTO;
import br.com.somar.project.somar.model.Campanha;
import br.com.somar.project.somar.model.Usuario;
import lombok.AllArgsConstructor;

public interface CampanhaRepository extends JpaRepository<Campanha, Long> {

    // 🔍 Buscar campanhas por usuário (ONG)
    Optional<Campanha> findById(Long id);

    @Query(value = "SELECT c.id, c.titulo, c.descricao, c.meta, c.localizacao, c.category, c.ong_id, u.nome AS ongNome FROM campanhas c JOIN usuarios u ON u.id = c.ong_id WHERE c.ong_id = :ongId", nativeQuery = true)
    List<CampanhaOngProjection> findCampanhasByOngId(@Param("ongId") Long ong_Id);

    @Query(value = """
            SELECT c.id, c.titulo, c.descricao, c.meta, c.localizacao, c.ong_id,u.nome as ongNome,c.category, u.email FROM campanhas c JOIN usuarios u ON u.id = c.ong_id""", nativeQuery = true)
    List<CampanhaOngProjection> findAllCampanhasComOng();

    public interface CampanhaOngProjection {
        Long getId();

        String getTitulo();

        String getDescricao();

        Integer getMeta();

        String getLocalizacao();

        Long getOngId();

        String getOngNome();

        String getCategory();

        String getOngEmail();
    }
}
