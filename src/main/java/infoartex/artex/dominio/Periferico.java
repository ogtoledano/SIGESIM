package infoartex.artex.dominio;

public class Periferico {
	private String perif;
	private String modelo;
	private String serie;
	private String inventario;
	
	
	
	public Periferico(String perif, String modelo, String serie,
			String inventario) {
		this.perif = perif;
		this.modelo = modelo;
		this.serie = serie;
		this.inventario = inventario;
	}
	
	public String getPerif() {
		return perif;
	}
	public void setPeriferico(String perif) {
		this.perif = perif;
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
	public String getInventario() {
		return inventario;
	}
	public void setInventario(String inventario) {
		this.inventario = inventario;
	}
	
	
}
