DROP DATABASE IF EXISTS banco;
CREATE DATABASE IF NOT EXISTS banco;
-- SHOW DATABASES;
USE banco;

DROP TABLE IF EXISTS `personas`;
DROP TABLE IF EXISTS `clientes`;
DROP TABLE IF EXISTS `cuentas`;
DROP TABLE IF EXISTS `movimientos`;
DROP TABLE IF EXISTS `reportes`;

CREATE TABLE IF NOT EXISTS `personas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `direccion` varchar(200) DEFAULT NULL,
  `edad` tinyint NOT NULL,
  `genero` char(1) NOT NULL,
  `identificacion` varchar(12) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `clientes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contrasena` varchar(255) NOT NULL,
  `estado` varchar(5) NOT NULL,
  `persona_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqqdwv2x70kik01nxcgkxvh8ue` (`persona_id`),
  CONSTRAINT `FKqqdwv2x70kik01nxcgkxvh8ue` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `cuentas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` varchar(5) NOT NULL,
  `numero_cuenta` int NOT NULL,
  `saldo_inicial` double NOT NULL,
  `tipo` varchar(9) NOT NULL,
  `cliente_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK65yk2321jpusl3fk96lqehrli` (`cliente_id`),
  CONSTRAINT `FK65yk2321jpusl3fk96lqehrli` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `movimientos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `saldo` double DEFAULT NULL,
  `tipo_movimiento` varchar(90) DEFAULT NULL,
  `valor` double NOT NULL,
  `cuenta_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4moe88hxuohcysas5h70mdc09` (`cuenta_id`),
  CONSTRAINT `FK4moe88hxuohcysas5h70mdc09` FOREIGN KEY (`cuenta_id`) REFERENCES `cuentas` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `reportes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cliente_id` bigint DEFAULT NULL,
  `fecha` varchar(255) DEFAULT NULL,
  `movimiento_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjs9qpup322mn2xkd4koixafgb` (`movimiento_id`),
  CONSTRAINT `FKjs9qpup322mn2xkd4koixafgb` FOREIGN KEY (`movimiento_id`) REFERENCES `movimientos` (`id`)
) ENGINE=InnoDB;

/* Poblar tabla personas */
INSERT INTO personas(nombre, direccion, telefono, edad, genero, identificacion) VALUES ('Jose Lema', 'Otavalo sn y principal', '098254785', 45, 'M', 81345687);
INSERT INTO personas(nombre, direccion, telefono, edad, genero, identificacion) VALUES ('Marianela Montalvo', 'Amazonas y NNUU', '097548965', 27, 'F', 1032356782);
INSERT INTO personas(nombre, direccion, telefono, edad, genero, identificacion) VALUES ('Juan Osorio', '13 junio y Equinoccial', '098874587', 38, 'M', 79678342);

/* Poblar tabla clientes */
INSERT INTO clientes(contrasena, estado, persona_id) VALUES ('1234', 'True', 1);
INSERT INTO clientes(contrasena, estado, persona_id) VALUES ('5678', 'True', 2);
INSERT INTO clientes(contrasena, estado, persona_id) VALUES ('1245', 'True', 3);

/* Poblar tabla cuentas */
INSERT INTO cuentas(numero_cuenta, tipo, saldo_inicial, estado, cliente_id) VALUES ('478758', 'Ahorro', 2000, 'True', 1);
INSERT INTO cuentas(numero_cuenta, tipo, saldo_inicial, estado, cliente_id) VALUES ('225487', 'Corriente', 100, 'True', 2);
INSERT INTO cuentas(numero_cuenta, tipo, saldo_inicial, estado, cliente_id) VALUES ('495878', 'Ahorros', 0, 'True', 3);
INSERT INTO cuentas(numero_cuenta, tipo, saldo_inicial, estado, cliente_id) VALUES ('496825', 'Ahorros', 540, 'True', 2);

/* Poblar tabla movimientos */
INSERT INTO movimientos(tipo_movimiento, valor, saldo, cuenta_id, fecha) VALUES ('Retiro de 575', 575, 1425, 1, CURDATE());
INSERT INTO movimientos(tipo_movimiento, valor, saldo, cuenta_id, fecha) VALUES ('Deposito de 600', 600, 700, 2, '2022-02-10');
INSERT INTO movimientos(tipo_movimiento, valor, saldo, cuenta_id, fecha) VALUES ('Deposito de 150', 150, 150, 3, CURDATE());
INSERT INTO movimientos(tipo_movimiento, valor, saldo, cuenta_id, fecha) VALUES ('Retiro de 540', 540, 0, 2, '2022-02-08');

/* Poblar tabla reportes */
INSERT INTO reportes(fecha, cliente_id, movimiento_id) VALUES ('2022-02-10', 2, 2);
INSERT INTO reportes(fecha, cliente_id, movimiento_id) VALUES ('2022-02-08', 2, 4);
