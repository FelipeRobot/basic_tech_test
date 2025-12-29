package com.zybo.asistente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UsuarioResponse {

    private Long id;
    private String nombres;
    private String documento;
    private String telefono;
}
