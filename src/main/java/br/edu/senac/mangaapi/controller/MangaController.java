package br.edu.senac.mangaapi.controller;

import br.edu.senac.mangaapi.dto.MangaRequest;
import br.edu.senac.mangaapi.dto.MangaResponse;
import br.edu.senac.mangaapi.dto.StatusRequest;
import br.edu.senac.mangaapi.service.MangaService;
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

import java.util.List;

@RestController
@RequestMapping("/api/mangas")
@Tag(name = "Mangás", description = "CRUD de mangás")
public class MangaController {

    private final MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @GetMapping
    @Operation(summary = "Lista mangás não apagados", description = "Retorna registros com status 0 ou 1.")
    public List<MangaResponse> listarTodosNaoApagados() {
        return mangaService.listarTodosNaoApagados();
    }

    @GetMapping("/ativos")
    @Operation(summary = "Lista mangás ativos", description = "Retorna apenas registros com status 1.")
    public List<MangaResponse> listarAtivos() {
        return mangaService.listarAtivos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca mangá por ID")
    public MangaResponse buscarPorId(@PathVariable Integer id) {
        return mangaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria mangá")
    public MangaResponse criar(@Valid @RequestBody MangaRequest request) {
        return mangaService.criar(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza mangá")
    public MangaResponse atualizar(@PathVariable Integer id, @Valid @RequestBody MangaRequest request) {
        return mangaService.atualizar(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Altera o status do mangá", description = "Use -1 para apagado, 0 para inativo ou 1 para ativo.")
    public MangaResponse alterarStatus(@PathVariable Integer id, @Valid @RequestBody StatusRequest request) {
        return mangaService.alterarStatus(id, request.status());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Apaga logicamente mangá", description = "Marca o registro com status -1.")
    public void apagarLogicamente(@PathVariable Integer id) {
        mangaService.apagarLogicamente(id);
    }
}
