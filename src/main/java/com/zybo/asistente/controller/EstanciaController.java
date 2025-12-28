package com.zybo.asistente.controller;

import com.zybo.asistente.dto.EstanciaResponse;
import com.zybo.asistente.service.EstanciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estancias")
@RequiredArgsConstructor
public class EstanciaController {

    private final EstanciaService estanciaService;

    /**
     * ingreso de vehículo
     */
    @PostMapping("/ingreso")
    public ResponseEntity<EstanciaResponse> registrarIngreso(
            @RequestParam String placa) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estanciaService.registrarIngreso(placa));
    }

    /**
     * salida de vehículo
     */
    @PostMapping("/salida")
    public ResponseEntity<EstanciaResponse> registrarSalida(
            @RequestParam String placa) {

        return ResponseEntity.ok(
                estanciaService.registrarSalida(placa)
        );
    }

    /**
     * consultar estancia activa
     */
    @GetMapping("/activa/{placa}")
    public ResponseEntity<EstanciaResponse> obtenerActiva(
            @PathVariable String placa) {

        return ResponseEntity.ok(
                estanciaService.obtenerEstanciaActiva(placa)
        );
    }
}
