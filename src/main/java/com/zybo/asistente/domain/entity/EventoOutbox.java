package com.zybo.asistente.domain.entity;

import com.zybo.asistente.domain.enums.EstadoEvento;
import com.zybo.asistente.domain.enums.TipoEvento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventos_outbox")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEvento tipoEvento;

    @Column(nullable = false, columnDefinition = "CLOB")
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEvento estado;

    @Column(nullable = false)
    private LocalDateTime creadoEn;
}
