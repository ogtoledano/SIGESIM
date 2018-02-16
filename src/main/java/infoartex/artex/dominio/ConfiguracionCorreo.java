package infoartex.artex.dominio;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
public class ConfiguracionCorreo extends Entidad {
	@NotBlank(message = "El nombre no puede ser vacío")
	public String servidorSaliente;
	@NotBlank(message = "La dirección no puede ser vacía")
	public String direccion;
	@NotBlank(message = "La contraseña no puede ser vacía")
	public String password;
	@NotNull(message = "El puerto no puede ser vacio")
	@Min(0)
	@Max(65535)
	public int puerto;

	public boolean envioHabilitado;

	public ConfiguracionCorreo() {

	}

	public ConfiguracionCorreo(String servidorSaliente, String direccion,
			String password, int puerto) {
		super();
		this.servidorSaliente = servidorSaliente;
		this.direccion = direccion;
		this.password = password;
		this.puerto = puerto;
		this.envioHabilitado = false;
	}

	public String getServidorSaliente() {
		return servidorSaliente;
	}

	public void setServidorSaliente(String servidorSaliente) {
		this.servidorSaliente = servidorSaliente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public boolean isEnvioHabilitado() {
		return envioHabilitado;
	}

	public void setEnvioHabilitado(boolean envioHabilitado) {
		this.envioHabilitado = envioHabilitado;
	}
}
