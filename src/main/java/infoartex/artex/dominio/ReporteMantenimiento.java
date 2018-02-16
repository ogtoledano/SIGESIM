package infoartex.artex.dominio;

import infoartex.artex.controladorJPA.ControladorJPAGenerico;
import infoartex.artex.controladorJPA.impl.ControladorJPAGenericoImpl;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class ReporteMantenimiento extends Entidad {

	@NotNull(message = "La fecha no puede ser vacía")
	@Temporal(TemporalType.DATE)
	public Date fecha;

	@NotBlank(message = "El tipo de medio no puede ser vacío")
	public String tipomedio;

	@NotBlank(message = "El inventario no puede ser vacío")
	@Pattern(regexp="[0-9]+(-)([0-9]|[a-z]|[A-Z])+",message="Debe estar en el formato <centro costo>-<numeros o letras>")
	public String inventario;

	@NotBlank(message = "El campo observación no puede ser vacío")
	public String observacion;

	@ManyToOne
	public Departamento departamento;

	private String usuario;

	@OneToOne(mappedBy = "reporte")
	public Mantenimiento mantenimiento;

	public ReporteMantenimiento() {

	}

	public ReporteMantenimiento(Date fecha, String tipomedio, String inventario, String observacion) {
		super();
		this.fecha = fecha;
		this.tipomedio = tipomedio;
		this.inventario = inventario;
		this.observacion = observacion;
	}

	public Mantenimiento getMantenimiento() {
		return mantenimiento;
	}

	public void setMantenimiento(Mantenimiento mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipomedio() {
		return tipomedio;
	}

	public void setTipomedio(String tipomedio) {
		this.tipomedio = tipomedio;
	}

	public String getInventario() {
		return inventario;
	}

	public void setInventario(String inventario) {
		this.inventario = inventario;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@PreRemove
	public void eliminarRelacion() {
		ControladorJPAGenerico controller = new ControladorJPAGenericoImpl();
		if (departamento != null) {
			departamento.getRepomantenimiento().remove(this);
			try {
				controller.actualizar(departamento);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(mantenimiento!=null){
			mantenimiento.setReporte(null);
			try {
				controller.actualizar(mantenimiento);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}
