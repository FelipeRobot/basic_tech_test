package com.zybo.asistente.dto;

import java.time.LocalDateTime;

import com.zybo.asistente.domain.enums.EstadoEstancia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class EstanciaResponse {

    private Long id;
    private String placa;
    private LocalDateTime horaIngreso;
    private LocalDateTime horaSalida;
    private Long minutos;
    private Long valorCobrado;
    private EstadoEstancia estado;
}
