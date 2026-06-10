package br.edu.senac.mangaapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PaginaRequest(
        @NotNull(message = "O ID do capítulo é obrigatório.")
        Integer capituloId,

        @NotNull(message = "O número de ordem da página é obrigatório.")
        @Min(value = 1, message = "O número de ordem deve ser maior que zero.")
        Integer numeroOrdem,

        @NotBlank(message = "A URL da imagem é obrigatória.")
        @Size(max = 255, message = "A URL da imagem deve ter no máximo 255 caracteres.")
        String imagemUrl,

        Integer status
) {
}
