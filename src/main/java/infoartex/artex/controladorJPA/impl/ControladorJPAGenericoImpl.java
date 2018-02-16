package infoartex.artex.controladorJPA.impl;

import infoartex.artex.bundles.EstructuraUnion;
import infoartex.artex.controladorJPA.ControladorJPAGenerico;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Sesion;

import com.vaadin.annotations.PreserveOnRefresh;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

@PreserveOnRefresh
public class ControladorJPAGenericoImpl implements ControladorJPAGenerico {

	protected EntityManagerFactory emf = null;
	private int cantidadBusqueda;

	public ControladorJPAGenericoImpl() {
		emf = Persistence.createEntityManagerFactory("provider");
	}

	public Entidad salvar(Entidad entidad) {

		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(entidad);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return entidad;
	}

	public Entidad actualizar(Entidad entidad) throws Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			entidad = em.merge(entidad);
			em.getTransaction().commit();
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return entidad;
	}

	public Entidad obtenerXId(Integer id, Class<?> clazz) {
		EntityManager em = getEntityManager();
		try {
			return (Entidad) em.find(clazz, id);
		} finally {
			em.close();
		}
	}

	public List<?> listarTodos(Class<?> clazz) {
		return findEntidades(true, -1, -1, clazz);
	}

	public void eliminar(Integer id, Class<?> clazz) throws Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Entidad entidad;
			try {
				entidad = (Entidad) em.getReference(clazz, id);
				entidad.getId();
			} catch (EntityNotFoundException enfe) {
				throw new Exception("The entidad with id " + id + " no existe.", enfe);
			}
			em.remove(entidad);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<?> findEntidades(boolean all, int maxResults, int firstResult, Class<?> claz) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(claz));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	//
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public Entidad obtenerUsuario(String usuario) {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("Select u from Usuario u where u.usuario = ?1");
		q.setParameter(1, usuario);
		return (q.getResultList().size() > 0) ? (Entidad) q.getResultList().get(0) : null;
	}

	@Override
	public int cantidadElementos(Class<?> clazz) {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("SELECT count(e) FROM " + clazz.getName() + " e");
		long valor = (Long) q.getResultList().get(0);
		return Integer.parseInt("" + valor);
	}

	@Override
	public Sesion obtenerSesion(String usuario) {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("Select u from Sesion u where u.usuario = ?1");
		q.setParameter(1, usuario);
		return (q.getResultList().size() > 0) ? (Sesion) q.getResultList().get(0) : null;
	}

	@Override
	public List<?> buscarRapido(String criterio, List<String> campos, int maxResults, int firstResult, HashMap<String, String> filtro, List<EstructuraUnion> columnasUnion, Class<?> clazz) {
		EntityManager em = getEntityManager();
		String jql = "Select e from " + clazz.getName() + " e ";
		for (EstructuraUnion est : columnasUnion) {
			jql += "join e." + est.getColumna() + " " + est.getColumna() + " ";
		}
		jql += " where (";
		for (int i = 0; i < campos.size() - 1; i++) {
			jql += "lower(concat(e." + campos.get(i) + ",\"\"" + ")) like ?1 or ";
		}

		jql += "lower(concat(e." + campos.get(campos.size() - 1) + ",\"\"" + ")) like ?1)";

		int i = 2;

		Queue<String> valores = new LinkedList<>();
		if (filtro != null && !filtro.isEmpty()) {
			for (String key : campos) {
				if (filtro.get(key) != null) {
					jql += "and e." + key + " = ?" + (i++) + " ";
					valores.add(filtro.get(key));
				}
			}
			for (EstructuraUnion est : columnasUnion) {
				if (filtro.get(est.getColumna()) != null) {
					jql += "and (";
					List<String> attrs=est.getAttrs();
					for(int k=0;k<attrs.size()-1;k++){
						jql +=est.getColumna()+"." + attrs.get(k) + " = ?" + (i++) + "or ";
					}
					jql +=est.getColumna()+"." + attrs.get(attrs.size()-1) + " = ?" + (i++) + ") ";
					valores.add(filtro.get(est.getColumna()));
				}
			}
			
		}
		Query q = (Query) em.createQuery(jql);
		q.setParameter(1, "%" + criterio.toLowerCase() + "%");
		for (int j = 2; j < i; j++) {
			q.setParameter(j, valores.poll());
		}
		cantidadBusqueda = q.getResultList().size();
		q.setMaxResults(maxResults);
		q.setFirstResult(firstResult);
		return q.getResultList();
	}

	public List<?> filtrarRapidoMedios(String criterio, String nombDepartamento, List<String> campos, Class<?> clazz) {
		EntityManager em = getEntityManager();
		String jql = "Select e from " + clazz.getName() + " e join e.departamento d where (";
		for (int i = 0; i < campos.size() - 1; i++) {
			jql += " lower(e." + campos.get(i) + ") like ?1 or ";
		}
		jql += "lower(e." + campos.get(campos.size() - 1) + ") like ?1) and e.nombComputadora = ?2 and d.nombre = ?3";
		Query q = (Query) em.createQuery(jql);
		q.setParameter(1, "%" + criterio.toLowerCase() + "%");
		q.setParameter(2, "Sin asignar");
		q.setParameter(3, nombDepartamento);
		cantidadBusqueda = q.getResultList().size();
		return q.getResultList();
	}

	@Override
	public int cantBuscarRapido() {
		return cantidadBusqueda;
	}

	@Override
	public List<?> listarMedioSinUso(String departamento, Class<?> clazz) {
		EntityManager em = getEntityManager();
		String jpql = "SELECT m FROM " + clazz.getName() + " m join m.departamento d where m.nombComputadora = ?1 and d.nombre=?2";
		Query query = em.createQuery(jpql);
		query.setParameter(1, "Sin asignar");
		query.setParameter(2, departamento);
		return query.getResultList();
	}

	@Override
	public int cantInterrupcionesTipo(String tipo) {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("SELECT count(e) FROM Interrupcion e where e.estado=?1");
		q.setParameter(1, tipo);
		long valor = (Long) q.getResultList().get(0);
		return Integer.parseInt("" + valor);
	}

	@Override
	public List<?> listarTecnicos() {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("SELECT e FROM Usuario e join e.rol r where r.nombre=?1 or r.nombre=?2");
		q.setParameter(1, "Técnico");
		q.setParameter(2, "Administrador");
		return q.getResultList();
	}

	public List<?> listarInterupcionesXMes(int mes) {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("SELECT e FROM Interrupcion e where e.fecha between ?1 and ?2 and e.estado=?3");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		gc.set(GregorianCalendar.MONTH, mes - 1);
		gc.set(GregorianCalendar.DAY_OF_MONTH, 1);
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime(new Date());
		gc2.set(GregorianCalendar.MONTH, mes);
		gc2.set(GregorianCalendar.DAY_OF_MONTH, 1);
		gc2.set(GregorianCalendar.DAY_OF_MONTH, gc2.get(GregorianCalendar.DAY_OF_MONTH) - 1);
		q.setParameter(1, gc.getTime());
		q.setParameter(2, gc2.getTime());
		q.setParameter(3, "Sin procesar");
		return q.getResultList();
	}

	public List<?> listarMantenimientosXMes(String mes) {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("SELECT e FROM Mantenimiento e where e.mes=?1");
		q.setParameter(1, mes);
		return q.getResultList();
	}

	public int maximoID(Class<?> clazz) {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("SELECT max(e.id) FROM " + clazz.getName() + " e");
		return q.getResultList().get(0) == null ? 0 : (Integer) q.getResultList().get(0);
	}

	public Entidad obtenerOrden(int noOrden) {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("Select o from OrdenTrabajo o where o.numero = ?1");
		q.setParameter(1, noOrden);
		return (q.getResultList().size() > 0) ? (Entidad) q.getResultList().get(0) : null;
	}

	public Entidad obtenerPiezaRecXCodigoInvent(String inventario) {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("Select o from PiezaRecuperada o where o.inventario = ?1");
		q.setParameter(1, inventario);
		return (q.getResultList().size() > 0) ? (Entidad) q.getResultList().get(0) : null;
	}

	public Entidad obtenerPiezaRepuestoXCodigoInvent(String codigo) {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("Select o from PiezaRepuesto o where o.codigo = ?1 and o.noOrden= ?2");
		q.setParameter(1, codigo);
		q.setParameter(2, "Disponible");
		return (q.getResultList().size() > 0) ? (Entidad) q.getResultList().get(0) : null;
	}

	@Override
	public Entidad obtenerTraslado(int parseInt) {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("Select o from Traslado o where o.numero = ?1");
		q.setParameter(1, "" + parseInt);
		return (q.getResultList().size() > 0) ? (Entidad) q.getResultList().get(0) : null;
	}

	public List<?> obtenerPCLaptop(Class<?> clazz) {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("SELECT e FROM " + clazz.getName() + " e");
		return q.getResultList();
	}

	public Entidad obtenerTipoMedio(String denominacion) {
		EntityManager em = getEntityManager();
		Query q = (Query) em.createQuery("Select o from TipoMedio o where o.denominacion = ?1");
		q.setParameter(1, denominacion);
		return (q.getResultList().size() > 0) ? (Entidad) q.getResultList().get(0) : null;
	}

}
