package br.edu.senac.mangaapi.dto;

import java.time.LocalDateTime;

public record FavoritoResponse(
        Integer usuarioId,
        Integer mangaId,
        LocalDateTime dataAdicao,
        Integer status
) {
}
