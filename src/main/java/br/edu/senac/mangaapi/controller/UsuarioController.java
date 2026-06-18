package br.edu.senac.mangaapi.controller;

import br.edu.senac.mangaapi.dto.StatusRequest;
import br.edu.senac.mangaapi.dto.UsuarioRequest;
import br.edu.senac.mangaapi.dto.UsuarioResponse;
import br.edu.senac.mangaapi.service.UsuarioService;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "CRUD de usuários")
@CrossOrigin("*")    
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Lista usuários não apagados", description = "Retorna registros com status 0 ou 1.")
    public List<UsuarioResponse> listarTodosNaoApagados() {
        return usuarioService.listarTodosNaoApagados();
    }

    @GetMapping("/ativos")
    @Operation(summary = "Lista usuários ativos", description = "Retorna apenas registros com status 1.")
    public List<UsuarioResponse> listarAtivos() {
        return usuarioService.listarAtivos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca usuário por ID")
    public UsuarioResponse buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria usuário")
    public UsuarioResponse criar(@Valid @RequestBody UsuarioRequest request) {
        return usuarioService.criar(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza usuário")
    public UsuarioResponse atualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioRequest request) {
        return usuarioService.atualizar(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Altera o status do usuário", description = "Use -1 para apagado, 0 para inativo ou 1 para ativo.")
    public UsuarioResponse alterarStatus(@PathVariable Integer id, @Valid @RequestBody StatusRequest request) {
        return usuarioService.alterarStatus(id, request.status());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Apaga logicamente usuário", description = "Marca o registro com status -1.")
    public void apagarLogicamente(@PathVariable Integer id) {
        usuarioService.apagarLogicamente(id);
    }
}
