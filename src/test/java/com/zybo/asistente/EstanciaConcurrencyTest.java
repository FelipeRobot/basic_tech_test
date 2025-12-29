package com.zybo.asistente;

import com.zybo.asistente.domain.entity.Usuario;
import com.zybo.asistente.domain.entity.Vehiculo;
import com.zybo.asistente.domain.enums.EstadoEstancia;
import com.zybo.asistente.repository.EstanciaRepository;
import com.zybo.asistente.repository.UsuarioRepository;
import com.zybo.asistente.repository.VehiculoRepository;
import com.zybo.asistente.service.EstanciaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EstanciaConcurrencyTest {

    @Autowired
    private EstanciaService estanciaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private EstanciaRepository estanciaRepository;

    @Test
    void noDebePermitirDosIngresosConcurrentesParaElMismoVehiculo() throws InterruptedException {

        // -------- Arrange --------
        Usuario usuario = Usuario.builder()
                .nombres("Juan Perez")
                .documento("123456789")
                .telefono("3001234567")
                .build();

        usuario = usuarioRepository.save(usuario);

        Vehiculo vehiculo = Vehiculo.builder()
                .placa("ABC123")
                .usuario(usuario)
                .build();

        vehiculoRepository.save(vehiculo);

        int hilos = 2;
        ExecutorService executor = Executors.newFixedThreadPool(hilos);
        CountDownLatch latch = new CountDownLatch(hilos);

        // -------- Act --------
        for (int i = 0; i < hilos; i++) {
            executor.execute(() -> {
                try {
                    estanciaService.registrarIngreso("ABC123");
                } catch (Exception e) {
                    // esperado: uno de los hilos debe fallar
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // -------- Assert --------
        long estanciasActivas = estanciaRepository
                .countByVehiculoIdAndEstado(
                        vehiculo.getId(),
                        EstadoEstancia.ACTIVA
                );

        assertThat(estanciasActivas).isEqualTo(1);
    }
}
