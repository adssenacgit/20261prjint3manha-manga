package br.edu.senac.mangaapi.dto;

public record MangaResponse(
        Integer id,
        String titulo,
        String autor,
        String capaUrl,
        String categoria,
        Integer status
) {
}
