package infoartex.artex.componentes;
import infoartex.artex.dominio.PC;
import infoartex.artex.vistas.Administrar;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class ListarPC extends ComponenteGenerico{
	private final CustomLayout container;
	private ListaPaginada<PC> listaPaginada;
	
	public ListarPC(final Administrar view) {
		super(view);
		container=new CustomLayout("formulario");
		listaPaginada=new ListaPaginadaPC(view, 10, 200, PC.class);
		initComponents(view);
	}

	private void initComponents(final Administrar view) {
		listaPaginada.addColumnas("Denominación","Responsable");
		listaPaginada.addCampos("nombre");
		listaPaginada.addCampos("responsable");
		Label nombreForm=new Label("Listar PC");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm,"nombreFormulario");
		listaPaginada.initComponent(view);
		listaPaginada.setVerEntidad(new VerDatosPC(view));
		listaPaginada.setEditorEntidad(new EditarDatosPC(view));
		container.addComponent(listaPaginada,"formulario");
		setCompositionRoot(container);
		
	}
}
