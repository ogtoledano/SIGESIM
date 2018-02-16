package infoartex.artex.componentes;

import infoartex.artex.dominio.PiezaRecuperada;
import infoartex.artex.vistas.Administrar;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class ListarPiezaRecuperada extends ComponenteGenerico{
	private final CustomLayout container;
	private ListaPaginada<PiezaRecuperada> listaPaginada;
	
	public ListarPiezaRecuperada(final Administrar view) {
		super(view);
		container=new CustomLayout("formulario");
		listaPaginada=new ListaPaginada<>(view, 10, 200, PiezaRecuperada.class);
		initComponents(view);
	}
	
	private void initComponents(Administrar view) {
		listaPaginada.addColumnas("Inventario proveniente","Pieza","No Orden");
		//Lo que deseo ver de la lista, 
		listaPaginada.addCampos("inventario");
		listaPaginada.addCampos("tipo");
		listaPaginada.addCampos("noOrden");
		Label nombreForm=new Label("Listar pieza recuperada");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm,"nombreFormulario");
		listaPaginada.initComponent(view);
		//aqui le pones el componente que sirve para ver la entidad cualquiera
				listaPaginada.setVerEntidad(new VerDatoPiezaRecuperada(view));
	//editar piezas recuperadas
	listaPaginada.setEditorEntidad(new EditarPiezaRecuperada(view));
	
		
		container.addComponent(listaPaginada,"formulario");
		setCompositionRoot(container);
	}
}
