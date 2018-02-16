package infoartex.artex.dominio;

import infoartex.artex.controladorJPA.ControladorJPAGenerico;
import infoartex.artex.controladorJPA.impl.ControladorJPAGenericoImpl;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
public class Mantenimiento extends Entidad {

	@NotBlank(message = "El campo del No.Consecutivo no puede ser vacío")
	public String no;
	@NotBlank(message = "El campo del mes no puede ser vacío")
	public String mes;
	@NotBlank(message = "El campo del equipo no puede ser vacío")
	public String equipo;
	@ManyToOne
	public Departamento departamento;

	private String usuario;

	@NotBlank(message = "El campo del inventario no puede ser vacío")
	@Pattern(regexp="[0-9]+(-)([0-9]|[a-z]|[A-Z])+",message="Debe estar en el formato <centro costo>-<numeros o letras>")
	public String inventario;

	public String estado;

	@OneToOne(orphanRemoval=true)
	public ReporteMantenimiento reporte;

	@ManyToOne
	public PC pcAsociada;

	public Mantenimiento() {

	}

	public Mantenimiento(String no, String mes, String equipo, String inventario) {
		super();
		this.no = no;
		this.mes = mes;
		this.equipo = equipo;
		this.inventario = inventario;
		this.estado = "Programado";
	}

	public ReporteMantenimiento getReporte() {
		return reporte;
	}

	public void setReporte(ReporteMantenimiento reporte) {
		this.reporte = reporte;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getInventario() {
		return inventario;
	}

	public void setInventario(String inventario) {
		this.inventario = inventario;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@PreRemove
	public void eliminarRelacion() {
		ControladorJPAGenerico controller = new ControladorJPAGenericoImpl();
		if (departamento != null) {
			departamento.getMantenimientos().remove(this);
		}
		if (pcAsociada != null) {
			pcAsociada.getMantenimientosEfectuados().remove(this);
		}
		try {
			if (departamento != null) {
				controller.actualizar(departamento);
			}
			if (pcAsociada != null) {
				controller.actualizar(pcAsociada);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public PC getPcAsociada() {
		return pcAsociada;
	}

	public void setPcAsociada(PC pcAsociada) {
		this.pcAsociada = pcAsociada;
	}

}
