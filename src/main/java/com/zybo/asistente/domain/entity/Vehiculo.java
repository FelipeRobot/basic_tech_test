package com.zybo.asistente.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "vehiculos",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "placa")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String placa;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
