package br.edu.senac.mangaapi.repository;

import br.edu.senac.mangaapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByStatusNot(Integer status);

    List<Usuario> findByStatus(Integer status);

    Optional<Usuario> findByIdAndStatusNot(Integer id, Integer status);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Integer id);
}
