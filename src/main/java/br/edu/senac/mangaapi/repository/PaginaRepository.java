package br.edu.senac.mangaapi.repository;

import br.edu.senac.mangaapi.entity.Pagina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaginaRepository extends JpaRepository<Pagina, Integer> {

    List<Pagina> findByStatusNot(Integer status);

    List<Pagina> findByStatus(Integer status);

    List<Pagina> findByCapituloIdAndStatusNot(Integer capituloId, Integer status);

    Optional<Pagina> findByIdAndStatusNot(Integer id, Integer status);
}
