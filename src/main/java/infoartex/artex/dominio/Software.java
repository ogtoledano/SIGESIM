package infoartex.artex.dominio;

public class Software {
	private String noOrden;
	private String soft;
	private String nombre;
	private String fecha;

	public Software(String noOrden, String soft, String nombre, String fecha) {
		super();
		this.noOrden = noOrden;
		this.soft = soft;
		this.nombre = nombre;
		this.fecha = fecha;
	}
	
	public String getNoOrden() {
		return noOrden;
	}
	public void setNoOrden(String noOrden) {
		this.noOrden = noOrden;
	}
	public String getSoft() {
		return soft;
	}
	public void setSoft(String soft) {
		this.soft = soft;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
}
