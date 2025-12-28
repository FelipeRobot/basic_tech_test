package com.zybo.asistente.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zybo.asistente.domain.entity.EventoOutbox;
import com.zybo.asistente.domain.enums.EstadoEvento;

public interface EventoOutboxRepository extends JpaRepository<EventoOutbox, Long> {

    List<EventoOutbox> findTop10ByEstadoOrderByCreadoEnAsc(EstadoEvento estado);
}
