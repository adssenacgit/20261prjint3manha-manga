package br.edu.senac.mangaapi.controller;

import br.edu.senac.mangaapi.dto.FavoritoRequest;
import br.edu.senac.mangaapi.dto.FavoritoResponse;
import br.edu.senac.mangaapi.dto.StatusRequest;
import br.edu.senac.mangaapi.service.FavoritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@Tag(name = "Favoritos", description = "CRUD de favoritos")
@CrossOrigin("*")    
public class FavoritoController {

    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    @GetMapping
    @Operation(summary = "Lista favoritos não apagados", description = "Retorna registros com status 0 ou 1.")
    public List<FavoritoResponse> listarTodosNaoApagados() {
        return favoritoService.listarTodosNaoApagados();
    }

    @GetMapping("/ativos")
    @Operation(summary = "Lista favoritos ativos", description = "Retorna apenas registros com status 1.")
    public List<FavoritoResponse> listarAtivos() {
        return favoritoService.listarAtivos();
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lista favoritos de um usuário")
    public List<FavoritoResponse> listarPorUsuario(@PathVariable Integer usuarioId) {
        return favoritoService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/manga/{mangaId}")
    @Operation(summary = "Lista favoritos de um mangá")
    public List<FavoritoResponse> listarPorManga(@PathVariable Integer mangaId) {
        return favoritoService.listarPorManga(mangaId);
    }

    @GetMapping("/{usuarioId}/{mangaId}")
    @Operation(summary = "Busca favorito pela chave composta")
    public FavoritoResponse buscarPorId(@PathVariable Integer usuarioId, @PathVariable Integer mangaId) {
        return favoritoService.buscarPorId(usuarioId, mangaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria favorito")
    public FavoritoResponse criar(@Valid @RequestBody FavoritoRequest request) {
        return favoritoService.criar(request);
    }

    @PutMapping("/{usuarioId}/{mangaId}")
    @Operation(summary = "Atualiza favorito")
    public FavoritoResponse atualizar(@PathVariable Integer usuarioId,
                                      @PathVariable Integer mangaId,
                                      @Valid @RequestBody FavoritoRequest request) {
        return favoritoService.atualizar(usuarioId, mangaId, request);
    }

    @PatchMapping("/{usuarioId}/{mangaId}/status")
    @Operation(summary = "Altera o status do favorito", description = "Use -1 para apagado, 0 para inativo ou 1 para ativo.")
    public FavoritoResponse alterarStatus(@PathVariable Integer usuarioId,
                                          @PathVariable Integer mangaId,
                                          @Valid @RequestBody StatusRequest request) {
        return favoritoService.alterarStatus(usuarioId, mangaId, request.status());
    }

    @DeleteMapping("/{usuarioId}/{mangaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Apaga logicamente favorito", description = "Marca o registro com status -1.")
    public void apagarLogicamente(@PathVariable Integer usuarioId, @PathVariable Integer mangaId) {
        favoritoService.apagarLogicamente(usuarioId, mangaId);
    }
}
