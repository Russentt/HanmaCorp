# Marketplace Microservices Platform

Plataforma de marketplace desarrollada mediante arquitectura de microservicios utilizando Spring Boot y Spring Cloud.

El proyecto fue diseñado con fines académicos para implementar:

* comunicación distribuida,
* persistencia desacoplada,
* APIs REST,
* descubrimiento de servicios,
* gateway centralizado,
* integración entre microservicios.

---

# Arquitectura

El sistema se compone de los siguientes microservicios:

| Microservicio      | Responsabilidad                 |
| ------------------ | ------------------------------- |
| IdentidadService   | Usuarios y roles                |
| MarketplaceService | Tiendas, productos y categorías |
| InventarioService  | Gestión de stock                |
| CarritoService     | Carritos de compra              |
| OrdenesService     | Órdenes y estados               |
| PagosService       | Procesamiento de pagos          |
| LogisticaService   | Envíos y seguimiento            |
| ResenasService     | Reseñas y reputación            |

---

# Tecnologías

* Java 21
* Spring Boot
* Spring Cloud
* Spring Data JPA
* OpenFeign
* Eureka Server
* API Gateway
* Flyway
* MySQL
* Lombok
* Validation API

---

# Componentes de Infraestructura

## Eureka Server

Servicio de descubrimiento para registro dinámico de microservicios.

## API Gateway

Punto único de entrada para centralizar rutas y acceso a APIs.

---

# Comunicación Entre Servicios

La comunicación entre microservicios se realiza mediante OpenFeign.

## Ejemplos

* CarritoService → MarketplaceService, InventarioService
* OrdenesService → CarritoService, InventarioService, PagosService
* ResenasService → MarketplaceService, OrdenesService

---

# Modelo General

## IdentidadService

* Usuario
* Rol

## MarketplaceService

* Vendedor
* Tienda
* Producto
* Categoria

## InventarioService

* Bodega
* Inventario
* MovimientoStock

## CarritoService

* Carrito
* ItemCarrito

## OrdenesService

* Orden
* DetalleOrden
* EstadoOrden

## PagosService

* Pago
* Transaccion
* Reembolso

## LogisticaService

* Envio
* EventoSeguimiento
* DireccionEntrega

## ResenasService

* Resena
* CalificacionProducto
* CalificacionVendedor

---

# Endpoints Personalizados

Ejemplos de lógica de negocio implementada:

```http id="zjksq7"
GET /productos/precio
GET /productos/tienda/{id}
GET /productos/categoria/{id}
GET /productos/stock/{id}
GET /productos/producto-stock/{id}
GET /carrito/items/{id}
GET /inventarios/stock/{stockDisponible}
GET /pago/orden/{id}
```

---

# Endpoints con uso de OpenFeign

```http id="zjksq7"
GET /vendedores/usuario/{id}
GET /carrito/productos/{id}
GET /inventarios/producto-stock/{id}
PUT /inventarios/reservar/{id}
PUT /inventarios/liberar/{id}
GET /ordenes/items/{id}
GET /ordenes/pago/aprobado/{id}
```

# Persistencia

Cada microservicio posee:

* base de datos independiente,
* entidades propias,
* migraciones controladas con Flyway.

---

# Objetivo Académico

El proyecto busca demostrar:

* diseño de microservicios,
* modelado de dominio,
* persistencia desacoplada,
* comunicación distribuida,
* uso del ecosistema Spring Cloud.