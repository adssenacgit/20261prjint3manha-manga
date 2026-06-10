package br.edu.senac.mangaapi.service;

import br.edu.senac.mangaapi.dto.FavoritoRequest;
import br.edu.senac.mangaapi.dto.FavoritoResponse;
import br.edu.senac.mangaapi.entity.Favorito;
import br.edu.senac.mangaapi.entity.FavoritoId;
import br.edu.senac.mangaapi.repository.FavoritoRepository;
import br.edu.senac.mangaapi.util.StatusRegistro;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioService usuarioService;
    private final MangaService mangaService;

    public FavoritoService(FavoritoRepository favoritoRepository, UsuarioService usuarioService, MangaService mangaService) {
        this.favoritoRepository = favoritoRepository;
        this.usuarioService = usuarioService;
        this.mangaService = mangaService;
    }

    @Transactional(readOnly = true)
    public List<FavoritoResponse> listarTodosNaoApagados() {
        return favoritoRepository.findByStatusNot(StatusRegistro.APAGADO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FavoritoResponse> listarAtivos() {
        return favoritoRepository.findByStatus(StatusRegistro.ATIVO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FavoritoResponse> listarPorUsuario(Integer usuarioId) {
        usuarioService.buscarEntidadeNaoApagada(usuarioId);
        return favoritoRepository.findByIdUsuarioIdAndStatusNot(usuarioId, StatusRegistro.APAGADO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FavoritoResponse> listarPorManga(Integer mangaId) {
        mangaService.buscarEntidadeNaoApagada(mangaId);
        return favoritoRepository.findByIdMangaIdAndStatusNot(mangaId, StatusRegistro.APAGADO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public FavoritoResponse buscarPorId(Integer usuarioId, Integer mangaId) {
        return toResponse(buscarEntidadeNaoApagada(usuarioId, mangaId));
    }

    @Transactional
    public FavoritoResponse criar(FavoritoRequest request) {
        usuarioService.buscarEntidadeNaoApagada(request.usuarioId());
        mangaService.buscarEntidadeNaoApagada(request.mangaId());
        int status = validarStatusOuAtivo(request.status());
        FavoritoId id = new FavoritoId(request.usuarioId(), request.mangaId());

        Favorito favorito = favoritoRepository.findById(id).orElse(null);
        if (favorito != null && !favorito.getStatus().equals(StatusRegistro.APAGADO)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este mangá já está nos favoritos do usuário.");
        }

        if (favorito == null) {
            favorito = new Favorito();
            favorito.setId(id);
        }

        favorito.setStatus(status);
        return toResponse(favoritoRepository.save(favorito));
    }

    @Transactional
    public FavoritoResponse atualizar(Integer usuarioId, Integer mangaId, FavoritoRequest request) {
        Favorito favorito = buscarEntidadeNaoApagada(usuarioId, mangaId);
        if (!usuarioId.equals(request.usuarioId()) || !mangaId.equals(request.mangaId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é permitido alterar a chave composta do favorito. Informe os mesmos IDs da URL no corpo da requisição.");
        }
        usuarioService.buscarEntidadeNaoApagada(request.usuarioId());
        mangaService.buscarEntidadeNaoApagada(request.mangaId());
        int status = request.status() == null ? favorito.getStatus() : validarStatusOuAtivo(request.status());
        favorito.setStatus(status);
        return toResponse(favoritoRepository.save(favorito));
    }

    @Transactional
    public FavoritoResponse alterarStatus(Integer usuarioId, Integer mangaId, Integer status) {
        validarStatus(status);
        Favorito favorito = buscarEntidadePorId(usuarioId, mangaId);
        favorito.setStatus(status);
        return toResponse(favoritoRepository.save(favorito));
    }

    @Transactional
    public void apagarLogicamente(Integer usuarioId, Integer mangaId) {
        Favorito favorito = buscarEntidadeNaoApagada(usuarioId, mangaId);
        favorito.setStatus(StatusRegistro.APAGADO);
        favoritoRepository.save(favorito);
    }

    private Favorito buscarEntidadeNaoApagada(Integer usuarioId, Integer mangaId) {
        return favoritoRepository.findByIdAndStatusNot(new FavoritoId(usuarioId, mangaId), StatusRegistro.APAGADO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Favorito não encontrado ou apagado logicamente."));
    }

    private Favorito buscarEntidadePorId(Integer usuarioId, Integer mangaId) {
        return favoritoRepository.findById(new FavoritoId(usuarioId, mangaId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Favorito não encontrado."));
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

    private FavoritoResponse toResponse(Favorito favorito) {
        return new FavoritoResponse(
                favorito.getId().getUsuarioId(),
                favorito.getId().getMangaId(),
                favorito.getDataAdicao(),
                favorito.getStatus()
        );
    }
}
