package br.com.somar.project.somar.dto;

public record CampanhaResponseDTO(
        Long id,
        String titulo,
        String descricao,
        int meta,
        String localizacao,
        Long OngId,
        String ongNome,
        String category) {

    public record CampanhaComOngDTO(
            Long id,
            String titulo,
            String descricao,
            int meta,
            String localizacao,
            Long ongId,
            String ongName,
            String category,
            String ongEmail) {
    }

    public record CampanhasListagemDTO(
            Long id,
            String titulo,
            String descricao,
            int meta,
            String localizacao,
            String category,
            Long ongId) {

    }

}