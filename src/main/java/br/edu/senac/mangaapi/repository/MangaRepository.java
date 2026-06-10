package br.edu.senac.mangaapi.repository;

import br.edu.senac.mangaapi.entity.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MangaRepository extends JpaRepository<Manga, Integer> {

    List<Manga> findByStatusNot(Integer status);

    List<Manga> findByStatus(Integer status);

    Optional<Manga> findByIdAndStatusNot(Integer id, Integer status);
}
