package infoartex.artex.dominio;

import infoartex.artex.controladorJPA.ControladorJPAGenerico;
import infoartex.artex.controladorJPA.impl.ControladorJPAGenericoImpl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;


//obligado
@SuppressWarnings("serial")
@Entity
public class OrdenTrabajo extends Entidad {

	@NotNull(message="El valor no puede ser nulo")
	public int numero;
	@NotNull(message="La fecha no puede ser vacía")
	@Temporal(TemporalType.DATE)
	public Date fecha;
	
	@NotBlank(message = "El invenatrio no puede ser vacío")
	@Pattern(regexp="[0-9]+(-)([0-9]|[a-z]|[A-Z])+",message="Debe estar en el formato <centro costo>-<numeros o letras>")
	public String inventario;
	
	@NotBlank(message = "El tipo de medio no puede ser vacío")
	public String tipomedio;
	
	@NotBlank(message = "El dictamen no puede ser vacío")
	public String dictamen;
	
	@NotBlank(message = "El campo de las acciones no puede ser vacío")
	public String acciones;
	@NotNull(message="La fecha de salida no puede ser vacía")
	@Temporal(TemporalType.DATE)
	public Date fechasalida;
	
	@NotBlank(message = "El campo del trabajador no puede ser vacío")
	public String trabajadorentregado;
	
	public String estado;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	public List<PiezaRepuesto> piezasRepuesto;
	
	@OneToMany
	public List<PiezaRecuperada> piezasRecuperada;
	
	@OneToOne(mappedBy="orden")
	public Interrupcion interrupcion;

	//obligado
		public OrdenTrabajo(){ }

		//obligado
		public OrdenTrabajo(int numero, Date fecha, String inventario,
				String tipomedio, String dictamen, String acciones,
				Date fechasalida, String trabajadorentregado) {
			super();
			this.numero = numero;
			this.fecha = fecha;
			this.inventario = inventario;
			this.tipomedio = tipomedio;
			this.dictamen = dictamen;
			this.acciones = acciones;
			this.fechasalida = fechasalida;
			this.trabajadorentregado = trabajadorentregado;
			this.piezasRecuperada=new LinkedList<>();
			this.piezasRepuesto=new LinkedList<>();
		}

		public int getNumero() {
			return numero;
		}

		public void setNumero(int numero) {
			this.numero = numero;
		}

		public Date getFecha() {
			return fecha;
		}

		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}

		public String getInventario() {
			return inventario;
		}

		public void setInventario(String inventario) {
			this.inventario = inventario;
		}

		public String getTipomedio() {
			return tipomedio;
		}

		public void setTipomedio(String tipomedio) {
			this.tipomedio = tipomedio;
		}

		public String getDictamen() {
			return dictamen;
		}

		public void setDictamen(String dictamen) {
			this.dictamen = dictamen;
		}

		public String getAcciones() {
			return acciones;
		}

		public void setAcciones(String acciones) {
			this.acciones = acciones;
		}

		public Date getFechasalida() {
			return fechasalida;
		}

		public void setFechasalida(Date fechasalida) {
			this.fechasalida = fechasalida;
		}

		public String getTrabajadorentregado() {
			return trabajadorentregado;
		}

		public void setTrabajadorentregado(String trabajadorentregado) {
			this.trabajadorentregado = trabajadorentregado;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public List<PiezaRepuesto> getPiezasRepuesto() {
			return piezasRepuesto;
		}

		public void setPiezasRepuesto(List<PiezaRepuesto> piezasRepuesto) {
			this.piezasRepuesto = piezasRepuesto;
		}

		public List<PiezaRecuperada> getPiezasRecuperada() {
			return piezasRecuperada;
		}

		public void setPiezasRecuperada(List<PiezaRecuperada> piezasRecuperada) {
			this.piezasRecuperada = piezasRecuperada;
		}
		
		public String toString(){
			return this==null?"":numero+"";
		}
		
		
		
	public Interrupcion getInterrupcion() {
			return interrupcion;
		}

		public void setInterrupcion(Interrupcion interrupcion) {
			this.interrupcion = interrupcion;
		}

	@PreRemove
	public void devolverPiezas(){
		ControladorJPAGenerico controller=new ControladorJPAGenericoImpl();
		if(interrupcion!=null){
			interrupcion.setOrden(null);
			interrupcion.setEstado("Sin procesar");
		}
		List<PiezaRepuesto> listaPieza=new LinkedList<PiezaRepuesto>();
		listaPieza.addAll(piezasRepuesto);
		piezasRepuesto.clear();
		try {
			controller.actualizar(this);
			if(interrupcion!=null){
				controller.actualizar(interrupcion);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(PiezaRepuesto p:listaPieza){
			PiezaRepuesto aux=(PiezaRepuesto) controller.obtenerPiezaRepuestoXCodigoInvent(p.codigo);
			if(aux!=null){
				aux.setCantidad(aux.getCantidad()+p.getCantidad());
				try {
					p.setOrden(null);
					controller.actualizar(aux);
					controller.eliminar(p.getId(), PiezaRepuesto.class);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				p.setOrden(null);
				p.setNoOrden("Disponible");
				try {
					controller.actualizar(p);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		for(PiezaRecuperada p:piezasRecuperada){
			p.setOrden(null);
			p.setNoOrden("Disponible");
		}
		
		try {
			controller.actualizar(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
