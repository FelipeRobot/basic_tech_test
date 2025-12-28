package com.zybo.asistente.domain.entity;

import com.zybo.asistente.domain.enums.EstadoEstancia;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "estancias",
    indexes = {
        @Index(name = "idx_estancia_vehiculo_estado", columnList = "vehiculo_id, estado")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estancia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @Column(nullable = false)
    private LocalDateTime horaIngreso;

    private LocalDateTime horaSalida;

    private Long minutos;

    private Long valorCobrado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEstancia estado;

    /**
     * 🔒 Optimistic Locking
     * Evita doble ingreso / doble cierre bajo concurrencia
     */
    @Version
    private Long version;
}
