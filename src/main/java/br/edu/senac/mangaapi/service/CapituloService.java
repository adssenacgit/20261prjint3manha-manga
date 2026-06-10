package br.edu.senac.mangaapi.service;

import br.edu.senac.mangaapi.dto.CapituloRequest;
import br.edu.senac.mangaapi.dto.CapituloResponse;
import br.edu.senac.mangaapi.entity.Capitulo;
import br.edu.senac.mangaapi.repository.CapituloRepository;
import br.edu.senac.mangaapi.util.StatusRegistro;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CapituloService {

    private final CapituloRepository capituloRepository;
    private final MangaService mangaService;

    public CapituloService(CapituloRepository capituloRepository, MangaService mangaService) {
        this.capituloRepository = capituloRepository;
        this.mangaService = mangaService;
    }

    @Transactional(readOnly = true)
    public List<CapituloResponse> listarTodosNaoApagados() {
        return capituloRepository.findByStatusNot(StatusRegistro.APAGADO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CapituloResponse> listarAtivos() {
        return capituloRepository.findByStatus(StatusRegistro.ATIVO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CapituloResponse> listarPorManga(Integer mangaId) {
        mangaService.buscarEntidadeNaoApagada(mangaId);
        return capituloRepository.findByMangaIdAndStatusNot(mangaId, StatusRegistro.APAGADO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CapituloResponse buscarPorId(Integer id) {
        return toResponse(buscarEntidadeNaoApagada(id));
    }

    @Transactional
    public CapituloResponse criar(CapituloRequest request) {
        mangaService.buscarEntidadeNaoApagada(request.mangaId());
        int status = validarStatusOuAtivo(request.status());
        Capitulo capitulo = new Capitulo();
        aplicarDados(capitulo, request, status);
        return toResponse(capituloRepository.save(capitulo));
    }

    @Transactional
    public CapituloResponse atualizar(Integer id, CapituloRequest request) {
        Capitulo capitulo = buscarEntidadeNaoApagada(id);
        mangaService.buscarEntidadeNaoApagada(request.mangaId());
        int status = request.status() == null ? capitulo.getStatus() : validarStatusOuAtivo(request.status());
        aplicarDados(capitulo, request, status);
        return toResponse(capituloRepository.save(capitulo));
    }

    @Transactional
    public CapituloResponse alterarStatus(Integer id, Integer status) {
        validarStatus(status);
        Capitulo capitulo = buscarEntidadePorId(id);
        capitulo.setStatus(status);
        return toResponse(capituloRepository.save(capitulo));
    }

    @Transactional
    public void apagarLogicamente(Integer id) {
        Capitulo capitulo = buscarEntidadeNaoApagada(id);
        capitulo.setStatus(StatusRegistro.APAGADO);
        capituloRepository.save(capitulo);
    }

    public Capitulo buscarEntidadeNaoApagada(Integer id) {
        return capituloRepository.findByIdAndStatusNot(id, StatusRegistro.APAGADO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Capítulo não encontrado ou apagado logicamente."));
    }

    private Capitulo buscarEntidadePorId(Integer id) {
        return capituloRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Capítulo não encontrado."));
    }

    private void aplicarDados(Capitulo capitulo, CapituloRequest request, Integer status) {
        capitulo.setMangaId(request.mangaId());
        capitulo.setNumero(request.numero());
        capitulo.setTitulo(request.titulo());
        capitulo.setStatus(status);
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

    private CapituloResponse toResponse(Capitulo capitulo) {
        return new CapituloResponse(
                capitulo.getId(),
                capitulo.getMangaId(),
                capitulo.getNumero(),
                capitulo.getTitulo(),
                capitulo.getStatus()
        );
    }
}
