package br.edu.senac.mangaapi.dto;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Integer id,
        String nome,
        String email,
        LocalDateTime dataCadastro,
        Integer status
) {
}
