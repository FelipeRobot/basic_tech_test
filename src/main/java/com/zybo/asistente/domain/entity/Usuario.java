package com.zybo.asistente.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "usuarios",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "documento"),
        @UniqueConstraint(columnNames = "telefono")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String documento;

    @Column(nullable = false)
    private String telefono;
}
