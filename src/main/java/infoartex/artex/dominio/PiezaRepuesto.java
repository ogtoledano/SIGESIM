package infoartex.artex.dominio;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
public class PiezaRepuesto extends Entidad {

	@NotBlank(message = "El campo código no puede ser vacío")
	public String codigo;
	@NotBlank(message = "El campo factura no puede ser vacío")
	public String factura;
	@NotBlank(message = "El campo del tipo no puede ser vacío")
	public String tipo;
	@NotNull(message = "El cantidad del tipo no puede ser vacío")
	@Digits(integer = 10, fraction = 0, message = "Deben ser números")
	public int cantidad;
	@NotBlank(message = "El campo detalles no puede ser vacío")
	public String detalles;

	@ManyToOne
	public OrdenTrabajo orden;

	public String noOrden;

	public PiezaRepuesto() {

	}

	public PiezaRepuesto(String codigo, String factura, String tipo, int cantidad, String detalles) {
		super();
		this.codigo = codigo;
		this.factura = factura;
		this.tipo = tipo;
		this.cantidad = cantidad;
		this.detalles = detalles;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
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

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
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

	public boolean equals(Object pieza) {
		if (id != null) {
			return id.equals(((PiezaRepuesto) pieza).id);
		} else {
			return false;
		}
	}

}
