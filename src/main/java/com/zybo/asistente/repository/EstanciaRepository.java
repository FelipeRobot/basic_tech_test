package com.zybo.asistente.repository;

import com.zybo.asistente.domain.entity.Estancia;
import com.zybo.asistente.domain.enums.EstadoEstancia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstanciaRepository extends JpaRepository<Estancia, Long> {

    Optional<Estancia> findByVehiculoIdAndEstado(Long vehiculoId, EstadoEstancia estado);

    boolean existsByVehiculoIdAndEstado(Long vehiculoId, EstadoEstancia estado);

    long countByVehiculoIdAndEstado(Long vehiculoId, EstadoEstancia estado);
}
