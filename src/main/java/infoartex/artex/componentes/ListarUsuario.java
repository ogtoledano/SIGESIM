package infoartex.artex.componentes;

import infoartex.artex.dominio.Usuario;
import infoartex.artex.vistas.Administrar;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class ListarUsuario extends ComponenteGenerico{
	private final CustomLayout container;
	private ListaPaginada<Usuario> listaPaginada;
	
	public ListarUsuario(final Administrar view) {
		super(view);
		container=new CustomLayout("formulario");
		listaPaginada=new ListaPaginada<>(view, 10, 200, Usuario.class);
		initComponents(view);
	}

	private void initComponents(Administrar view) {
		listaPaginada.addColumnas("Usuario","Nombre","Apellidos","E-mail","Cargo");
		//ojo con la fecha!!!!!!
		//listaPaginada.addCampos("fecha");
		listaPaginada.addCampos("usuario");
		listaPaginada.addCampos("nombre");
		listaPaginada.addCampos("apellidos");
		listaPaginada.addCampos("email");
		listaPaginada.addCampos("cargo");
		Label nombreForm=new Label("Listar usuarios");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm,"nombreFormulario");
		listaPaginada.initComponent(view);
		//especificarle que componente se encargara de editar la entidad
		listaPaginada.setEditorEntidad(new EditarPerfil(view, ""));
		//especificarle que componente se encargara de mostrar los datos de la entidad
		listaPaginada.setVerEntidad(new VerDatosUsuario(view));
		container.addComponent(listaPaginada,"formulario");
		setCompositionRoot(container);
	}
}
