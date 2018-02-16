/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package infoartex.artex.servicio.impl;

import java.util.List;

import infoartex.artex.controladorJPA.ControladorJPAGenerico;
import infoartex.artex.controladorJPA.impl.ControladorJPAGenericoImpl;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Interrupcion;
import infoartex.artex.dominio.Mantenimiento;
import infoartex.artex.dominio.Sesion;
import infoartex.artex.dominio.Usuario;
import infoartex.artex.servicio.Servicio;

/**
 *
 * @author administrador
 */
public class ServicioImpl implements Servicio{
    private ControladorJPAGenerico controlador;

    public ServicioImpl() {
        controlador=new ControladorJPAGenericoImpl();
    }
    
    @Override
    public void registrarEntidad(Entidad entity) {
        controlador.salvar(entity);
    }

	@Override
	public Sesion obtenerSesion(String usuario) {
		return controlador.obtenerSesion(usuario);
	}

	@Override
	public Usuario obtenerUsuario(String usuario) {
		return (Usuario)controlador.obtenerUsuario(usuario);
	}

	@Override
	public void cerrarSesion(String usuario) throws Exception {
		controlador.eliminar(obtenerSesion(usuario).getId(), Sesion.class);
		
	}

	@Override
	public int cantidadElementos(Class<?> clazz) {
		return controlador.cantidadElementos(clazz);
		
	}

	@Override
	public Entidad actualizarEntidad(Entidad entity) throws Exception {
		return controlador.actualizar(entity);
		
	}

	@Override
	public List<?> listarTodos(Class<?> clazz) {
		return controlador.listarTodos(clazz);
	}

	@Override
	public void eliminarEntidad(int id,Class<?> clazz) throws Exception {
		controlador.eliminar(id, clazz);
		
	}

	@Override
	public List<?> listarMedioSinUso(String departamento,Class<?> clazz) {
		return controlador.listarMedioSinUso(departamento,clazz);
	}

	public List<?> filtrarRapidoMedios(String criterio,String nombDepartamento, List<String> campos, Class<?> clazz){
		return controlador.filtrarRapidoMedios(criterio,nombDepartamento, campos, clazz);
	}

	@Override
	public int cantInterrupcionesTipo(String tipo) {
		return controlador.cantInterrupcionesTipo(tipo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listarTecnicos() {
		return (List<Usuario>) controlador.listarTecnicos();
	}
	
	@SuppressWarnings("unchecked")
	public List<Interrupcion> listarInterupcionesXMes(int mes){
		return (List<Interrupcion>) controlador.listarInterupcionesXMes(mes);
	}
	
	@SuppressWarnings("unchecked")
	public List<Mantenimiento> listarMantenimientosXMes(String mes){
		return (List<Mantenimiento>) controlador.listarMantenimientosXMes(mes);
	}

	@Override
	public int maximoID(Class<?> clazz) {
		return controlador.maximoID(clazz);
	}

	@Override
	public Entidad obtenerOrden(int noOrden) {
		return controlador.obtenerOrden(noOrden);
	}
	
	public Entidad obtenerPiezaRecXCodigoInvent(String inventario){
		 return controlador.obtenerPiezaRecXCodigoInvent(inventario);
	}
    
	public Entidad obtenerPiezaRepuestoXCodigoInvent(String codigo){
		 return controlador.obtenerPiezaRepuestoXCodigoInvent(codigo);
	}

	@Override
	public Entidad obtenerTraslado(int parseInt) {
		return controlador.obtenerTraslado(parseInt);
	}
	
	public List<?> obtenerPCLaptop(Class<?> clazz){
		return controlador.obtenerPCLaptop(clazz);
	}
	
	public Entidad obtenerTipoMedio(String denominacion){
		return controlador.obtenerTipoMedio(denominacion);
	}
	
}
