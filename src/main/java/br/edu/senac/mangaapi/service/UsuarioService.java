package br.edu.senac.mangaapi.service;

import br.edu.senac.mangaapi.dto.UsuarioRequest;
import br.edu.senac.mangaapi.dto.UsuarioResponse;
import br.edu.senac.mangaapi.entity.Usuario;
import br.edu.senac.mangaapi.repository.UsuarioRepository;
import br.edu.senac.mangaapi.util.StatusRegistro;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarTodosNaoApagados() {
        return usuarioRepository.findByStatusNot(StatusRegistro.APAGADO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarAtivos() {
        return usuarioRepository.findByStatus(StatusRegistro.ATIVO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Integer id) {
        return toResponse(buscarEntidadeNaoApagada(id));
    }

    @Transactional
    public UsuarioResponse criar(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe usuário cadastrado com este e-mail.");
        }
        int status = validarStatusOuAtivo(request.status());
        Usuario usuario = new Usuario();
        aplicarDados(usuario, request, status);
        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponse atualizar(Integer id, UsuarioRequest request) {
        Usuario usuario = buscarEntidadeNaoApagada(id);
        if (usuarioRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe outro usuário cadastrado com este e-mail.");
        }
        int status = request.status() == null ? usuario.getStatus() : validarStatusOuAtivo(request.status());
        aplicarDados(usuario, request, status);
        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponse alterarStatus(Integer id, Integer status) {
        validarStatus(status);
        Usuario usuario = buscarEntidadePorId(id);
        usuario.setStatus(status);
        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void apagarLogicamente(Integer id) {
        Usuario usuario = buscarEntidadeNaoApagada(id);
        usuario.setStatus(StatusRegistro.APAGADO);
        usuarioRepository.save(usuario);
    }

    public Usuario buscarEntidadeNaoApagada(Integer id) {
        return usuarioRepository.findByIdAndStatusNot(id, StatusRegistro.APAGADO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado ou apagado logicamente."));
    }

    private Usuario buscarEntidadePorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
    }

    private void aplicarDados(Usuario usuario, UsuarioRequest request, Integer status) {
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(request.senha());
        usuario.setStatus(status);
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

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCadastro(),
                usuario.getStatus()
        );
    }
}
