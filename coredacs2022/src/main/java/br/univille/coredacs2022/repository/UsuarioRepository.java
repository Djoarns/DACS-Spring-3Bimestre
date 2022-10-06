package br.univille.coredacs2022.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.univille.coredacs2022.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(@Param("usuario") String usuario);

    Optional<Usuario> findByUsuarioAndSenha(@Param("usuario") String usuario, @Param("senha") String senha);
}
