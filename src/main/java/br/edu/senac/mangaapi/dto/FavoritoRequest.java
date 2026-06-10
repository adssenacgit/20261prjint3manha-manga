package br.edu.senac.mangaapi.dto;

import jakarta.validation.constraints.NotNull;

public record FavoritoRequest(
        @NotNull(message = "O ID do usuário é obrigatório.")
        Integer usuarioId,

        @NotNull(message = "O ID do mangá é obrigatório.")
        Integer mangaId,

        Integer status
) {
}
