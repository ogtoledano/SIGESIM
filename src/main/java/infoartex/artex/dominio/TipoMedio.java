package infoartex.artex.dominio;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
public class TipoMedio extends Entidad{
	@NotBlank(message = "La denominación no puede ser vacía")
	public String denominacion;
	
	public TipoMedio() {
	}

	public TipoMedio(String denominacion) {
		super();
		this.denominacion = denominacion;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	
	
}
