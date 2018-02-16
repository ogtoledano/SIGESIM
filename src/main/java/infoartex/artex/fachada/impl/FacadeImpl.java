/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package infoartex.artex.fachada.impl;

import java.util.List;

import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Interrupcion;
import infoartex.artex.dominio.Mantenimiento;
import infoartex.artex.dominio.Sesion;
import infoartex.artex.dominio.Usuario;
import infoartex.artex.fachada.Facade;
import infoartex.artex.servicio.Servicio;
import infoartex.artex.servicio.impl.ServicioImpl;

/**
 *
 * @author administrador
 */
public class FacadeImpl implements Facade{

    private Servicio servicio;

    public FacadeImpl() {
        servicio=new ServicioImpl();
    }

    @Override
    public void registrarEntidad(Entidad entity) {
        servicio.registrarEntidad(entity);
    }

	@Override
	public Sesion obtenerSesion(String usuario) {
		return servicio.obtenerSesion(usuario);
	}

	@Override
	public Usuario obtenerUsuario(String usuario) {
		return servicio.obtenerUsuario(usuario);
	}

	@Override
	public void cerrarSesion(String usuario) throws Exception{
		servicio.cerrarSesion(usuario);
		
	}

	@Override
	public int cantidadElementos(Class<?> clazz) {
		return servicio.cantidadElementos(clazz);
	}

	@Override
	public Entidad actualizarEntidad(Entidad entity) throws Exception{
		return servicio.actualizarEntidad(entity);
		
	}

	@Override
	public List<?> listarTodos(Class<?> clazz) {
		return servicio.listarTodos(clazz);
	}

	@Override
	public void eliminarEntidad(int id,Class<?> clazz) throws Exception{
		servicio.eliminarEntidad(id,clazz);
		
	}

	@Override
	public List<?> listarMedioSinUso(String departamento,
			Class<?> clazz) {
		return servicio.listarMedioSinUso(departamento,clazz);
	}

	public List<?> filtrarRapidoMedios(String criterio,String nombDepartamento, List<String> campos, Class<?> clazz){
		return servicio.filtrarRapidoMedios(criterio,nombDepartamento, campos, clazz);
	}

	@Override
	public int cantInterrupcionesTipo(String tipo) {
		return servicio.cantInterrupcionesTipo(tipo);
	}

	@Override
	public List<Usuario> listarTecnicos() {
		return servicio.listarTecnicos();
	}
	
	public List<Interrupcion> listarInterupcionesXMes(int mes){
		return servicio.listarInterupcionesXMes(mes);
	}
	
	public List<Mantenimiento> listarMantenimientosXMes(String mes){
		return servicio.listarMantenimientosXMes(mes);
	}

	@Override
	public int maximoID(Class<?> clazz) {
		return servicio.maximoID(clazz);
	}

	@Override
	public Entidad obtenerOrden(int noOrden) {
		return servicio.obtenerOrden(noOrden);
	}

	@Override
	public Entidad obtenerPiezaRecXCodigoInvent(String inventario) {
		return servicio.obtenerPiezaRecXCodigoInvent(inventario);
	}

	@Override
	public Entidad obtenerPiezaRepuestoXCodigoInvent(String codigo) {
		return servicio.obtenerPiezaRepuestoXCodigoInvent(codigo);
	}

	@Override
	public Entidad obtenerTraslado(int parseInt) {
		return servicio.obtenerTraslado(parseInt);
	}

	@Override
	public List<?> obtenerPCLaptop(Class<?> clazz) {
		return servicio.obtenerPCLaptop(clazz);
	}
	
	public Entidad obtenerTipoMedio(String denominacion){
		return servicio.obtenerTipoMedio(denominacion);
	}
    
}
