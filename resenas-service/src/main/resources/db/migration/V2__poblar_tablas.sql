INSERT INTO resenas
(usuario_id, producto_id, orden_id, titulo, comentario, puntuacion, fecha_creacion, visible)
VALUES
(1, 1, 1, 'Excelente notebook', 'Muy buen rendimiento para desarrollo y multitarea.', 5, NOW(), true),

(1, 2, 1, 'Buen accesorio', 'El mouse responde bastante bien y tiene buena ergonomia.', 4, NOW(), true),

(2, 3, 2, 'Muy rapido', 'El smartphone tiene excelente fluidez y buena bateria.', 5, NOW(), true),

(2, 4, 2, 'Pantalla impecable', 'Muy buena calidad de imagen para gaming.', 5, NOW(), true),

(3, 5, 3, 'Buen teclado', 'Comodo para escribir durante muchas horas.', 4, NOW(), true),

(3, 6, 3, 'Audio aceptable', 'Buen sonido aunque el volumen podria ser mejor.', 3, NOW(), true),

(4, 7, 4, 'Muy util', 'La webcam tiene buena calidad para reuniones.', 4, NOW(), true),

(4, 8, 4, 'Correcto', 'Cumple lo esperado por el precio.', 4, NOW(), true),

(5, 9, 5, 'Buen producto', 'Llego rapido y funciona correctamente.', 4, NOW(), true),

(5, 10, 5, 'Muy satisfecho', 'Excelente calidad de construccion.', 5, NOW(), true),

(6, 11, 6, 'Gran notebook', 'Ideal para programacion y virtualizacion.', 5, NOW(), true),

(6, 12, 6, 'Buen complemento', 'Muy util para escritorio.', 4, NOW(), true),

(7, 13, 7, 'Aceptable', 'El producto funciona bien aunque esperaba mas.', 3, NOW(), true),

(7, 14, 7, 'Buena compra', 'Relacion precio-calidad bastante correcta.', 4, NOW(), true),

(8, 15, 8, 'Muy recomendado', 'Excelente rendimiento en uso diario.', 5, NOW(), true),

(8, 16, 8, 'Buen diseño', 'Compacto y facil de usar.', 4, NOW(), true),

(9, 17, 9, 'Excelente monitor', 'Muy buena calidad para trabajar y jugar.', 5, NOW(), true),

(9, 18, 9, 'Buen accesorio', 'Cumple perfectamente su funcion.', 4, NOW(), true),

(10, 19, 10, 'Correcto', 'Buen producto aunque podria mejorar el material.', 3, NOW(), true),

(10, 20, 10, 'Muy satisfecho', 'Excelente experiencia de uso.', 5, NOW(), true);

INSERT INTO reacciones
(usuario_id, tipo, fecha_reaccion, resena_id)
VALUES
(2, 'UTIL', NOW(), 1),
(3, 'UTIL', NOW(), 1),

(1, 'UTIL', NOW(), 3),

(4, 'UTIL', NOW(), 4),
(5, 'UTIL', NOW(), 4),

(6, 'UTIL', NOW(), 6),

(7, 'UTIL', NOW(), 8),

(8, 'UTIL', NOW(), 10),

(9, 'UTIL', NOW(), 11),

(10, 'UTIL', NOW(), 15),

(1, 'UTIL', NOW(), 17),

(2, 'NO_UTIL', NOW(), 19);