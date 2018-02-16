package infoartex.artex.dominio;

import infoartex.artex.controladorJPA.ControladorJPAGenerico;
import infoartex.artex.controladorJPA.impl.ControladorJPAGenericoImpl;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

//obligado
@SuppressWarnings("serial")
@Entity
public class Interrupcion extends Entidad{
	@NotBlank(message = "El inventario no puede ser vacío")
	@Pattern(regexp="[0-9]+(-)([0-9]|[a-z]|[A-Z])+",message="Debe estar en el formato <centro costo>-<numeros o letras>")
	public String inventario;
	@NotNull(message="La fecha no puede ser vacía")
	@Temporal(TemporalType.DATE)
	public Date fecha;
	public String hora;
	@NotBlank(message = "El nombre del trabajador no puede ser vacío")
	public String trabajador;
	
	@NotBlank(message = "La descripción no puede ser vacía")
	protected String descripcion;
	
	@ManyToOne
	public Departamento departamento;
	
	private String usuario; 
	
	public String estado;
	
	@OneToOne
	private OrdenTrabajo orden;
	
	//obligado
	public Interrupcion(){
		
	}
	
	//obligado
	public Interrupcion(String inventario, Date fecha, String hora,
			String trabajador, String descripcion) {
		super();
		this.inventario = inventario;
		this.fecha = fecha;
		this.hora = hora;
		this.trabajador = trabajador;
		this.descripcion = descripcion;
	}
	
	//obligado todo getter y setter
	public String getInventario() {
		return inventario;
	}

	public void setInventario(String inventario) {
		this.inventario = inventario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(String trabajador) {
		this.trabajador = trabajador;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public OrdenTrabajo getOrden() {
		return orden;
	}

	public void setOrden(OrdenTrabajo orden) {
		this.orden = orden;
	}  

	//esto es un callBack, existen varios tipos de callBacks, ver parte III del libro de JPA
	//esta permite eliminar las dependencias que tiene la interrupcion con departamento en la tabla
	//donde se establecen las relaciones en la base de datos.
	@PreRemove
	public void eliminarRelacion(){
		ControladorJPAGenerico controller=new ControladorJPAGenericoImpl();
		if(departamento!=null){
		departamento.getInterrupciones().remove(this);
		try {
			controller.actualizar(departamento);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		if(orden!=null){
			try {
				controller.eliminar(orden.getId(), OrdenTrabajo.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
		
}
