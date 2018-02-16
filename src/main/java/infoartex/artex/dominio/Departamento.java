package infoartex.artex.dominio;

import infoartex.artex.controladorJPA.ControladorJPAGenerico;
import infoartex.artex.controladorJPA.impl.ControladorJPAGenericoImpl;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
public class Departamento extends Entidad{
	@NotBlank(message = "El nombre no puede ser vacío")
	public String nombre;
	@NotBlank(message = "La gerencia no puede estar vacía")
	public String gerencia;
	@NotBlank(message = "El centro de costo no puede estar vacía")
	public String ccosto;
	@OneToMany
	private List<Interrupcion> interrupciones;
	@OneToMany
	private List<ReporteMantenimiento> repomantenimiento;
	@OneToMany
	private List<Mantenimiento> mantenimientos;
	@OneToMany
	private List<PC> computadoras;
	@OneToMany
	private List<MedioInformatico> medio;
	public Departamento(){
		
	}
	
	public Departamento(String nombre, String gerencia,String ccosto,
			List<Interrupcion> interrupciones,
			List<ReporteMantenimiento> repomantenimiento,
			List<Mantenimiento> mantenimientos, List<PC> computadoras,
			List<MedioInformatico> medio) {
		super();
		this.nombre = nombre;
		this.gerencia = gerencia;
		this.ccosto = ccosto;
		this.interrupciones = interrupciones;
		this.repomantenimiento = repomantenimiento;
		this.mantenimientos = mantenimientos;
		this.computadoras = computadoras;
		this.medio = medio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCcosto() {
		return ccosto;
	}

	public void setCcosto(String ccosto) {
		this.ccosto = ccosto;
	}

	public String getGerencia() {
		return gerencia;
	}

	public void setGerencia(String gerencia) {
		this.gerencia = gerencia;
	}

	public List<Interrupcion> getInterrupciones() {
		return interrupciones;
	}

	public void setInterrupciones(List<Interrupcion> interrupciones) {
		this.interrupciones = interrupciones;
	}

	public List<ReporteMantenimiento> getRepomantenimiento() {
		return repomantenimiento;
	}

	public void setRepomantenimiento(List<ReporteMantenimiento> repomantenimiento) {
		this.repomantenimiento = repomantenimiento;
	}

	public List<Mantenimiento> getMantenimientos() {
		return mantenimientos;
	}

	public void setMantenimientos(List<Mantenimiento> mantenimientos) {
		this.mantenimientos = mantenimientos;
	}

	public List<PC> getComputadoras() {
		return computadoras;
	}

	public void setComputadoras(List<PC> computadoras) {
		this.computadoras = computadoras;
	}

	public List<MedioInformatico> getMedio() {
		return medio;
	}

	public void setMedio(List<MedioInformatico> medio) {
		this.medio = medio;
	}

	//otro call back
	@PreRemove
	public void eliminarRelaciones() {
		ControladorJPAGenerico controller=new ControladorJPAGenericoImpl();
		List<Interrupcion> lista=getInterrupciones();
		for(Interrupcion i:lista){
			i.setDepartamento(null);
		}
		List<MedioInformatico> medio=getMedio();
		for(MedioInformatico m:medio){
			m.setDepartamento(null);
		}
		try {
			controller.actualizar(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int interrupcionesXDia(String fecha) {
		int cant=0;
		for(Interrupcion i:interrupciones){
			SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
			if(format.format(i.getFecha()).equals(fecha)){
				cant++;
			}
		}
		return cant;
	}
	@Override
	public String toString(){
		return this.nombre;
	}
	
}
