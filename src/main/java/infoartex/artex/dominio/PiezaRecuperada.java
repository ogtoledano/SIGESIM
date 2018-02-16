package infoartex.artex.dominio;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
public class PiezaRecuperada extends Entidad {

	@NotBlank(message = "El campo inventario no puede ser vacío")
	@Pattern(regexp="[0-9]+(-)([0-9]|[a-z]|[A-Z])+",message="Debe estar en el formato <centro costo>-<numeros o letras>")
	public String inventario;
	@NotBlank(message = "El campo tipo no puede ser vacío")
	public String tipo;
	@NotBlank(message = "El campo detalles no puede ser vacío")
	public String detalles;
	
	@ManyToOne
	public OrdenTrabajo orden;
	
	public String noOrden;
	
 public PiezaRecuperada () {

	}

public PiezaRecuperada(String inventario, String tipo, int cantidad, String detalles) {
	super();
	this.inventario = inventario;
	this.tipo = tipo;
	this.detalles = detalles;
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

public String getDetalles() {
	return detalles;
}

public void setDetalles(String detalles) {
	this.detalles = detalles;
}

public void setTipo(String tipo) {
	this.tipo = tipo;
}

public OrdenTrabajo getOrden() {
	return orden;
}

public void setOrden(OrdenTrabajo orden) {
	this.orden = orden;
}

public String getNoOrden() {
	return noOrden;
}

public void setNoOrden(String noOrden) {
	this.noOrden = noOrden;
}

public boolean equals(Object pieza){
	if(id!=null){
		return id.equals(((PiezaRecuperada)pieza).id);
	}else{
		return false;
	}
}

}
