package infoartex.artex.dominio;

import infoartex.artex.controladorJPA.ControladorJPAGenerico;
import infoartex.artex.controladorJPA.impl.ControladorJPAGenericoImpl;

import javax.persistence.Entity;
import javax.persistence.PreRemove;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
public class Laptop extends PC{
	
	@NotBlank(message = "El campo inventario no puede ser vacío")
	@Pattern(regexp="[0-9]+(-)([0-9]|[a-z]|[A-Z])+",message="Debe estar en el formato <centro costo>-<numeros o letras>")
	public String inventario;
	@NotBlank(message = "El campo modelo no puede ser vacío")
	public String modelo;
	@NotBlank(message = "El campo del número de serie no puede ser vacío")
	public String serie;
	@NotBlank(message = "El campo del modelo de la placa no puede ser vacío")
	public String modeloplaca;
	@NotBlank(message = "El campo del procesador no puede ser vacío")
	public String procesador;
	@NotBlank(message = "El campo de la capacidad de la RAM ser vacío")
	@Pattern(regexp="[0-9]{1,10}",message="Deben ser números")
	public String capram;
	@NotBlank(message = "El campo del tipo de RAM no puede ser vacío")
	public String tiporam;
	@NotBlank(message = "El campo de la marca del HDD no puede ser vacío")
	public String marcahdd;
	@NotBlank(message = "El campo de la capacidad del HDD no puede ser vacío")
	@Pattern(regexp="[0-9]{1,10}",message="Deben ser números")
	public String caphdd;
	@NotBlank(message = "El campo de la unidad óptica no puede ser vacío")
	public String optica;
	@NotBlank(message = "El campo del display  no puede ser vacío")
	public String display;
	@NotBlank(message = "El campo del adaptador de red  no puede ser vacío")
	public String red;
	
	
	public Laptop() {
	}

	public Laptop(String nombre, String responsable,String inventario, String modelo, String serie,
			String modeloplaca, String procesador, String capram, String tiporam,
			String marcahdd, String caphdd, String optica, String display,
			String red) {
		super(nombre,responsable);
		this.inventario = inventario;
		this.modelo = modelo;
		this.serie = serie;
		this.modeloplaca = modeloplaca;
		this.procesador = procesador;
		this.capram = capram;
		this.tiporam = tiporam;
		this.marcahdd = marcahdd;
		this.caphdd = caphdd;
		this.optica = optica;
		this.display = display;
		this.red = red;
	}

	public String getInventario() {
		return inventario;
	}
	
	public void setInventario(String inventario) {
		this.inventario = inventario;
	}


	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getSerie() {
		return serie;
	}


	public void setSerie(String serie) {
		this.serie = serie;
	}


	public String getModeloplaca() {
		return modeloplaca;
	}


	public void setModeloplaca(String modeloplaca) {
		this.modeloplaca = modeloplaca;
	}


	public String getProcesador() {
		return procesador;
	}


	public void setProcesador(String procesador) {
		this.procesador = procesador;
	}


	public String getCapram() {
		return capram;
	}


	public void setCapram(String capram) {
		this.capram = capram;
	}


	public String getTiporam() {
		return tiporam;
	}


	public void setTiporam(String tiporam) {
		this.tiporam = tiporam;
	}


	public String getMarcahdd() {
		return marcahdd;
	}


	public void setMarcahdd(String marcahdd) {
		this.marcahdd = marcahdd;
	}


	public String getCaphdd() {
		return caphdd;
	}


	public void setCaphdd(String caphdd) {
		this.caphdd = caphdd;
	}


	public String getOptica() {
		return optica;
	}


	public void setOptica(String optica) {
		this.optica = optica;
	}


	public String getDisplay() {
		return display;
	}


	public void setDisplay(String display) {
		this.display = display;
	}


	public String getRed() {
		return red;
	}


	public void setRed(String red) {
		this.red = red;
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

}
