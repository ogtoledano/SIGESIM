package infoartex.artex.bundles;

import infoartex.artex.controladorJPA.ControladorJPAGenerico;
import infoartex.artex.controladorJPA.impl.ControladorJPAGenericoImpl;
import infoartex.artex.dominio.Entidad;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;



public class Paginador<T>{

	private LinkedList<T> elementos;
	private int pageSize;
	private ControladorJPAGenerico controlador;
	// sea posInicial y posFinal rango de la ventana
	private int posInicial;
	private int posFinal;
	// sea j y k rango de página
	private int j;
	private int k;
	// página actual y cantidad de páginas
	private int currentPage;
	private int cantPaginas;
	private int cantTuplas;
	Class<?> clase;
	// para el buscador
	private boolean isBuscar;
	private List<EstructuraUnion> columnasUnion;
	//esto todavia
	
	//private HashMap<String, String> valores;
	
	@SuppressWarnings("unused")
	private int idUltimo;
	@SuppressWarnings("unused")
	private int idPrimero;
	@SuppressWarnings("unused")
	private boolean reverse;
	
	private int cantTotalReg;
	private List<String> campos;
	private String criterio;
	private HashMap<String, String> filtros;

	public Paginador(int size, int tuplas, Class<?> c) {
		clase = c;
		elementos = new LinkedList<T>();
		pageSize = size;
		controlador = new ControladorJPAGenericoImpl();
		posInicial = 0;
		columnasUnion=new LinkedList<>();
		// para que funcione por demanda
		CargarDatos();
		cantTotalReg = controlador.cantidadElementos(clase);
		if (cantTotalReg % pageSize == 0) {
			cantPaginas = cantTotalReg / pageSize;
		} else {
			cantPaginas = cantTotalReg / pageSize + 1;
		}
		currentPage = 1;
		cantTuplas = tuplas;
		isBuscar = false;
		filtros=new HashMap<>();
	}
	
	public Paginador(int size, int tuplas,String criterio, List<String> campos, Class<?> c) {
		clase = c;
		elementos = new LinkedList<T>();
		pageSize = size;
		controlador = new ControladorJPAGenericoImpl();
		posInicial = 0;
		isBuscar = true;
		columnasUnion=new LinkedList<>();
		// para que funcione por demanda
		this.criterio=criterio;
		this.campos=campos;
		CargarDatos();
		cantTotalReg = controlador.cantBuscarRapido();
		if (cantTotalReg % pageSize == 0) {
			cantPaginas = cantTotalReg / pageSize;
		} else {
			cantPaginas = cantTotalReg / pageSize + 1;
		}
		currentPage = 1;
		cantTuplas = tuplas;	
		filtros=new HashMap<>();
	}
	
	public Paginador(int size, int tuplas,String criterio, List<String> campos, HashMap<String, String> filtro,Class<?> c) {
		clase = c;
		elementos = new LinkedList<T>();
		pageSize = size;
		controlador = new ControladorJPAGenericoImpl();
		posInicial = 0;
		// para que funcione por demanda
		isBuscar = true;
		this.filtros=filtro;
		this.campos=campos;
		this.criterio=criterio;
		this.columnasUnion=new LinkedList<>();
		CargarDatos();
		cantTotalReg = controlador.cantBuscarRapido();
		if (cantTotalReg % pageSize == 0) {
			cantPaginas = cantTotalReg / pageSize;
		} else {
			cantPaginas = cantTotalReg / pageSize + 1;
		}
		currentPage = 1;
		cantTuplas = tuplas;
	}
	
	public Paginador(int size, int tuplas,String criterio, List<String> campos, HashMap<String, String> filtro,List<EstructuraUnion> columnasUnion,Class<?> c) {
		clase = c;
		elementos = new LinkedList<T>();
		pageSize = size;
		controlador = new ControladorJPAGenericoImpl();
		posInicial = 0;
		// para que funcione por demanda
		isBuscar = true;
		this.filtros=filtro;
		this.campos=campos;
		this.criterio=criterio;
		this.columnasUnion=columnasUnion;
		CargarDatos();
		cantTotalReg = controlador.cantBuscarRapido();
		if (cantTotalReg % pageSize == 0) {
			cantPaginas = cantTotalReg / pageSize;
		} else {
			cantPaginas = cantTotalReg / pageSize + 1;
		}
		currentPage = 1;
		cantTuplas = tuplas;
	}
	

