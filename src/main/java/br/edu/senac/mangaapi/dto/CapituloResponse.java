package br.edu.senac.mangaapi.dto;

public record CapituloResponse(
        Integer id,
        Integer mangaId,
        Integer numero,
        String titulo,
        Integer status
) {
}
