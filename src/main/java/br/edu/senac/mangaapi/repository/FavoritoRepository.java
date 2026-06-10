package br.edu.senac.mangaapi.repository;

import br.edu.senac.mangaapi.entity.Favorito;
import br.edu.senac.mangaapi.entity.FavoritoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, FavoritoId> {

    List<Favorito> findByStatusNot(Integer status);

    List<Favorito> findByStatus(Integer status);

    List<Favorito> findByIdUsuarioIdAndStatusNot(Integer usuarioId, Integer status);

    List<Favorito> findByIdMangaIdAndStatusNot(Integer mangaId, Integer status);

    Optional<Favorito> findByIdAndStatusNot(FavoritoId id, Integer status);
}
