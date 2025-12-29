package com.zybo.asistente.service;

import com.zybo.asistente.domain.entity.Usuario;
import com.zybo.asistente.dto.UsuarioRequest;
import com.zybo.asistente.dto.UsuarioResponse;
import com.zybo.asistente.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioResponse crear(UsuarioRequest request) {

        Usuario usuario = Usuario.builder()
                .nombres(request.getNombres())
                .documento(request.getDocumento())
                .telefono(request.getTelefono())
                .build();

        Usuario guardado = usuarioRepository.save(usuario);

        return mapToResponse(guardado);
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapToResponse(usuario);
    }

    // -------- mapper --------
    private UsuarioResponse mapToResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombres(usuario.getNombres())
                .documento(usuario.getDocumento())
                .telefono(usuario.getTelefono())
                .build();
    }
}
