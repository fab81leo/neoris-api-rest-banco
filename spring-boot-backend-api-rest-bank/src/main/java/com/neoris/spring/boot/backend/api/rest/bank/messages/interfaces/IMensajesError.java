package com.neoris.spring.boot.backend.api.rest.bank.messages.interfaces;

public interface IMensajesError {

	String ERROR_BD_CONSULTA = "consulta";
	String ERROR_BD_INSERCION = "inserción";
	String ERROR_BD_ACTUALIZACION = "actualización";
	String ERROR_BD_ELIMINACION = "eliminación";
	
	String ERROR_BASE_DE_DATOS = "Error al realizar %s en la Base de Datos...";	
	
	String ERROR_BUSQUEDA_CLIENTE_POR_ID = "El Cliente con Id %s no existe en el sistema!";
	String ERROR_EDICION_CLIENTE_POR_ID = "No se puede modificar el Cliente. ";
	
	String ERROR_BUSQUEDA_CUENTA_POR_ID = "La Cuenta con Id %s no existe en el sistema!";
	String ERROR_EDICION_CUENTA_POR_ID = "No se puede modificar la Cuenta. ";
	
	String ERROR_BUSQUEDA_MOVIMIENTO_POR_ID = "El movimiento con Id %s no existe en el sistema!";
	String ERROR_EDICION_MOVIMIENTO_POR_ID = "No se puede modificar el movimiento. ";
	
	String ERROR_SALDO_NO_DISPONIBLE = "Saldo no disponible!";
	String ERROR_RETIRO_SUPERA_SALDO_ACTUAL = "El valor a retirar es superior al saldo actual!";
	String ERROR_VALOR_DEPOSITO_NO_VALIDO = "El valor a depositar debe ser positivo!";
		
}