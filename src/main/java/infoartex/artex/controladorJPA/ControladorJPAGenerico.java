package infoartex.artex.controladorJPA;

import infoartex.artex.bundles.EstructuraUnion;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Sesion;

import com.vaadin.annotations.PreserveOnRefresh;

import java.util.HashMap;
import java.util.List;

@PreserveOnRefresh
public interface ControladorJPAGenerico {

    public Entidad salvar(Entidad entidad);

    public Entidad actualizar(Entidad entidad) throws Exception;

    public Entidad obtenerXId(Integer id, Class<?> clazz);

    public List<?> listarTodos(Class<?> clazz);
    
    public List<?> findEntidades(boolean all, int maxResults, int firstResult, Class<?> claz);

    public void eliminar(Integer id, Class<?> clazz) throws Exception;

    public Entidad obtenerUsuario(String usuario);

	public Sesion obtenerSesion(String usuario);
	
	public int cantidadElementos(Class<?> clazz);
	
	public List<?> buscarRapido(String criterio, List<String> campos,int maxResults, int firstResult, HashMap<String, String> filtro,List<EstructuraUnion> columnasUnion,Class<?> clazz);

	public int cantBuscarRapido();

	public List<?> listarMedioSinUso(String departamento,Class<?> clazz);
	
	public List<?> filtrarRapidoMedios(String criterio,String nombDepartamento ,List<String> campos, Class<?> clazz);
	

	public int cantInterrupcionesTipo(String tipo);

	public List<?> listarTecnicos();
	
	public List<?> listarInterupcionesXMes(int mes);
	
	public List<?> listarMantenimientosXMes(String mes);
	
	public Entidad obtenerOrden(int noOrden);
	
	public int maximoID(Class<?> clazz);
	
	public Entidad obtenerPiezaRecXCodigoInvent(String inventario);
	
	public Entidad obtenerPiezaRepuestoXCodigoInvent(String codigo);

	public Entidad obtenerTraslado(int parseInt);
	
	public List<?> obtenerPCLaptop(Class<?> clazz);
	
	public Entidad obtenerTipoMedio(String denominacion);
     
    }
