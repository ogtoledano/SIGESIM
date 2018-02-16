package infoartex.artex.dominio;

import infoartex.artex.controladorJPA.ControladorJPAGenerico;
import infoartex.artex.controladorJPA.impl.ControladorJPAGenericoImpl;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

import org.hibernate.validator.constraints.NotBlank;
 
//obligado
@SuppressWarnings("serial")
@Entity
public class PC extends Entidad {
	
	@NotBlank(message = "El campo del nombre no puede ser vacío")
	public String nombre;
	@NotBlank(message = "El campo del responsable no puede ser vacío")
	public String responsable;
	@OneToMany
	protected List<MedioInformatico> medios;
	@ManyToOne
	protected Departamento departamento;
	@OneToMany(cascade=CascadeType.MERGE)
	protected List<Mantenimiento> mantenimientosEfectuados;
	
	 public PC () {
	    }

	public PC(String nombre, String responsable) {
		super();
		this.nombre = nombre;
		this.responsable = responsable;
		this.medios=new LinkedList<>();
		this.mantenimientosEfectuados=new LinkedList<>();
	}

	public List<Mantenimiento> getMantenimientosEfectuados() {
		return mantenimientosEfectuados;
	}

	public void setMantenimientosEfectuados(
			List<Mantenimiento> mantenimientosEfectuados) {
		this.mantenimientosEfectuados = mantenimientosEfectuados;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public List<MedioInformatico> getMedios() {
		return medios;
	}

	public void setMedios(List<MedioInformatico> medios) {
		this.medios = medios;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@PreRemove
	public void eliminarRelacion(){
		try {
			ControladorJPAGenerico controller=new ControladorJPAGenericoImpl();
			for(MedioInformatico m:medios){
				m.setNombComputadora("Sin asignar");
				m.setComputadora(null);
				controller.actualizar(m);
			}
			if(departamento!=null){
				departamento.getComputadoras().remove(this);
				controller.actualizar(departamento);
			}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	public Torre obtenerTorre(){
		boolean enc=false;
		int i=0;
		while(i<medios.size()&&!enc){
			if(medios.get(i) instanceof Torre){
				enc=true;
			}else{
				i++;
			}
		}
		return enc?(Torre)medios.get(i):null;
	}
		
}
