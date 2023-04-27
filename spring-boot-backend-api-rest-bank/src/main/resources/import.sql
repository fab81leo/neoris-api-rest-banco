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
INSERT INTO movimientos(tipo_movimiento, valor, saldo, cuenta_id, fecha) VALUES ('Retiro de 575', 575, 1425, 1, NOW());
INSERT INTO movimientos(tipo_movimiento, valor, saldo, cuenta_id, fecha) VALUES ('Deposito de 600', 600, 700, 2, '2022-02-10');
INSERT INTO movimientos(tipo_movimiento, valor, saldo, cuenta_id, fecha) VALUES ('Deposito de 150', 150, 150, 3, NOW());
INSERT INTO movimientos(tipo_movimiento, valor, saldo, cuenta_id, fecha) VALUES ('Retiro de 540', 540, 0, 2, '2022-02-08');

/* Poblar tabla reportes */
INSERT INTO reportes(fecha, cliente_id, movimiento_id) VALUES ('2022-02-10', 2, 2);
INSERT INTO reportes(fecha, cliente_id, movimiento_id) VALUES ('2022-02-08', 2, 4);
