INSERT INTO bodegas
(nombre, direccion)
VALUES
('Bodega Central Santiago', 'Av. Libertador Bernardo OHiggins 2450, Santiago'),
('Bodega Norte', 'Ruta 5 Norte Km 120, Antofagasta'),
('Bodega Sur', 'Camino Industrial 450, Concepcion');

INSERT INTO inventarios
(producto_id, stock_disponible, stock_reservado, stock_minimo, bodega_id)
VALUES
(1, 15, 2, 5, 1),
(2, 40, 5, 10, 1),
(3, 10, 1, 3, 1),
(4, 5, 0, 2, 2),
(5, 18, 3, 5, 2),
(6, 25, 4, 8, 2),
(7, 30, 2, 10, 1),
(8, 22, 1, 6, 1),
(9, 50, 5, 15, 3),
(10, 16, 2, 5, 3),
(11, 8, 1, 2, 1),
(12, 35, 6, 10, 1),
(13, 12, 1, 4, 2),
(14, 28, 3, 8, 2),
(15, 20, 2, 5, 3),
(16, 14, 1, 4, 3),
(17, 17, 2, 5, 1),
(18, 45, 4, 12, 1),
(19, 32, 3, 10, 2),
(20, 9, 1, 3, 2),
(21, 7, 0, 2, 3),
(22, 40, 5, 10, 3),
(23, 60, 8, 20, 1),
(24, 13, 1, 4, 1),
(25, 26, 2, 8, 2),
(26, 21, 1, 6, 2),
(27, 19, 2, 5, 3),
(28, 6, 0, 2, 3),
(29, 24, 3, 7, 1),
(30, 11, 1, 3, 1);

INSERT INTO movimientos_stock
(tipo_movimiento, cantidad, fecha_movimiento, inventario_id)
VALUES
('ENTRADA', 15, NOW(), 1),
('RESERVA', 2, NOW(), 1),

('ENTRADA', 40, NOW(), 2),
('RESERVA', 5, NOW(), 2),

('ENTRADA', 10, NOW(), 3),
('RESERVA', 1, NOW(), 3),

('ENTRADA', 5, NOW(), 4),

('ENTRADA', 18, NOW(), 5),
('RESERVA', 3, NOW(), 5),

('ENTRADA', 25, NOW(), 6),
('RESERVA', 4, NOW(), 6),

('ENTRADA', 30, NOW(), 7),

('ENTRADA', 22, NOW(), 8),

('ENTRADA', 50, NOW(), 9),
('RESERVA', 5, NOW(), 9),

('ENTRADA', 16, NOW(), 10),

('ENTRADA', 8, NOW(), 11),

('ENTRADA', 35, NOW(), 12),
('RESERVA', 6, NOW(), 12),

('ENTRADA', 12, NOW(), 13),

('ENTRADA', 28, NOW(), 14),

('ENTRADA', 20, NOW(), 15),

('ENTRADA', 14, NOW(), 16),

('ENTRADA', 17, NOW(), 17),

('ENTRADA', 45, NOW(), 18),

('ENTRADA', 32, NOW(), 19),

('ENTRADA', 9, NOW(), 20),

('ENTRADA', 7, NOW(), 21),

('ENTRADA', 40, NOW(), 22),

('ENTRADA', 60, NOW(), 23),

('ENTRADA', 13, NOW(), 24),

('ENTRADA', 26, NOW(), 25),

('ENTRADA', 21, NOW(), 26),

('ENTRADA', 19, NOW(), 27),

('ENTRADA', 6, NOW(), 28),

('ENTRADA', 24, NOW(), 29),

('ENTRADA', 11, NOW(), 30);