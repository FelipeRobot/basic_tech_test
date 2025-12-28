package com.zybo.asistente.repository;

import com.zybo.asistente.domain.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    Optional<Vehiculo> findByPlaca(String placa);

    boolean existsByPlaca(String placa);
}
