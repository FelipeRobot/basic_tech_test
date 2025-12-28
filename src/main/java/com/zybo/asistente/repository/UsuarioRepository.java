package com.zybo.asistente.repository;

import com.zybo.asistente.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByDocumento(String documento);

    Optional<Usuario> findByTelefono(String telefono);
}
