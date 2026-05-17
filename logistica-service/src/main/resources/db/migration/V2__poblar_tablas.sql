INSERT INTO regiones
(nombre)
VALUES
('Region Metropolitana'),
('Valparaiso'),
('Biobio'),
('Antofagasta'),
('La Araucania');

INSERT INTO comunas
(nombre, region_id)
VALUES
('Puente Alto', 1),
('Santiago', 1),
('Maipu', 1),
('Providencia', 1),

('Valparaiso', 2),
('Vina del Mar', 2),

('Concepcion', 3),
('San Pedro de la Paz', 3),

('Antofagasta', 4),
('Calama', 4),

('Temuco', 5),
('Villarrica', 5);

INSERT INTO direccion_entrega
(usuario_id, calle, numero, departamento, referencia, codigo_postal, comuna_id)
VALUES
(1, 'Av. Concha y Toro', '1450', 'Depto 302', 'Frente al supermercado', '8200001', 1),

(2, 'Alameda Libertador Bernardo OHiggins', '2345', NULL, 'Edificio azul', '8320000', 2),

(3, 'Pajaritos', '980', 'Casa 12', 'Porton negro', '9250000', 3),

(4, 'Nueva Providencia', '2211', 'Oficina 504', 'Cerca del metro', '7500000', 4),

(5, 'Errázuriz', '450', NULL, 'Frente al puerto', '2340000', 5),

(6, 'Libertad', '1780', 'Depto 15', 'Torre norte', '2520000', 6),

(7, 'Los Carrera', '320', NULL, 'Casa esquina', '4030000', 7),

(8, 'Pedro Aguirre Cerda', '890', 'Depto 44', 'Condominio los pinos', '4130000', 8),

(9, 'Av. Brasil', '1200', NULL, 'Frente a farmacia', '1240000', 9),

(10, 'Granaderos', '560', 'Casa B', 'Cerca del mall', '1390000', 10);

INSERT INTO envios
(orden_id, direccion_entrega_id, fecha_envio, fecha_entrega_estimada, fecha_entrega_real)
VALUES
(1, 1, CURDATE(), DATE_ADD(NOW(), INTERVAL 3 DAY), DATE_ADD(NOW(), INTERVAL 2 DAY)),

(2, 2, CURDATE(), DATE_ADD(NOW(), INTERVAL 5 DAY), DATE_ADD(NOW(), INTERVAL 5 DAY)),

(3, 3, CURDATE(), DATE_ADD(NOW(), INTERVAL 4 DAY), DATE_ADD(NOW(), INTERVAL 3 DAY)),

(4, 4, CURDATE(), DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 2 DAY)),

(5, 5, CURDATE(), DATE_ADD(NOW(), INTERVAL 6 DAY), DATE_ADD(NOW(), INTERVAL 5 DAY)),

(6, 6, CURDATE(), DATE_ADD(NOW(), INTERVAL 3 DAY), DATE_ADD(NOW(), INTERVAL 3 DAY)),

(7, 7, CURDATE(), DATE_ADD(NOW(), INTERVAL 7 DAY), DATE_ADD(NOW(), INTERVAL 6 DAY)),

(8, 8, CURDATE(), DATE_ADD(NOW(), INTERVAL 4 DAY), DATE_ADD(NOW(), INTERVAL 4 DAY)),

(9, 9, CURDATE(), DATE_ADD(NOW(), INTERVAL 5 DAY), DATE_ADD(NOW(), INTERVAL 4 DAY)),

(10, 10, CURDATE(), DATE_ADD(NOW(), INTERVAL 3 DAY), DATE_ADD(NOW(), INTERVAL 2 DAY));