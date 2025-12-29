package com.zybo.asistente.service;

import com.zybo.asistente.domain.entity.Usuario;
import com.zybo.asistente.domain.entity.Vehiculo;
import com.zybo.asistente.dto.VehiculoResponse;
import com.zybo.asistente.exception.NotFoundException;
import com.zybo.asistente.repository.UsuarioRepository;
import com.zybo.asistente.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public VehiculoResponse registrarVehiculo(String placa, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new NotFoundException("Usuario no encontrado con id " + usuarioId)
                );

        Vehiculo vehiculo = Vehiculo.builder()
                .placa(placa)
                .usuario(usuario)
                .build();

        Vehiculo guardado = vehiculoRepository.save(vehiculo);

        return mapToResponse(guardado);
    }

    @Transactional(readOnly = true)
    public VehiculoResponse buscarPorPlaca(String placa) {

        Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() ->
                        new NotFoundException("Vehículo no encontrado con placa " + placa)
                );

        return mapToResponse(vehiculo);
    }

    private VehiculoResponse mapToResponse(Vehiculo vehiculo) {
        return VehiculoResponse.builder()
                .id(vehiculo.getId())
                .placa(vehiculo.getPlaca())
                .usuarioId(vehiculo.getUsuario().getId())
                .build();
    }
}
