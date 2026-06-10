package br.edu.senac.mangaapi.repository;

import br.edu.senac.mangaapi.entity.Capitulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CapituloRepository extends JpaRepository<Capitulo, Integer> {

    List<Capitulo> findByStatusNot(Integer status);

    List<Capitulo> findByStatus(Integer status);

    List<Capitulo> findByMangaIdAndStatusNot(Integer mangaId, Integer status);

    Optional<Capitulo> findByIdAndStatusNot(Integer id, Integer status);
}
