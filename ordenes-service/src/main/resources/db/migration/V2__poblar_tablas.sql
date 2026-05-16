INSERT INTO estado_orden
(nombre)
VALUES
('PENDIENTE'),
('PAGADA'),
('ENVIADA'),
('ENTREGADA'),
('CANCELADA');

INSERT INTO ordenes
(usuario_id, total, fecha_creacion, estado_id)
VALUES
(1, 899960, NOW(), 2),
(2, 1259980, NOW(), 1),
(3, 249970, NOW(), 2),
(4, 117970, NOW(), 3),
(5, 48980, NOW(), 4),
(6, 789970, NOW(), 2),
(7, 97980, NOW(), 1),
(8, 127970, NOW(), 3),
(9, 409970, NOW(), 2),
(10, 284980, NOW(), 4);

INSERT INTO detalle_orden
(producto_id, cantidad, precio_unitario, orden_id)
VALUES
(1, 1, 649990, 1),
(2, 2, 24990, 1),
(21, 1, 199990, 1),

(3, 1, 459990, 2),
(4, 1, 799990, 2),

(5, 2, 89990, 3),
(6, 1, 69990, 3),

(7, 1, 45990, 4),
(8, 2, 35990, 4),

(9, 1, 15990, 5),
(10, 1, 32990, 5),

(11, 1, 649990, 6),
(12, 2, 69990, 6),

(13, 1, 54990, 7),
(14, 1, 42990, 7),

(15, 2, 38990, 8),
(16, 1, 49990, 8),

(17, 1, 289990, 9),
(18, 2, 59990, 9),

(19, 1, 34990, 10),
(20, 1, 249990, 10);

INSERT INTO historial
(fecha_cambio, orden_id, estado_anterior_id, estado_actual_id)
VALUES
(NOW(), 1, 1, 2),

(NOW(), 3, 1, 2),

(NOW(), 4, 1, 2),
(NOW(), 4, 2, 3),

(NOW(), 5, 1, 2),
(NOW(), 5, 2, 3),
(NOW(), 5, 3, 4),

(NOW(), 6, 1, 2),

(NOW(), 8, 1, 2),
(NOW(), 8, 2, 3),

(NOW(), 9, 1, 2),

(NOW(), 10, 1, 2),
(NOW(), 10, 2, 3),
(NOW(), 10, 3, 4);