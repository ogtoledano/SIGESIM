package infoartex.artex.componentes;

import infoartex.artex.dominio.OrdenTrabajo;
import infoartex.artex.vistas.Administrar;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class ListarOrdenesTrabajo extends ComponenteGenerico{
	
	private final CustomLayout container;
	private ListaPaginada<OrdenTrabajo> listaPaginada;
	
	public ListarOrdenesTrabajo(Administrar view) {
		super(view);
		container=new CustomLayout("formulario");
		listaPaginada=new ListaPaginada<>(view, 10, 200, OrdenTrabajo.class);
		initComponents(view);
	}

	public void initComponents(Administrar view) {
		listaPaginada.addColumnas("Número","Fecha","Inventario","Tipo de medio","Dictamen","Fecha de salida","Estado");
		listaPaginada.addCampos("numero");
		listaPaginada.addCampos("fecha");
		listaPaginada.addCampos("inventario");
		listaPaginada.addCampos("tipomedio");
		listaPaginada.addCampos("dictamen");
		listaPaginada.addCampos("fechasalida");
		listaPaginada.addCampos("estado");
		Label nombreForm=new Label("Listar órdenes de trabajo");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm,"nombreFormulario");
		listaPaginada.initComponent(view);
		listaPaginada.setVerEntidad(new VerDatosOrdenTrabajo(view));
		listaPaginada.setEditorEntidad(new EditarOrdenTrabajo(view));
		container.addComponent(listaPaginada,"formulario");
		setCompositionRoot(container);
	}

}
