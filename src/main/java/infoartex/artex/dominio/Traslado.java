package infoartex.artex.dominio;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
public class Traslado extends Entidad{
	
	
	@NotBlank(message = "El número consecutivo no puede ser vacío")
	public String numero;
	public String tipoTraslado;
	public String usuario;
	@Temporal(TemporalType.DATE)
	public Date fecha;
	public String hora;
	
	@NotBlank(message = "El campo trabajador no puede ser vacío")
	public String trabajadorentrega;
	@NotBlank(message = "El campo trabajador no puede ser vacío")
	public String trabajadorrecibe;
	
	public Traslado() {
		
	}

	public Traslado(String numero, String tipoTraslado, String usuario,
			Date fecha, String hora, String trabajadorentrega,
			String trabajadorrecibe) {
		super();
		this.numero = numero;
		this.tipoTraslado = tipoTraslado;
		this.usuario = usuario;
		this.fecha = fecha;
		this.hora = hora;
		this.trabajadorentrega = trabajadorentrega;
		this.trabajadorrecibe = trabajadorrecibe;
	}



	public String getNumero() {
		return numero;
	}



	public void setNumero(String numero) {
		this.numero = numero;
	}



	public String getTrabajadorentrega() {
		return trabajadorentrega;
	}



	public void setTrabajadorentrega(String trabajadorentrega) {
		this.trabajadorentrega = trabajadorentrega;
	}



	public String getTrabajadorrecibe() {
		return trabajadorrecibe;
	}



	public void setTrabajadorrecibe(String trabajadorrecibe) {
		this.trabajadorrecibe = trabajadorrecibe;
	}



	public String getTipoTraslado() {
		return tipoTraslado;
	}

	public void setTipoTraslado(String tipoTraslado) {
		this.tipoTraslado = tipoTraslado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	
}
