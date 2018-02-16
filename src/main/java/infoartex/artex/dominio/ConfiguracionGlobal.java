package infoartex.artex.dominio;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
@SuppressWarnings("serial")
@Entity
public class ConfiguracionGlobal extends Entidad{
	@NotBlank(message="El nombre la sucursal no puede ser vacío")
	public String nombreSucursal;
	@NotBlank(message="El Centro de Costo de la Sucursal no puede ser vacío")
	public String centroCostoSucursal;

	public ConfiguracionGlobal() {
		
	}
	
	public ConfiguracionGlobal(String nombreSucursal, String centroCostoSucursal) {
		super();
		this.nombreSucursal = nombreSucursal;
		this.centroCostoSucursal = centroCostoSucursal;
	}

	public String getCentroCostoSucursal() {
		return centroCostoSucursal;
	}

	public void setCentroCostoSucursal(String centroCostoSucursal) {
		this.centroCostoSucursal = centroCostoSucursal;
	}

	public String getNombreSucursal() {
		return nombreSucursal;
	}

	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}
	
}
