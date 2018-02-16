package infoartex.artex.componentes;


import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;

import infoartex.artex.dominio.Interrupcion;
import infoartex.artex.vistas.Administrar;


@SuppressWarnings("serial")
public class ListarInterrupciones extends ComponenteGenerico{

	private final CustomLayout container;
	private ListaPaginada<Interrupcion> listaPaginada;
	
	public ListarInterrupciones(final Administrar view) {
		super(view);
		container=new CustomLayout("formulario");
		listaPaginada=new ListaPaginadaInterrupcion(view, 10, 200, Interrupcion.class);
		initComponents(view);
	}

	private void initComponents(final Administrar view) {
		listaPaginada.addColumnas("Inventario","Fecha","Hora","Trabajador");
		listaPaginada.addCampos("inventario");
		listaPaginada.addCampos("fecha");
		listaPaginada.addCampos("hora");
		listaPaginada.addCampos("trabajador");
		listaPaginada.addCampos("estado");
		Label nombreForm=new Label("Listar interrupciones");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm,"nombreFormulario");
		listaPaginada.initComponent(view);
		//especificarle que componente se encargara de editar la entidad
		listaPaginada.setEditorEntidad(new EditarInterrupcion(view));
		//especificarle que componente se encargara de mostrar los datos de la entidad
		listaPaginada.setVerEntidad(new VerDatosInterrupcion(view));
		container.addComponent(listaPaginada,"formulario");
		setCompositionRoot(container);
	}
}
