# HanmaCorporation - Arquitectura de Microservicios

## Contexto

HanmaCorporation es una plataforma de comercio electrónico diseñada bajo una arquitectura de microservicios robusta, escalable y altamente disponible. El proyecto resuelve la necesidad de segmentar las lógicas de negocio complejas (como gestión de inventario, logística, pagos y usuarios) en módulos independientes. Esto permite un despliegue ágil, un mantenimiento aislado por dominio y una orquestación centralizada utilizando el ecosistema de Spring Cloud y despliegue en contenedores con Docker.

---

## Créditos

Equipo de desarrollo:

- [Nombre Apellido 1]
- [Nombre Apellido 2]
- [Nombre Apellido 3]

---

## Arquitectura

El sistema está compuesto por los siguientes microservicios de infraestructura y de negocio:

### Infraestructura

- **config-server:** Servidor centralizado de configuración (Spring Cloud Config).
- **eureka-service:** Servidor de descubrimiento y registro de servicios (Netflix Eureka).
- **api-gateway:** Puerta de enlace unificada y enrutamiento dinámico (Spring Cloud Gateway).
- **mysql-db:** Base de datos relacional central, dividida lógicamente por esquemas (vía Docker).

### Microservicios de Negocio

- **identidad-service:** Gestión de usuarios, roles y autenticación.
- **marketplace-service:** Administración de tiendas, vendedores, productos y categorías.
- **inventario-service:** Control de bodegas, inventarios y movimientos de stock.
- **carrito-service:** Gestión de carritos de compra y sus ítems.
- **ordenes-service:** Procesamiento de órdenes de compra, detalles, estados e historial.
- **pagos-service:** Pasarela interna para pagos, transacciones, estados y reembolsos.
- **logistica-service:** Administración de regiones, comunas, direcciones de entrega y envíos.
- **resenas-service:** Sistema de retroalimentación, reseñas y reacciones.

---

## Networking

El **API Gateway** centraliza todas las peticiones a través del puerto `8090`. Las rutas principales expuestas son:

- `/usuarios/**`, `/roles/**` ➔ **identidad-service**
- `/vendedores/**`, `/productos/**`, `/tiendas/**`, `/categorias/**` ➔ **marketplace-service**
- `/bodegas/**`, `/inventarios/**`, `/movimientos/**` ➔ **inventario-service**
- `/carrito/**`, `/items/**` ➔ **carrito-service**
- `/ordenes/**`, `/historial/**`, `/estados/**`, `/detalle/**` ➔ **ordenes-service**
- `/pagos/**`, `/transacciones/**`, `/estadoPago/**`, `/reembolsos/**` ➔ **pagos-service**
- `/comunas/**`, `/direcciones/**`, `/envios/**`, `/regiones/**` ➔ **logistica-service**
- `/reacciones/**`, `/resenas/**` ➔ **resenas-service**

---

## Accesos

Una vez que el entorno esté levantado, puedes acceder a los siguientes paneles de control y documentación:

- **Documentación Swagger UI (Unificada):** [http://localhost:8090/swagger-ui.html](http://localhost:8090/swagger-ui.html)
- **Panel de Eureka (Discovery Server):** [http://localhost:8761](http://localhost:8761)
- **Verificación de Config Server:** [http://localhost:8888/api-gateway/default](http://localhost:8888/api-gateway/default)

---

## Guía de Despliegue

La plataforma está diseñada para ejecutarse tanto en un entorno completamente contenerizado como en un esquema híbrido o local, ideal para el desarrollo.

### Opción 1: Entorno Contenerizado (Full Docker)

Esta opción levanta toda la infraestructura, la base de datos y todos los microservicios en contenedores. Ideal para pruebas de integración o despliegue rápido.

1. Abre una terminal en el directorio raíz del proyecto.
2. Ejecuta el comando de construcción y despliegue:
   ```bash
   docker-compose up -d --build
   ```
3. Espera aproximadamente **60 segundos**. Los servicios tienen tiempos de espera programados para garantizar que la base de datos y el servidor de configuración arranquen correctamente antes que los servicios de negocio.
4. Verifica el estado de los contenedores:
   ```bash
   docker ps
   ```
5. Para detener todo el ecosistema:
   ```bash
   docker-compose down
   ```

### Opción 2: Entorno Local / Híbrido (Desarrollo en IDE)

Gracias a la configuración dinámica de variables, el proyecto permite trabajar en un esquema híbrido o de forma **completamente local** sin depender de contenedores. Los archivos `.yml` tienen como valor por defecto `localhost`, adaptándose automáticamente a tu entorno de ejecución.

**A. Modelo Completamente Local:** Ideal si no deseas usar Docker durante el desarrollo.

1. Asegúrate de tener tu propio gestor MySQL (ej. XAMPP o instalación nativa) corriendo localmente en el puerto `3306` con las bases de datos requeridas creadas.
2. Desde tu IDE (IntelliJ, Eclipse, VSCode), ejecuta primero los servicios de infraestructura estrictamente en este orden: `config-server`, luego `eureka-service` y por último `api-gateway`.
3. Dale _Play_ a los microservicios de negocio que necesites. Todo el ecosistema se comunicará nativamente a través de tu red local.

**B. Modelo Híbrido (Recomendado para programar ágilmente):** Delega la infraestructura pesada a Docker y mantén tu código de negocio en el IDE para debugear fácilmente.

1. Abre una terminal y levanta **solo la infraestructura base** en Docker:
   ```bash
   docker-compose up -d mysql-db config-server eureka-service api-gateway
   ```
2. Ve a tu IDE y ejecuta únicamente el microservicio que vas a desarrollar o modificar (ej. `IdentidadServiceApplication.java`).
3. Tu código local se conectará de forma transparente a la base de datos y al Eureka alojados en Docker.
4. Prueba tus cambios a través del Gateway local (`http://localhost:8090`).
