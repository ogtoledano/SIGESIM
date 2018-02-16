/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoartex.artex.dominio;

import infoartex.artex.controladorJPA.ControladorJPAGenerico;
import infoartex.artex.controladorJPA.impl.ControladorJPAGenericoImpl;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author administrador
 */
@SuppressWarnings("serial")
@Entity
public class Usuario extends Entidad{

	@NotBlank(message = "El usuario no puede ser vacío")
    public String usuario;
	@NotBlank(message = "El nombre no puede ser vacío")
	public String nombre;
	@NotBlank(message = "Los apellidos no pueden ser vacíos")
	public String apellidos;
	@NotBlank(message = "El correo no puede ser vacío")
    @Email(message = "Formato de correo incorrecto")
	public String email;
	@NotBlank(message = "La contraseña no puede ser vacía")
    private String password;
	@NotBlank(message = "El cargo no puede ser vacío")
	public String cargo;
	@ManyToOne
	private Rol rol;
    public Usuario() {
    }

    public Usuario(String usuario, String nombre, String apellidos, String email, String password, String cargo, Rol rol) {
        super();
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.cargo = cargo;
        this.rol= rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCargo() {
        return cargo;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	//esto es un callBack, existen varios tipos de callBacks, ver parte III del libro de JPA
		//esta permite eliminar las dependencias que tiene la interrupcion con departamento en la tabla
		//donde se establecen las relaciones en la base de datos.
		@PreRemove
		public void eliminarRelacion(){
			if(rol!=null){
			ControladorJPAGenerico controller=new ControladorJPAGenericoImpl();
			rol.getUsuarios().remove(this);
			try {
				controller.actualizar(rol);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
		}
}
