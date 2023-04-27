package com.neoris.spring.boot.backend.api.rest.bank;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.neoris.spring.boot.backend.api.rest.bank.controllers.CuentaRestController;
import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Cliente;
import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Cuenta;
import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Movimiento;
import com.neoris.spring.boot.backend.api.rest.bank.services.IClienteService;
import com.neoris.spring.boot.backend.api.rest.bank.services.ICuentaService;
import com.neoris.spring.boot.backend.api.rest.bank.services.IMovimientoService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.log4j.Logger;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class SpringBootBackendApiRestBankApplicationTests {

	private final static Logger logger = Logger.getLogger(SpringBootBackendApiRestBankApplicationTests.class);
		
	@Autowired
	CuentaRestController cuentaRestController;
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private ICuentaService cuentaService;

	@Autowired
	private IMovimientoService movimientoService;
	
	// Esto se ejecuta antes de cada método de prueba
	@BeforeEach
	void beforeEach() {
		logger.info("------ Iniciando pruebas sobre endPoints - API REST Bank ------");		    
	}
	
	// Esto se ejecuta despues de cada método de prueba
    @AfterEach
    void afterEach() {
    	logger.info("------ Finalizando pruebas sobre endPoints - API REST Bank ------");        
    }
	
    @BeforeAll
    void beforeAll() {
    	logger.info("====== Iniciando JUnit 5 Test - API REST Bank =====");
    }
    
    @AfterAll
    void afterAll() { 
    	logger.info("====== Finalizando JUnit 5 Test - API REST Bank =====");
    }
   
    @Test
    @Order(1)
    @DisplayName("Test - Consulta identificacion de persona por Id ")
   	void testConsultaIdentificacionPersona() {
       	Cliente cliente = clienteService.findById(1L);
   		assertEquals("81345687", cliente.getPersona().getIdentificacion());
   	}
    
    @Test
    @Order(2)
    @DisplayName("Test - Consulta nombre de persona por Id ")
   	void testConsultaNombrePersona() {
       	Cliente cliente = clienteService.findById(1L);
   		assertEquals("Jose Lema", cliente.getPersona().getNombre());
   	}
    
    @Test
    @Order(3)
    @DisplayName("Test - Consulta tipo de cuenta por Id ")
	void testConsultaTipoNumeroCuenta() {
    	Movimiento movimiento = movimientoService.findById(2L);
		assertEquals("Corriente", movimiento.getCuenta().getTipo());
	}
    
    @Test
    @Order(4)
    @DisplayName("Test - Consulta saldo inicial de la cuenta ")
	void testConsultaCuentaSaldoInicial() {
    	Cuenta cuenta = cuentaService.findById(2L);
		assertEquals(100, cuenta.getSaldoInicial());
	}
    
    @Test
    @Order(5)
    @DisplayName("Test - Consulta saldo en movimientos por Id")
	void testConsultaSaldo() {
    	Movimiento movimiento = movimientoService.findById(2L);
		assertEquals(700, movimiento.getSaldo());
	}
    
    @Test
    @Order(6)
    @DisplayName("Test - Consulta Estado y Tipo de Cuenta")
    void testConsultaTipoCuenta() {
    	
    	long id = 1;
    	ResponseEntity<?> response = cuentaRestController.show(id);
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	
    	if (HttpStatus.OK.equals(response.getStatusCode())) {
    		assertEquals("Ahorro", ((Cuenta)response.getBody()).getTipo());
    	}
    }
    	
}