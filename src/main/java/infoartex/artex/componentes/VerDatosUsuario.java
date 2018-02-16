package infoartex.artex.componentes;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Usuario;
import infoartex.artex.vistas.Administrar;


@SuppressWarnings("serial")
public class VerDatosUsuario extends ComponenteGenerico{
	private final CustomLayout main;
	private Usuario usuario;

	public VerDatosUsuario(Administrar view) {
		super(view);
		main = new CustomLayout("formulario");
		usuario=new Usuario();
	}

	public void initComponents(Administrar view) {
		main.removeAllComponents();
		Label nombreFormulario=new Label("Ver datos del usuario");
		nombreFormulario.setStyleName("text-left encabezado");
		main.addComponent(nombreFormulario, "nombreFormulario");
		Panel datosBasico = new Panel("Datos del usuario");
        datosBasico.setSizeUndefined();
        final FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
		Label usuarioN=new Label("Usuario: "+ usuario.getUsuario());
		Label nombre=new Label("Nombre y Apellidos: "+ usuario.getNombre()+" "+usuario.getApellidos());
		Label email=new Label("Correo electrónico: "+ usuario.getEmail());
		Label cargo=new Label("Cargo: "+ usuario.getCargo());
		Label rol=new Label("Cargo: "+ usuario.getRol());
		formularioBasico.addComponents(usuarioN,nombre,email,cargo,rol);
		datosBasico.setContent(formularioBasico);
		usuarioN.setStyleName("label label-info");
		datosBasico.setWidth("100%");
		main.addComponent(datosBasico,"formulario");
		setCompositionRoot(main);
		
	}
	
	public void setEntidad(Entidad entidad){
		this.usuario=(Usuario)entidad;
	}

}
