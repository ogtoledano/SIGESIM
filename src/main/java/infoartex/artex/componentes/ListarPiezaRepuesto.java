package infoartex.artex.componentes;

import infoartex.artex.dominio.PiezaRepuesto;
import infoartex.artex.vistas.Administrar;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class ListarPiezaRepuesto extends ComponenteGenerico{
	private final CustomLayout container;
	private ListaPaginada<PiezaRepuesto> listaPaginada;
	
	public ListarPiezaRepuesto(final Administrar view) {
		super(view);
		container=new CustomLayout("formulario");
		listaPaginada=new ListaPaginada<>(view, 10, 200, PiezaRepuesto.class);
		initComponents(view);
	}
	
	private void initComponents(Administrar view) {
		listaPaginada.addColumnas("Código","Factura","Tipo","Cantidad","No. Orden");
		//Lo que deseo ver de la lista, 
		listaPaginada.addCampos("codigo");
		listaPaginada.addCampos("factura");
		listaPaginada.addCampos("tipo");
		listaPaginada.addCampos("cantidad");
		listaPaginada.addCampos("noOrden");
		Label nombreForm=new Label("Listar pieza de repuesto");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm,"nombreFormulario");
		listaPaginada.initComponent(view);
		//aqui le pones el componente que sirve para ver la entidad cualquiera
		listaPaginada.setVerEntidad(new VerDatoPiezaRepuesto(view));
		listaPaginada.setEditorEntidad(new EditarPiezaRepuesto(view));
		container.addComponent(listaPaginada,"formulario");
		setCompositionRoot(container);
	}
}
