package com.zybo.asistente.controller;

import com.zybo.asistente.dto.VehiculoRequest;
import com.zybo.asistente.dto.VehiculoResponse;
import com.zybo.asistente.service.VehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    /**
     * Registrar vehículo
     */
    @PostMapping
    public ResponseEntity<VehiculoResponse> registrar(
            @Valid @RequestBody VehiculoRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        vehiculoService.registrarVehiculo(
                                request.getPlaca(),
                                request.getUsuarioId()
                        )
                );
    }

    /**
     * Buscar vehículo por placa
     */
    @GetMapping("/{placa}")
    public ResponseEntity<VehiculoResponse> buscarPorPlaca(
            @PathVariable String placa) {

        return ResponseEntity.ok(
                vehiculoService.buscarPorPlaca(placa)
        );
    }
}
