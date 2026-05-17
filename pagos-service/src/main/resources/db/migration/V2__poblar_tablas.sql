INSERT INTO estado_pago
(nombre)
VALUES
('PENDIENTE'),
('APROBADO'),
('RECHAZADO'),
('REEMBOLSADO');

INSERT INTO pago
(orden_id, monto, metodo_pago, fecha_pago, estado_id)
VALUES
(1, 899960, 'TARJETA_CREDITO', NOW(), 2),
(2, 1259980, 'TRANSFERENCIA', NOW(), 1),
(3, 249970, 'TARJETA_DEBITO', NOW(), 2),
(4, 117970, 'MERCADO_PAGO', NOW(), 2),
(5, 48980, 'PAYPAL', NOW(), 4),
(6, 789970, 'TARJETA_CREDITO', NOW(), 2),
(7, 97980, 'TRANSFERENCIA', NOW(), 1),
(8, 127970, 'TARJETA_DEBITO', NOW(), 2),
(9, 409970, 'WEBPAY', NOW(), 2),
(10, 284980, 'PAYPAL', NOW(), 2);

INSERT INTO reembolso
(monto, motivo, pago_id)
VALUES
(48980, 'Producto devuelto por el cliente', 5);

INSERT INTO transaccion
(codigo_transaccion, respuesta_pasarela, pago_id)
VALUES
('TRX-1001', 'APROBADA', 1),
('TRX-1002', 'PENDIENTE', 2),
('TRX-1003', 'APROBADA', 3),
('TRX-1004', 'APROBADA', 4),
('TRX-1005', 'REEMBOLSADA', 5),
('TRX-1006', 'APROBADA', 6),
('TRX-1007', 'PENDIENTE', 7),
('TRX-1008', 'APROBADA', 8),
('TRX-1009', 'APROBADA', 9),
('TRX-1010', 'APROBADA', 10);