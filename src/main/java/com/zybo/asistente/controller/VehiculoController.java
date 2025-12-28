package com.zybo.asistente.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zybo.asistente.domain.entity.Vehiculo;
import com.zybo.asistente.service.VehiculoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @PostMapping
    public ResponseEntity<Vehiculo> registrar(@RequestParam String placa,
                                               @RequestParam Long usuarioId) {
        Vehiculo vehiculo = vehiculoService.registrarVehiculo(placa, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculo);
    }

    @GetMapping("/{placa}")
    public ResponseEntity<Vehiculo> buscarPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(
                vehiculoService.buscarPorPlaca(placa)
        );
    }
}
