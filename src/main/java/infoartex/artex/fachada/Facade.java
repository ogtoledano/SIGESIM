package infoartex.artex.fachada;

import java.util.List;

import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Interrupcion;
import infoartex.artex.dominio.Mantenimiento;
import infoartex.artex.dominio.Sesion;
import infoartex.artex.dominio.Usuario;


public interface Facade {
    
        public void registrarEntidad(Entidad entity);

		public Sesion obtenerSesion(String usuario);
		
		public Usuario obtenerUsuario(String usuario);

		public void cerrarSesion(String usuario) throws Exception;

	    public int cantidadElementos(Class<?> clazz);

		public Entidad actualizarEntidad(Entidad entity)throws Exception;

		public List<?> listarTodos(Class<?> clazz);

		public void eliminarEntidad(int id,Class<?> clazz) throws Exception;

		public List<?> listarMedioSinUso(String departamento,Class<?> clazz);
		
		public List<?> filtrarRapidoMedios(String criterio,String nombDepartamento, List<String> campos, Class<?> clazz);
		
		public int cantInterrupcionesTipo(String tipo);
		
		public List<Usuario> listarTecnicos();
		
		public List<Interrupcion> listarInterupcionesXMes(int mes);
		
		public List<Mantenimiento> listarMantenimientosXMes(String mes);
		
		public int maximoID(Class<?> clazz);
		
		public Entidad obtenerOrden(int noOrden);
		
		public Entidad obtenerPiezaRecXCodigoInvent(String inventario);
		
		public Entidad obtenerPiezaRepuestoXCodigoInvent(String codigo);

		public Entidad obtenerTraslado(int parseInt);
		
		public List<?> obtenerPCLaptop(Class<?> clazz);
		
		public Entidad obtenerTipoMedio(String denominacion);

}
