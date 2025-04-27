-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.4.3 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para agrotech
CREATE DATABASE IF NOT EXISTS `agrotech` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `agrotech`;

-- Volcando estructura para tabla agrotech.cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `rut` int NOT NULL,
  `ncliente` int NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `appaterno` varchar(20) NOT NULL,
  `apmaterno` varchar(20) DEFAULT NULL,
  `correo` varchar(70) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `direccion` varchar(60) NOT NULL,
  PRIMARY KEY (`rut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla agrotech.detalle_pedido
CREATE TABLE IF NOT EXISTS `detalle_pedido` (
  `id` int NOT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `estado` varchar(15) NOT NULL,
  `PEDIDO_id` int NOT NULL,
  `PEDIDO_rut` varchar(9) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `DETALLE_PEDIDO__IDX` (`PEDIDO_id`,`PEDIDO_rut`),
  CONSTRAINT `DETALLE_PEDIDO_PEDIDO_FK` FOREIGN KEY (`PEDIDO_id`, `PEDIDO_rut`) REFERENCES `pedido` (`id`, `EMPLEADO_rut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla agrotech.empleado
CREATE TABLE IF NOT EXISTS `empleado` (
  `rut` varchar(9) NOT NULL,
  `nempleado` int NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `appaterno` varchar(20) NOT NULL,
  `apmaterno` varchar(20) DEFAULT NULL,
  `telefono` varchar(15) NOT NULL,
  `direccion` varchar(60) NOT NULL,
  PRIMARY KEY (`rut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla agrotech.equipo
CREATE TABLE IF NOT EXISTS `equipo` (
  `id` int NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `estado` varchar(10) NOT NULL,
  `precio_venta` int DEFAULT NULL,
  `precio_arriendo` int DEFAULT NULL,
  `PEDIDO_id` int DEFAULT NULL,
  `PEDIDO_EMPLEADO_rut` varchar(9) DEFAULT NULL,
  `PROVEEDOR_rut_empresa` varchar(20) NOT NULL,
  PRIMARY KEY (`id`,`PROVEEDOR_rut_empresa`),
  KEY `EQUIPO_PEDIDO_FK` (`PEDIDO_id`,`PEDIDO_EMPLEADO_rut`),
  KEY `EQUIPO_PROVEEDOR_FK` (`PROVEEDOR_rut_empresa`),
  CONSTRAINT `EQUIPO_PEDIDO_FK` FOREIGN KEY (`PEDIDO_id`, `PEDIDO_EMPLEADO_rut`) REFERENCES `pedido` (`id`, `EMPLEADO_rut`),
  CONSTRAINT `EQUIPO_PROVEEDOR_FK` FOREIGN KEY (`PROVEEDOR_rut_empresa`) REFERENCES `proveedor` (`rut_empresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla agrotech.incidencia
CREATE TABLE IF NOT EXISTS `incidencia` (
  `id` int NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `fecha_reporte` date NOT NULL,
  `EQUIPO_id` int NOT NULL,
  `EQUIPO_rut_empresa` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `INCIDENCIA__IDX` (`EQUIPO_id`,`EQUIPO_rut_empresa`),
  CONSTRAINT `INCIDENCIA_EQUIPO_FK` FOREIGN KEY (`EQUIPO_id`, `EQUIPO_rut_empresa`) REFERENCES `equipo` (`id`, `PROVEEDOR_rut_empresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla agrotech.mantenimiento
CREATE TABLE IF NOT EXISTS `mantenimiento` (
  `id` int NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_termino` date DEFAULT NULL,
  `estado` varchar(15) NOT NULL,
  `TIPO_MANTENIMIENTO_id` int NOT NULL,
  `INCIDENCIA_id` int NOT NULL,
  PRIMARY KEY (`id`,`TIPO_MANTENIMIENTO_id`),
  UNIQUE KEY `MANTENIMIENTO__IDX` (`INCIDENCIA_id`),
  KEY `TIPO_MANTENIMIENTO_FK` (`TIPO_MANTENIMIENTO_id`),
  CONSTRAINT `MANTENIMIENTO_INCIDENCIA_FK` FOREIGN KEY (`INCIDENCIA_id`) REFERENCES `incidencia` (`id`),
  CONSTRAINT `TIPO_MANTENIMIENTO_FK` FOREIGN KEY (`TIPO_MANTENIMIENTO_id`) REFERENCES `tipo_mantenimiento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla agrotech.pedido
CREATE TABLE IF NOT EXISTS `pedido` (
  `id` int NOT NULL,
  `fecha_solicitud` date NOT NULL,
  `DETALLE_PEDIDO_id` int NOT NULL,
  `CLIENTE_rut` int NOT NULL,
  `EMPLEADO_rut` varchar(9) NOT NULL,
  PRIMARY KEY (`id`,`EMPLEADO_rut`),
  UNIQUE KEY `PEDIDO__IDX` (`DETALLE_PEDIDO_id`),
  KEY `PEDIDO_CLIENTE_FK` (`CLIENTE_rut`),
  KEY `PEDIDO_EMPLEADO_FK` (`EMPLEADO_rut`),
  CONSTRAINT `PEDIDO_CLIENTE_FK` FOREIGN KEY (`CLIENTE_rut`) REFERENCES `cliente` (`rut`),
  CONSTRAINT `PEDIDO_DETALLE_PEDIDO_FK` FOREIGN KEY (`DETALLE_PEDIDO_id`) REFERENCES `detalle_pedido` (`id`),
  CONSTRAINT `PEDIDO_EMPLEADO_FK` FOREIGN KEY (`EMPLEADO_rut`) REFERENCES `empleado` (`rut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla agrotech.proveedor
CREATE TABLE IF NOT EXISTS `proveedor` (
  `rut_empresa` varchar(20) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `correo` varchar(80) NOT NULL,
  PRIMARY KEY (`rut_empresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla agrotech.resena
CREATE TABLE IF NOT EXISTS `resena` (
  `id` int NOT NULL,
  `puntuacion` int NOT NULL,
  `comentario` varchar(150) DEFAULT NULL,
  `PEDIDO_id` int NOT NULL,
  `PEDIDO_rut` varchar(9) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `RESENA__IDX` (`PEDIDO_id`,`PEDIDO_rut`),
  CONSTRAINT `RESENA_PEDIDO_FK` FOREIGN KEY (`PEDIDO_id`, `PEDIDO_rut`) REFERENCES `pedido` (`id`, `EMPLEADO_rut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla agrotech.tipo_mantenimiento
CREATE TABLE IF NOT EXISTS `tipo_mantenimiento` (
  `id` int NOT NULL,
  `descripcion` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
