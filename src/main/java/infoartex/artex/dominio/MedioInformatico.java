package infoartex.artex.dominio;

import java.util.LinkedList;
import java.util.List;

import infoartex.artex.controladorJPA.ControladorJPAGenerico;
import infoartex.artex.controladorJPA.impl.ControladorJPAGenericoImpl;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

//obligado
@SuppressWarnings("serial")
@Entity
public class MedioInformatico extends Entidad{
	@NotBlank(message = "El campo del inventario no puede ser vacío")
	@Pattern(regexp="[0-9]+(-)([0-9]|[a-z]|[A-Z])+",message="Debe estar en el formato <centro costo>-<numeros o letras>")
	public String inventario;
	@NotBlank(message = "El campo del tipo no puede ser vacío")
	public String tipo;
	@NotBlank(message = "El campo de la marca no puede ser vacío")
	public String marca;
	@NotBlank(message = "El campo del modelo no puede ser vacío")
	public String modelo;
	@ManyToOne
	protected PC computadora;
	protected String nombComputadora;
	public String estado;
	
	@ManyToOne
	public Departamento departamento;
	
	@OneToMany(orphanRemoval=true)
	private List<Traslado> historial;

    public MedioInformatico(){
		
	     }
    
	public MedioInformatico(String inventario, String tipo, String marca,
			String modelo, String estado) {
		super();
		this.inventario = inventario;
		this.tipo = tipo;
		this.marca = marca;
		this.modelo = modelo;
		this.estado = estado;
		this.nombComputadora="Sin asignar";
		this.historial=new LinkedList<>();
	}

	public String getInventario() {
		return inventario;
	}

	public void setInventario(String inventario) {
		this.inventario = inventario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public PC getComputadora() {
		return computadora;
	}

	public void setComputadora(PC computadora) {
		this.computadora = computadora;
	}

	public String getNombComputadora() {
		return nombComputadora;
	}

	public void setNombComputadora(String nombComputadora) {
		this.nombComputadora = nombComputadora;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public List<Traslado> getHistorial() {
		return historial;
	}

	public void setHistorial(List<Traslado> historial) {
		this.historial = historial;
	}

	//Remueve dependencias en la BD en caso de borrar el medio informatico
	@PreRemove
	public void eliminarRelacion(){
		if(departamento!=null){
		ControladorJPAGenerico controller=new ControladorJPAGenericoImpl();
		departamento.getMedio().remove(this);
		try {
			controller.actualizar(departamento);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
	
}
