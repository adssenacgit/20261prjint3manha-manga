package br.edu.senac.mangaapi.dto;

import jakarta.validation.constraints.NotNull;

public record StatusRequest(
        @NotNull(message = "O status é obrigatório. Use -1, 0 ou 1.")
        Integer status
) {
}
