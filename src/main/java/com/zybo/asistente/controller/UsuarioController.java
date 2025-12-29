package com.zybo.asistente.controller;

import com.zybo.asistente.dto.UsuarioRequest;
import com.zybo.asistente.dto.UsuarioResponse;
import com.zybo.asistente.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Crear usuario
     */
    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(
            @Valid @RequestBody UsuarioRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.crear(request));
    }

    /**
     * Obtener usuario por id
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtener(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                usuarioService.buscarPorId(id)
        );
    }
}
