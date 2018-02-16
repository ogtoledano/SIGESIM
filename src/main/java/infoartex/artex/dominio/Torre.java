package infoartex.artex.dominio;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
public class Torre extends MedioInformatico{
	
	@NotBlank(message = "El campo de la fuente no puede ser vacía")
	@Pattern(regexp="[0-9]{1,10}",message="Deben ser números")
	private String fuente;
	@NotBlank(message = "El campo de la motherboard no puede ser vacío")
	private String board;
	@NotBlank(message = "El campo del procesador no puede ser vacío")
	private String procesador;
	@NotBlank(message = "El campo de la capacidad de la memoria RAM no puede ser vacío")
	@Pattern(regexp="[0-9]{1,10}",message="Deben ser números")
	private String ram;
	@NotBlank(message = "El campo del tipo de memoria RAM no puede ser vacío")
	private String tiporam;
	@NotBlank(message = "El campo del HDD no puede ser vacío")
	@Pattern(regexp="[0-9]{1,10}",message="Deben ser números")
	private String hdd;
	@NotBlank(message = "El campo del tipo de HDD no puede ser vacío")
	private String marcahdd;
	@NotBlank(message = "El inventario no puede ser vacío")
	private String optica;
	@NotBlank(message = "El campo del adaptador de red no puede ser vacío")
	private String red;
	@NotBlank(message = "El campo de la dirección MAC no puede ser vacío")
	private String mac;
	@NotBlank(message = "El campo de otros no puede ser vacío")
	private String otros;
	
	public Torre() {
	}

	public Torre(String inventario, String tipo, String marca,
			String modelo, String estado,String fuente, String board, String procesador, String ram,
			String tiporam, String hdd, String marcahdd, String optica,
			String red, String mac, String otros) {
		super(inventario,tipo,marca,modelo,estado);
		this.fuente = fuente;
		this.board = board;
		this.procesador = procesador;
		this.ram = ram;
		this.tiporam = tiporam;
		this.hdd = hdd;
		this.marcahdd = marcahdd;
		this.optica = optica;
		this.red = red;
		this.mac = mac;
		this.otros = otros;
	}

	public String getFuente() {
		return fuente;
	}


	public void setFuente(String fuente) {
		this.fuente = fuente;
	}


	public String getBoard() {
		return board;
	}


	public void setBoard(String board) {
		this.board = board;
	}


	public String getProcesador() {
		return procesador;
	}


	public void setProcesador(String procesador) {
		this.procesador = procesador;
	}


	public String getRam() {
		return ram;
	}


	public void setRam(String ram) {
		this.ram = ram;
	}


	public String getTiporam() {
		return tiporam;
	}


	public void setTiporam(String tiporam) {
		this.tiporam = tiporam;
	}


	public String getHdd() {
		return hdd;
	}


	public void setHdd(String hdd) {
		this.hdd = hdd;
	}


	public String getMarcahdd() {
		return marcahdd;
	}


	public void setMarcahdd(String marcahdd) {
		this.marcahdd = marcahdd;
	}


	public String getOptica() {
		return optica;
	}


	public void setOptica(String optica) {
		this.optica = optica;
	}


	public String getRed() {
		return red;
	}


	public void setRed(String red) {
		this.red = red;
	}


	public String getMac() {
		return mac;
	}


	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getOtros() {
		return otros;
	}


	public void setOtros(String otros) {
		this.otros = otros;
	}



}
