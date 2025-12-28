package com.zybo.asistente.service;

import com.zybo.asistente.domain.entity.Usuario;
import com.zybo.asistente.domain.entity.Vehiculo;
import com.zybo.asistente.repository.UsuarioRepository;
import com.zybo.asistente.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;

    public Vehiculo registrarVehiculo(String placa, Long usuarioId) {

        if (vehiculoRepository.existsByPlaca(placa)) {
            throw new RuntimeException("Ya existe un vehículo con esa placa");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Vehiculo vehiculo = Vehiculo.builder()
                .placa(placa)
                .usuario(usuario)
                .build();

        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo buscarPorPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
    }
}
