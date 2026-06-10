package br.edu.senac.mangaapi.service;

import br.edu.senac.mangaapi.dto.MangaRequest;
import br.edu.senac.mangaapi.dto.MangaResponse;
import br.edu.senac.mangaapi.entity.Manga;
import br.edu.senac.mangaapi.repository.MangaRepository;
import br.edu.senac.mangaapi.util.StatusRegistro;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MangaService {

    private final MangaRepository mangaRepository;

    public MangaService(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    @Transactional(readOnly = true)
    public List<MangaResponse> listarTodosNaoApagados() {
        return mangaRepository.findByStatusNot(StatusRegistro.APAGADO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MangaResponse> listarAtivos() {
        return mangaRepository.findByStatus(StatusRegistro.ATIVO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MangaResponse buscarPorId(Integer id) {
        return toResponse(buscarEntidadeNaoApagada(id));
    }

    @Transactional
    public MangaResponse criar(MangaRequest request) {
        int status = validarStatusOuAtivo(request.status());
        Manga manga = new Manga();
        aplicarDados(manga, request, status);
        return toResponse(mangaRepository.save(manga));
    }

    @Transactional
    public MangaResponse atualizar(Integer id, MangaRequest request) {
        Manga manga = buscarEntidadeNaoApagada(id);
        int status = request.status() == null ? manga.getStatus() : validarStatusOuAtivo(request.status());
        aplicarDados(manga, request, status);
        return toResponse(mangaRepository.save(manga));
    }

    @Transactional
    public MangaResponse alterarStatus(Integer id, Integer status) {
        validarStatus(status);
        Manga manga = buscarEntidadePorId(id);
        manga.setStatus(status);
        return toResponse(mangaRepository.save(manga));
    }

    @Transactional
    public void apagarLogicamente(Integer id) {
        Manga manga = buscarEntidadeNaoApagada(id);
        manga.setStatus(StatusRegistro.APAGADO);
        mangaRepository.save(manga);
    }

    public Manga buscarEntidadeNaoApagada(Integer id) {
        return mangaRepository.findByIdAndStatusNot(id, StatusRegistro.APAGADO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mangá não encontrado ou apagado logicamente."));
    }

    private Manga buscarEntidadePorId(Integer id) {
        return mangaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mangá não encontrado."));
    }

    private void aplicarDados(Manga manga, MangaRequest request, Integer status) {
        manga.setTitulo(request.titulo());
        manga.setAutor(request.autor());
        manga.setCapaUrl(request.capaUrl());
        manga.setCategoria(request.categoria());
        manga.setStatus(status);
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

    private MangaResponse toResponse(Manga manga) {
        return new MangaResponse(
                manga.getId(),
                manga.getTitulo(),
                manga.getAutor(),
                manga.getCapaUrl(),
                manga.getCategoria(),
                manga.getStatus()
        );
    }
}
