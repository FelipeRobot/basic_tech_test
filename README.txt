# 🚗 Asistente – Gestión de Parqueadero (Prueba Técnica)

API REST desarrollada en **Java 21 + Spring Boot** para la gestión de ingresos y salidas de vehículos en un parqueadero.

El sistema permite:
- Registrar usuarios y vehículos
- Registrar ingreso y salida de vehículos
- Consultar estancias activas
- Calcular tiempo y valor de la estancia
- Persistir eventos usando el patrón **Outbox**

---

## 🧱 Tecnologías utilizadas

- Java 21
- Spring Boot 3.2.x
- Spring Data JPA
- H2 Database (en memoria)
- Hibernate
- Lombok
- Maven

---

## 🗂️ Estructura del proyecto

src/main/java/com/zybo/asistente
├── controller # Controladores REST
├── domain
│ ├── entity # Entidades JPA
│ └── enums # Enumeraciones de dominio
├── dto # DTOs de salida
├── exception # Manejo de errores (extensible)
├── repository # Repositorios JPA
└── service # Lógica de negocio


---

## ⚙️ Configuración

La aplicación usa **H2 en memoria**, no requiere configuración externa.

### `application.properties`

```properties
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
Body (JSON):
POST /api/usuarios

{
  "documento": "123456789",
  "nombres": "Juan Pérez",
  "telefono": "3001234567"
}

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



--------------------------------------------------


🧠 Decisiones de diseño

DTOs para evitar exposición de entidades JPA

Lazy Loading controlado (sin errores de serialización)

Optimistic Locking en estancias para evitar concurrencia

Patrón Outbox para registrar eventos de dominio

Transacciones declarativas con @Transactional

Arquitectura por capas clara y mantenible



🚀 Posibles mejoras

Manejo global de errores con @ControllerAdvice

Tests de integración con MockMvc

Persistencia en base de datos real (PostgreSQL/MySQL)

Procesador asíncrono de eventos Outbox

Autenticación y autorización



⏱️ Alcances y decisiones por tiempo

Debido a la restricción de tiempo de la prueba técnica, el proyecto prioriza la correcta modelación del dominio, la consistencia transaccional y el manejo de concurrencia, dejando algunos aspectos planificados pero no implementados completamente. A continuación se detallan:

🚧 Funcionalidades parcialmente implementadas

CRUD completo de Usuarios y Vehículos

Se implementaron los endpoints principales (POST, GET)

PUT y DELETE quedaron fuera por priorización de lógica de negocio crítica (estancias y concurrencia)

Endpoint de dispatch de eventos (Outbox)

El patrón Outbox está implementado a nivel de persistencia

Falta el endpoint /eventos/dispatch para marcar eventos como ENVIADO

Validaciones con @Valid

No se implementaron DTOs de request con anotaciones de validación (@NotNull, @NotBlank, etc.)

El diseño está preparado para incorporarlos fácilmente

Manejo centralizado de errores

No se incluyó un @ControllerAdvice global

Las excepciones se manejan actualmente mediante RuntimeException para simplicidad

🧪 Testing

No se incluyó una prueba automatizada de concurrencia

Sin embargo, el sistema está diseñado con:

Transacciones

Bloqueo optimista (@Version)

Validaciones de estado

Esto permite soportar correctamente escenarios concurrentes de ingreso/salida

🔁 Uso de DTOs

Se creó el DTO EstanciaResponse para evitar exponer entidades JPA

Por limitaciones de tiempo, algunos endpoints aún retornan entidades directamente

El mapeo a DTO ya está implementado en el service y listo para ser aplicado en los controllers

🛢️ Base de datos

Se utilizó H2 en memoria para facilitar la ejecución y pruebas locales

El diseño es totalmente compatible con MySQL, cumpliendo el requisito del enunciado

🧠 Decisiones de diseño priorizadas

Durante la prueba se priorizó:

Correcta modelación del dominio

Consistencia de datos

Manejo de concurrencia

Patrones de arquitectura (Service Layer, Repository, Outbox)

Código claro y mantenible