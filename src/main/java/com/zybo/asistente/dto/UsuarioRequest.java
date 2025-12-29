package com.zybo.asistente.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;

    @NotBlank(message = "El documento es obligatorio")
    private String documento;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;
}
