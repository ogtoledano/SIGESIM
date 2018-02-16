package infoartex.artex.dominio;

import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Sesion extends Entidad{
	private String usuario;
	
	public Sesion() {
		
	}

	public Sesion(String usuario) {
		super();
		this.usuario = usuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	
}
