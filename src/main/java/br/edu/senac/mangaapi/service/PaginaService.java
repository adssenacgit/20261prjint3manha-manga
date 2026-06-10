package br.edu.senac.mangaapi.service;

import br.edu.senac.mangaapi.dto.PaginaRequest;
import br.edu.senac.mangaapi.dto.PaginaResponse;
import br.edu.senac.mangaapi.entity.Pagina;
import br.edu.senac.mangaapi.repository.PaginaRepository;
import br.edu.senac.mangaapi.util.StatusRegistro;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PaginaService {

    private final PaginaRepository paginaRepository;
    private final CapituloService capituloService;

    public PaginaService(PaginaRepository paginaRepository, CapituloService capituloService) {
        this.paginaRepository = paginaRepository;
        this.capituloService = capituloService;
    }

    @Transactional(readOnly = true)
    public List<PaginaResponse> listarTodosNaoApagados() {
        return paginaRepository.findByStatusNot(StatusRegistro.APAGADO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PaginaResponse> listarAtivos() {
        return paginaRepository.findByStatus(StatusRegistro.ATIVO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PaginaResponse> listarPorCapitulo(Integer capituloId) {
        capituloService.buscarEntidadeNaoApagada(capituloId);
        return paginaRepository.findByCapituloIdAndStatusNot(capituloId, StatusRegistro.APAGADO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PaginaResponse buscarPorId(Integer id) {
        return toResponse(buscarEntidadeNaoApagada(id));
    }

    @Transactional
    public PaginaResponse criar(PaginaRequest request) {
        capituloService.buscarEntidadeNaoApagada(request.capituloId());
        int status = validarStatusOuAtivo(request.status());
        Pagina pagina = new Pagina();
        aplicarDados(pagina, request, status);
        return toResponse(paginaRepository.save(pagina));
    }

    @Transactional
    public PaginaResponse atualizar(Integer id, PaginaRequest request) {
        Pagina pagina = buscarEntidadeNaoApagada(id);
        capituloService.buscarEntidadeNaoApagada(request.capituloId());
        int status = request.status() == null ? pagina.getStatus() : validarStatusOuAtivo(request.status());
        aplicarDados(pagina, request, status);
        return toResponse(paginaRepository.save(pagina));
    }

    @Transactional
    public PaginaResponse alterarStatus(Integer id, Integer status) {
        validarStatus(status);
        Pagina pagina = buscarEntidadePorId(id);
        pagina.setStatus(status);
        return toResponse(paginaRepository.save(pagina));
    }

    @Transactional
    public void apagarLogicamente(Integer id) {
        Pagina pagina = buscarEntidadeNaoApagada(id);
        pagina.setStatus(StatusRegistro.APAGADO);
        paginaRepository.save(pagina);
    }

    private Pagina buscarEntidadeNaoApagada(Integer id) {
        return paginaRepository.findByIdAndStatusNot(id, StatusRegistro.APAGADO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Página não encontrada ou apagada logicamente."));
    }

    private Pagina buscarEntidadePorId(Integer id) {
        return paginaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Página não encontrada."));
    }

    private void aplicarDados(Pagina pagina, PaginaRequest request, Integer status) {
        pagina.setCapituloId(request.capituloId());
        pagina.setNumeroOrdem(request.numeroOrdem());
        pagina.setImagemUrl(request.imagemUrl());
        pagina.setStatus(status);
    }

    private int validarStatusOuAtivo(Integer status) {
        int valor = StatusRegistro.valorOuAtivo(status);
        validarStatus(valor);
        return valor;
    }

    private void validarStatus(Integer status) {
        if (!StatusRegistro.isValido(status)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status inválido. Use -1 para apagado, 0 para inativo ou 1 para ativo.");
        }
    }

    private PaginaResponse toResponse(Pagina pagina) {
        return new PaginaResponse(
                pagina.getId(),
                pagina.getCapituloId(),
                pagina.getNumeroOrdem(),
                pagina.getImagemUrl(),
                pagina.getStatus()
        );
    }
}
