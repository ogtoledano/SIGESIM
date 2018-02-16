package artex;

import static org.junit.Assert.*;

import java.util.Date;

import infoartex.artex.dominio.Interrupcion;
import infoartex.artex.servicio.Servicio;
import infoartex.artex.servicio.impl.ServicioImpl;

import org.junit.Test;

public class ServiceTest {
private Servicio servicio;
	@Test
	public void testRegistrarEntidad() {
		try{
		Interrupcion i=new Interrupcion("mb0123",new Date(),"6:30","Rodrigo Gonzalez","El monitor no funciona");
		servicio=new ServicioImpl();
		servicio.registrarEntidad(i);
		assertTrue("elemento registrado", true);
		}catch(Exception ex){
			fail("Ha ocurrido un error al registrar en la bd");
		}
	}

	@Test
	public void testObtenerSesion() {
		fail("Not yet implemented");
	}

	@Test
	public void testObtenerUsuario() {
		fail("Not yet implemented");
	}

	@Test
	public void testCerrarSesion() {
		fail("Not yet implemented");
	}

	@Test
	public void testCantidadElementos() {
		fail("Not yet implemented");
	}

	@Test
	public void testActualizarEntidad() {
		fail("Not yet implemented");
	}

	@Test
	public void testListarTodos() {
		fail("Not yet implemented");
	}

	@Test
	public void testEliminarEntidad() {
		fail("Not yet implemented");
	}

	@Test
	public void testListarMedioSinUso() {
		fail("Not yet implemented");
	}

	@Test
	public void testFiltrarRapidoMedios() {
		fail("Not yet implemented");
	}

	@Test
	public void testCantInterrupcionesTipo() {
		fail("Not yet implemented");
	}

	@Test
	public void testListarTecnicos() {
		fail("Not yet implemented");
	}

}
