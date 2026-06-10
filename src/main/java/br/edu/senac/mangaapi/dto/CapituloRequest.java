package br.edu.senac.mangaapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CapituloRequest(
        @NotNull(message = "O ID do mangá é obrigatório.")
        Integer mangaId,

        @NotNull(message = "O número do capítulo é obrigatório.")
        @Min(value = 1, message = "O número do capítulo deve ser maior que zero.")
        Integer numero,

        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres.")
        String titulo,

        Integer status
) {
}
