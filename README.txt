🚗 Asistente – Gestión de Parqueadero (Prueba Técnica)

API REST desarrollada en Java 21 + Spring Boot para la gestión de ingresos y salidas de vehículos en un parqueadero.

El sistema permite:

Registrar usuarios y vehículos

Registrar ingreso y salida de vehículos

Consultar estancias activas

Calcular tiempo y valor de la estancia

Manejar concurrencia de forma segura

Persistir eventos usando el patrón Outbox

🧱 Tecnologías utilizadas

Java 21

Spring Boot 3.2.x

Spring Data JPA

Hibernate

H2 Database (en memoria)

Lombok

Maven

JUnit 5

🗂️ Estructura del proyecto
src/main/java/com/zybo/asistente
├── controller   # Controladores REST
├── domain
│   ├── entity   # Entidades JPA
│   └── enums    # Enumeraciones de dominio
├── dto          # DTOs de request y response
├── exception    # Manejo centralizado de errores
├── repository   # Repositorios JPA
└── service      # Lógica de negocio

⚙️ Configuración

La aplicación usa H2 en memoria, no requiere configuración externa.

application.properties
spring.application.name=asistente

# H2
spring.datasource.url=jdbc:h2:mem:zybo-db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Consola H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

▶️ Ejecución del proyecto
./mvnw spring-boot:run


La aplicación inicia en:

http://localhost:8080

🧪 Consola H2
http://localhost:8080/h2-console


Parámetros:

JDBC URL: jdbc:h2:mem:zybo-db

User: sa

Password: (vacío)

📡 Endpoints disponibles
👤 Usuarios

Crear usuario

POST /api/usuarios


Body (JSON):

{
  "documento": "123456789",
  "nombres": "Juan Pérez",
  "telefono": "3001234567"
}


Consultar usuario

GET /api/usuarios/{id}

🚘 Vehículos

Registrar vehículo

POST /api/vehiculos?placa=ABC123&usuarioId=1


Buscar por placa

GET /api/vehiculos/{placa}

🅿️ Estancias

Ingreso de vehículo

POST /api/estancias/ingreso?placa=ABC123


Salida de vehículo

POST /api/estancias/salida?placa=ABC123


Consultar estancia activa

GET /api/estancias/activa/{placa}

📦 Respuesta de Estancia (DTO)
{
  "id": 1,
  "placa": "ABC123",
  "horaIngreso": "2025-12-26T12:30:00",
  "horaSalida": "2025-12-26T13:15:00",
  "minutos": 45,
  "valorCobrado": 4500,
  "estado": "CERRADA"
}

🧠 Decisiones de diseño

Uso de DTOs para evitar exponer entidades JPA

Lazy Loading controlado, sin errores de serialización

Optimistic Locking (@Version) para evitar doble ingreso/salida

Prueba automatizada de concurrencia validando el comportamiento bajo carga

Implementación del patrón Outbox para eventos de dominio

Transacciones declarativas con @Transactional

Arquitectura por capas clara y mantenible

🧪 Testing

Tests automáticos con JUnit 5

Prueba de concurrencia que valida que:

No se permiten dos ingresos concurrentes para el mismo vehículo

Se mantiene la consistencia del estado de la estancia

Contexto Spring cargado correctamente en pruebas

Para ejecutar pruebas:

./mvnw test

🚀 Posibles mejoras

Procesador asíncrono de eventos Outbox (Kafka / RabbitMQ)

Tests de integración con MockMvc

Persistencia en base de datos real (PostgreSQL / MySQL)

Autenticación y autorización (JWT / OAuth2)

CRUD completo con paginación

⏱️ Alcances y decisiones por tiempo

Debido a la restricción de tiempo de la prueba técnica, se priorizó:

Correcta modelación del dominio

Consistencia transaccional

Manejo explícito de concurrencia

Uso de patrones de arquitectura

Código limpio, legible y mantenible

Algunas mejoras quedan planificadas para una siguiente iteración, sin afectar el cumplimiento de los requisitos funcionales y técnicos del enunciado.