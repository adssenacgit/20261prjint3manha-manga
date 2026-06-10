package br.edu.senac.mangaapi.controller;

import br.edu.senac.mangaapi.dto.PaginaRequest;
import br.edu.senac.mangaapi.dto.PaginaResponse;
import br.edu.senac.mangaapi.dto.StatusRequest;
import br.edu.senac.mangaapi.service.PaginaService;
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
@RequestMapping("/api/paginas")
@Tag(name = "Páginas", description = "CRUD de páginas")
public class PaginaController {

    private final PaginaService paginaService;

    public PaginaController(PaginaService paginaService) {
        this.paginaService = paginaService;
    }

    @GetMapping
    @Operation(summary = "Lista páginas não apagadas", description = "Retorna registros com status 0 ou 1.")
    public List<PaginaResponse> listarTodosNaoApagados() {
        return paginaService.listarTodosNaoApagados();
    }

    @GetMapping("/ativos")
    @Operation(summary = "Lista páginas ativas", description = "Retorna apenas registros com status 1.")
    public List<PaginaResponse> listarAtivos() {
        return paginaService.listarAtivos();
    }

    @GetMapping("/capitulo/{capituloId}")
    @Operation(summary = "Lista páginas de um capítulo")
    public List<PaginaResponse> listarPorCapitulo(@PathVariable Integer capituloId) {
        return paginaService.listarPorCapitulo(capituloId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca página por ID")
    public PaginaResponse buscarPorId(@PathVariable Integer id) {
        return paginaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria página")
    public PaginaResponse criar(@Valid @RequestBody PaginaRequest request) {
        return paginaService.criar(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza página")
    public PaginaResponse atualizar(@PathVariable Integer id, @Valid @RequestBody PaginaRequest request) {
        return paginaService.atualizar(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Altera o status da página", description = "Use -1 para apagado, 0 para inativo ou 1 para ativo.")
    public PaginaResponse alterarStatus(@PathVariable Integer id, @Valid @RequestBody StatusRequest request) {
        return paginaService.alterarStatus(id, request.status());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Apaga logicamente página", description = "Marca o registro com status -1.")
    public void apagarLogicamente(@PathVariable Integer id) {
        paginaService.apagarLogicamente(id);
    }
}
