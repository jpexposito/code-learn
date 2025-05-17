DROP DATABASE IF EXISTS ventas;
CREATE DATABASE ventas;
USE ventas;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

DROP TABLE IF EXISTS clientes;
CREATE TABLE clientes (
  id int NOT NULL,
  nombre varchar(100) DEFAULT NULL,
  correo varchar(100) DEFAULT NULL,
  ciudad varchar(50) DEFAULT NULL
);

INSERT INTO clientes (id, nombre, correo, ciudad) VALUES
(1, 'Ana Pérez', 'ana@example.com', 'Madrid'),
(2, 'Luis Gómez', 'luis@example.com', 'Barcelona'),
(3, 'Carla Ruiz', 'carla@example.com', 'Valencia'),
(4, 'Pedro Sánchez', 'pedro@example.com', 'Sevilla'),
(5, 'Lucía Martínez', 'lucia@example.com', 'Bilbao'),
(6, 'Jorge Torres', 'jorge@example.com', 'Madrid');

DROP TABLE IF EXISTS productos;
CREATE TABLE productos (
  id int NOT NULL,
  nombre varchar(100) DEFAULT NULL,
  precio decimal(10,2) DEFAULT NULL,
  categoria varchar(50) DEFAULT NULL
);

INSERT INTO productos (id, nombre, precio, categoria) VALUES
(1, 'Laptop', 1200.00, 'Electrónica'),
(2, 'Teléfono', 800.00, 'Electrónica'),
(3, 'Tablet', 500.00, 'Electrónica'),
(4, 'Auriculares', 150.00, 'Accesorios'),
(5, 'Monitor', 300.00, 'Periféricos'),
(6, 'Teclado', 50.00, 'Periféricos');

DROP TABLE IF EXISTS ventas;
CREATE TABLE ventas (
  id int NOT NULL,
  cliente_id int DEFAULT NULL,
  producto_id int DEFAULT NULL,
  fecha date DEFAULT NULL,
  cantidad int DEFAULT NULL
);

INSERT INTO ventas (id, cliente_id, producto_id, fecha, cantidad) VALUES
(1, 1, 1, '2024-05-01', 1),
(2, 2, 2, '2024-05-03', 2),
(3, 3, 3, '2024-05-05', 1),
(4, 4, 4, '2024-05-07', 3),
(5, 5, 5, '2024-05-10', 1),
(6, 6, 1, '2024-05-11', 1),
(7, 1, 6, '2024-05-12', 2),
(8, 2, 3, '2024-05-13', 1),
(9, 3, 2, '2024-05-14', 1),
(10, 4, 5, '2024-05-15', 2),
(11, 1, 2, '2024-05-16', 1);

COMMIT;
