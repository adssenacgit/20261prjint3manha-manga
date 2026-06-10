package br.edu.senac.mangaapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MangaRequest(
        @NotBlank(message = "O título do mangá é obrigatório.")
        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres.")
        String titulo,

        @NotBlank(message = "O autor do mangá é obrigatório.")
        @Size(max = 100, message = "O autor deve ter no máximo 100 caracteres.")
        String autor,

        @Size(max = 255, message = "A URL da capa deve ter no máximo 255 caracteres.")
        String capaUrl,

        @NotBlank(message = "A categoria do mangá é obrigatória.")
        @Size(max = 50, message = "A categoria deve ter no máximo 50 caracteres.")
        String categoria,

        Integer status
) {
}
