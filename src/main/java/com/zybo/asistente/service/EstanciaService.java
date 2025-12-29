package com.zybo.asistente.service;

import com.zybo.asistente.domain.entity.Estancia;
import com.zybo.asistente.domain.entity.EventoOutbox;
import com.zybo.asistente.domain.entity.Vehiculo;
import com.zybo.asistente.domain.enums.EstadoEstancia;
import com.zybo.asistente.domain.enums.EstadoEvento;
import com.zybo.asistente.domain.enums.TipoEvento;
import com.zybo.asistente.dto.EstanciaResponse;
import com.zybo.asistente.exception.BusinessException;
import com.zybo.asistente.exception.NotFoundException;
import com.zybo.asistente.repository.EstanciaRepository;
import com.zybo.asistente.repository.EventoOutboxRepository;
import com.zybo.asistente.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class EstanciaService {

    private final VehiculoRepository vehiculoRepository;
    private final EstanciaRepository estanciaRepository;
    private final EventoOutboxRepository eventoOutboxRepository;

    @Transactional
    public EstanciaResponse registrarIngreso(String placa) {

        Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() ->
                        new NotFoundException("Vehículo no encontrado con placa " + placa)
                );

        if (estanciaRepository.existsByVehiculoIdAndEstado(
                vehiculo.getId(), EstadoEstancia.ACTIVA)) {
            throw new BusinessException("El vehículo ya tiene una estancia activa");
        }

        Estancia estancia = Estancia.builder()
                .vehiculo(vehiculo)
                .horaIngreso(LocalDateTime.now())
                .estado(EstadoEstancia.ACTIVA)
                .build();

        estanciaRepository.save(estancia);

        registrarEvento(
                TipoEvento.INGRESO_VEHICULO,
                "{\"placa\":\"" + placa + "\"}"
        );

        return mapToResponse(estancia);
    }

    @Transactional
    public EstanciaResponse registrarSalida(String placa) {

        Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() ->
                        new NotFoundException("Vehículo no encontrado con placa " + placa)
                );

        Estancia estancia = estanciaRepository
                .findByVehiculoIdAndEstado(vehiculo.getId(), EstadoEstancia.ACTIVA)
                .orElseThrow(() ->
                        new BusinessException("No existe estancia activa para el vehículo")
                );

        LocalDateTime salida = LocalDateTime.now();

        long minutos = ChronoUnit.MINUTES.between(
                estancia.getHoraIngreso(), salida
        );

        long valor = minutos * 100;

        estancia.setHoraSalida(salida);
        estancia.setMinutos(minutos);
        estancia.setValorCobrado(valor);
        estancia.setEstado(EstadoEstancia.CERRADA);

        estanciaRepository.save(estancia);

        registrarEvento(
                TipoEvento.SALIDA_VEHICULO,
                "{\"placa\":\"" + placa + "\",\"valor\":" + valor + "}"
        );

        return mapToResponse(estancia);
    }

    @Transactional(readOnly = true)
    public EstanciaResponse obtenerEstanciaActiva(String placa) {

        Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() ->
                        new NotFoundException("Vehículo no encontrado con placa " + placa)
                );

        Estancia estancia = estanciaRepository
                .findByVehiculoIdAndEstado(vehiculo.getId(), EstadoEstancia.ACTIVA)
                .orElseThrow(() ->
                        new BusinessException("No hay estancia activa para el vehículo")
                );

        return mapToResponse(estancia);
    }

    // ---------- helpers ----------

    private void registrarEvento(TipoEvento tipo, String payload) {
        EventoOutbox evento = EventoOutbox.builder()
                .tipoEvento(tipo)
                .payload(payload)
                .estado(EstadoEvento.PENDIENTE)
                .creadoEn(LocalDateTime.now())
                .build();

        eventoOutboxRepository.save(evento);
    }

    private EstanciaResponse mapToResponse(Estancia estancia) {
        return EstanciaResponse.builder()
                .id(estancia.getId())
                .placa(estancia.getVehiculo().getPlaca())
                .horaIngreso(estancia.getHoraIngreso())
                .horaSalida(estancia.getHoraSalida())
                .minutos(estancia.getMinutos())
                .valorCobrado(estancia.getValorCobrado())
                .estado(estancia.getEstado())
                .build();
    }
}
