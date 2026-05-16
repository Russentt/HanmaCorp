INSERT INTO tiendas
(nombre, descripcion, reputacion, activa)
VALUES
('TechZone', 'Tienda especializada en tecnologia y gadgets', 4.8, true),
('HomeStyle', 'Productos modernos para el hogar', 4.5, true),
('FitLife', 'Accesorios y ropa deportiva', 4.6, true),
('UrbanWear', 'Moda urbana y juvenil', 4.4, true),
('BookUniverse', 'Libros y colecciones literarias', 4.9, true),
('GameWorld', 'Videojuegos y accesorios gamer', 4.7, true),
('HealthPlus', 'Productos de salud y bienestar', 4.3, true),
('PetCenter', 'Todo para mascotas', 4.5, true),
('DigitalStore', 'Electronica y accesorios digitales', 4.6, true),
('SmartLiving', 'Innovacion y hogar inteligente', 4.7, true),
('SportFactory', 'Equipamiento deportivo profesional', 4.5, true),
('ClassicBooks', 'Libros clasicos y academicos', 4.8, true),
('FashionPoint', 'Ropa y tendencias actuales', 4.2, true),
('NextGenGaming', 'Gaming y perifericos avanzados', 4.9, true),
('AnimalHouse', 'Accesorios premium para mascotas', 4.4, true);

INSERT INTO categorias
(nombre, descripcion)
VALUES
('Tecnologia', 'Productos electronicos y dispositivos inteligentes'),
('Hogar', 'Articulos y accesorios para el hogar'),
('Deportes', 'Productos relacionados con actividad fisica y deporte'),
('Moda', 'Vestimenta y accesorios de moda'),
('Libros', 'Libros fisicos y material de lectura'),
('Videojuegos', 'Consolas, videojuegos y accesorios gamer'),
('Salud', 'Productos de cuidado personal y bienestar'),
('Mascotas', 'Accesorios y productos para mascotas');

INSERT INTO productos
(nombre, descripcion, precio, stock, tienda_id, categoria_id)
VALUES
('Laptop Lenovo IdeaPad', 'Notebook Ryzen 7 con 16GB RAM', 649990, 15, 1, 1),
('Mouse Gamer RGB', 'Mouse mecanico con iluminacion RGB', 24990, 40, 1, 1),

('Smart TV 55 Pulgadas', 'Televisor Ultra HD 4K', 459990, 10, 2, 2),
('Sofa Modular', 'Sofa moderno de 5 cuerpos', 799990, 5, 2, 2),

('Mancuernas Ajustables', 'Set ajustable hasta 20kg', 89990, 18, 3, 3),
('Zapatillas Running', 'Calzado deportivo profesional', 69990, 25, 3, 3),

('Chaqueta Oversize', 'Chaqueta urbana unisex', 45990, 30, 4, 4),
('Jeans Slim Fit', 'Jeans elasticados modernos', 35990, 22, 4, 4),

('El Principito', 'Edicion ilustrada de coleccion', 15990, 50, 5, 5),
('Clean Code', 'Libro de desarrollo de software', 32990, 16, 5, 5),

('PlayStation 5', 'Consola Sony nueva generacion', 649990, 8, 6, 6),
('Control Inalambrico', 'Joystick compatible PS5', 69990, 35, 6, 6),

('Monitor Cardiaco', 'Dispositivo inteligente de salud', 54990, 12, 7, 7),
('Proteina Whey', 'Suplemento alimenticio premium', 42990, 28, 7, 7),

('Cama para Perro', 'Cama acolchada tamaño grande', 38990, 20, 8, 8),
('Rascador para Gatos', 'Rascador con multiples niveles', 49990, 14, 8, 8),

('Tablet Samsung', 'Tablet Android de 10 pulgadas', 289990, 17, 9, 1),
('Audifonos Bluetooth', 'Audifonos inalambricos premium', 59990, 45, 9, 1),

('Lampara Inteligente', 'Control por voz y aplicacion', 34990, 32, 10, 2),
('Aspiradora Robot', 'Limpieza automatizada inteligente', 249990, 9, 10, 2),

('Bicicleta Estatica', 'Bicicleta indoor profesional', 199990, 7, 11, 3),
('Guantes Deportivos', 'Guantes antideslizantes gym', 14990, 40, 11, 3),

('1984', 'Novela clasica de George Orwell', 12990, 60, 12, 5),
('Arquitectura Limpia', 'Libro de Robert C. Martin', 36990, 13, 12, 5),

('Poleron Hoodie', 'Poleron urbano con capucha', 29990, 26, 13, 4),
('Mochila Urbana', 'Mochila impermeable moderna', 25990, 21, 13, 4),

('Teclado Mecanico', 'Teclado gamer switches blue', 79990, 19, 14, 6),
('Silla Gamer', 'Silla ergonomica reclinable', 189990, 6, 14, 6),

('Collar Inteligente', 'GPS para mascotas', 55990, 24, 15, 8),
('Dispensador Automatico', 'Dispensador inteligente de comida', 74990, 11, 15, 8);

INSERT INTO vendedores
(usuario_id, fecha_registro, tienda_id)
VALUES
(36, NOW(), 1),
(37, NOW(), 2),
(38, NOW(), 3),
(39, NOW(), 4),
(40, NOW(), 5),
(41, NOW(), 6),
(42, NOW(), 7),
(43, NOW(), 8),
(44, NOW(), 9),
(45, NOW(), 10),
(46, NOW(), 11),
(47, NOW(), 12),
(48, NOW(), 13),
(49, NOW(), 14),
(50, NOW(), 15);