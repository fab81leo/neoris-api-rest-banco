package com.neoris.spring.boot.backend.api.rest.bank.messages.interfaces;

public interface IMensajes {

	String TIPO_DEBITO = "Retiro";
	String TIPO_CREDITO = "Deposito";
		
	String REGISTRO_CLIENTE_CREADO_CON_EXITO = "El cliente ha sido creado con éxito!";
	String REGISTRO_CLIENTE_ACTUALIZADO_CON_EXITO = "El cliente ha sido actualizado con éxito!";
	String REGISTRO_CLIENTE_ELIMINADO_CON_EXITO = "El cliente ha sido eliminado con éxito!";
	
	String REGISTRO_CUENTA_CREADA_CON_EXITO = "La cuenta ha sido creada con éxito!";
	String REGISTRO_CUENTA_ACTUALIZADA_CON_EXITO = "La cuenta ha sido actualizado con éxito!";
	String REGISTRO_CUENTA_ELIMINADA_CON_EXITO = "La cuenta ha sido eliminada con éxito!";
	
	String REGISTRO_MOVIMIENTO_CREADO_CON_EXITO = "El movimiento ha sido creado con éxito!";
	String REGISTRO_MOVIMIENTO_ACTUALIZADO_CON_EXITO = "El movimiento ha sido actualizado con éxito!";
	String REGISTRO_MOVIMIENTO_ELIMINADO_CON_EXITO = "El movimiento ha sido eliminado con éxito!";
	
}