	public void CargarDatos() {
		if (!isBuscar) {
				@SuppressWarnings("unchecked")
				List<T> aux = (List<T>) controlador.findEntidades(false, cantTuplas, posInicial, clase);
				elementos.clear();
				elementos.addAll(aux);
				posFinal = posInicial + elementos.size() - 1;
				j = 0;
				k = pageSize - 1;
				if (k > elementos.size() - 1) {
					k = elementos.size() - 1;
				}
		} else {
				@SuppressWarnings("unchecked")
				List<T> aux = (List<T>) controlador.buscarRapido(criterio, campos, cantTuplas, posInicial,filtros,columnasUnion, clase);
				elementos.clear();
				elementos.addAll(aux);
				posFinal = posInicial + elementos.size() - 1;
				j = 0;
				k = pageSize - 1;
				if (k > elementos.size() - 1) {
					k = elementos.size() - 1;
				}
				if (elementos.size() != 0) {
					idUltimo = ((Entidad) elementos.get(elementos.size() - 1))
							.getId();
					idPrimero = ((Entidad) elementos.get(0)).getId();
				}
		}
	}

	@SuppressWarnings("unchecked")
	public void actualizarCuandoElimina() {
		if (!isBuscar) {
			List<T> aux = (List<T>) controlador.findEntidades(false, cantTuplas, posInicial, clase);
			elementos.clear();
			elementos.addAll(aux);
			cantTotalReg = controlador.cantidadElementos(clase);
			if (cantTotalReg % pageSize == 0) {
				cantPaginas = cantTotalReg / pageSize;
			} else {
				cantPaginas = cantTotalReg / pageSize + 1;
			}
			if (k > elementos.size() - 1) {
				k = elementos.size() - 1;
			}
		} else {
			List<T> aux = (List<T>) controlador.buscarRapido(criterio, campos, cantTuplas, posInicial,filtros,columnasUnion, clase);
			j = 0;
			k = pageSize - 1;
			currentPage = 1;
			elementos.clear();
			elementos.addAll(aux);
			cantTotalReg = controlador.cantBuscarRapido();
			if (cantTotalReg % pageSize == 0) {
				cantPaginas = cantTotalReg / pageSize;
			} else {
				cantPaginas = cantTotalReg / pageSize + 1;
			}
			if (k > elementos.size() - 1) {
				k = elementos.size() - 1;
			}
		}
	}

	public LinkedList<T> getElementos() {
		return elementos;
	}

	public void pagSiguiente() {
		if (k == elementos.size() - 1) {
			posInicial = posFinal + 1;
			reverse = false;
			CargarDatos();
			currentPage++;
		} else {
			j = k + 1;
			k += pageSize;
			if (k > elementos.size() - 1) {
				k = elementos.size() - 1;
			}
			currentPage++;
		}
	}

	public void pagAnterior() {
		if (j == 0) {
			posInicial = posInicial - pageSize;
			if (posInicial < 0) {
				posInicial = 0;
			}
			reverse = true;
			CargarDatos();
			currentPage--;
			} else {
				k = j - 1;
				j -= pageSize;
				if (j < 0) {
					j = 0;
			}
			currentPage--;
		}	
	}


	public List<T> devolverElementosActuales() {
		if (elementos.size() != 0) {
			return (List<T>) elementos.subList(j, k + 1);
		} else {
			return new LinkedList<T>();
		}
	}


	public void irAlFinal() {
		currentPage = cantPaginas;
		posInicial = (cantPaginas - 1) * pageSize;
		posFinal = posInicial + pageSize;
		if (posFinal > elementos.size() - 1) {
			posFinal = elementos.size() - 1;
		}
		reverse = false;
		if (isBuscar) {
			//idUltimo = controlador.obtenerIdUltimo(valores, pageSize);
		}
		CargarDatos();	
	}


	public void irAlPrincipio() {
		currentPage = 1;
		posInicial = 0;
		posFinal = posInicial + elementos.size() - 1;
		j = 0;
		k = pageSize - 1;
		if (k > elementos.size() - 1) {
			k = elementos.size() - 1;
		}
		CargarDatos();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getCantPaginas() {
		return cantPaginas;
	}

	public void setIsBuscar(boolean valor) {
		isBuscar = valor;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setCantPaginas() {
		if (cantTotalReg % pageSize == 0) {
			cantPaginas = cantTotalReg / pageSize;
		} else {
			cantPaginas = cantTotalReg / pageSize + 1;
		}
	}

	public boolean isBuscar() {
		return isBuscar;
	}

	public HashMap<String, String> getFiltros() {
		return filtros;
	}

	public void setFiltros(HashMap<String, String> filtros) {
		this.filtros = filtros;
	}

	public List<EstructuraUnion> getColumnasUnion() {
		return columnasUnion;
	}

	public void setColumnasUnion(List<EstructuraUnion> columnasUnion) {
		this.columnasUnion = columnasUnion;
	}
	
	
	

}