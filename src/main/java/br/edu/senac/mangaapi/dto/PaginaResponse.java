package br.edu.senac.mangaapi.dto;

public record PaginaResponse(
        Integer id,
        Integer capituloId,
        Integer numeroOrdem,
        String imagemUrl,
        Integer status
) {
}
