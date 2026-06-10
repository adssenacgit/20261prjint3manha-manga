package br.edu.senac.mangaapi.controller;

import br.edu.senac.mangaapi.dto.CapituloRequest;
import br.edu.senac.mangaapi.dto.CapituloResponse;
import br.edu.senac.mangaapi.dto.StatusRequest;
import br.edu.senac.mangaapi.service.CapituloService;
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
@RequestMapping("/api/capitulos")
@Tag(name = "Capítulos", description = "CRUD de capítulos")
public class CapituloController {

    private final CapituloService capituloService;

    public CapituloController(CapituloService capituloService) {
        this.capituloService = capituloService;
    }

    @GetMapping
    @Operation(summary = "Lista capítulos não apagados", description = "Retorna registros com status 0 ou 1.")
    public List<CapituloResponse> listarTodosNaoApagados() {
        return capituloService.listarTodosNaoApagados();
    }

    @GetMapping("/ativos")
    @Operation(summary = "Lista capítulos ativos", description = "Retorna apenas registros com status 1.")
    public List<CapituloResponse> listarAtivos() {
        return capituloService.listarAtivos();
    }

    @GetMapping("/manga/{mangaId}")
    @Operation(summary = "Lista capítulos de um mangá")
    public List<CapituloResponse> listarPorManga(@PathVariable Integer mangaId) {
        return capituloService.listarPorManga(mangaId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca capítulo por ID")
    public CapituloResponse buscarPorId(@PathVariable Integer id) {
        return capituloService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria capítulo")
    public CapituloResponse criar(@Valid @RequestBody CapituloRequest request) {
        return capituloService.criar(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza capítulo")
    public CapituloResponse atualizar(@PathVariable Integer id, @Valid @RequestBody CapituloRequest request) {
        return capituloService.atualizar(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Altera o status do capítulo", description = "Use -1 para apagado, 0 para inativo ou 1 para ativo.")
    public CapituloResponse alterarStatus(@PathVariable Integer id, @Valid @RequestBody StatusRequest request) {
        return capituloService.alterarStatus(id, request.status());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Apaga logicamente capítulo", description = "Marca o registro com status -1.")
    public void apagarLogicamente(@PathVariable Integer id) {
        capituloService.apagarLogicamente(id);
    }
}
