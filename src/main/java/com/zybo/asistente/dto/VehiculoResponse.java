package com.zybo.asistente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class VehiculoResponse {

    private Long id;
    private String placa;
    private Long usuarioId;
}
