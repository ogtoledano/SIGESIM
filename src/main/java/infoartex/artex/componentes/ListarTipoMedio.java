package infoartex.artex.componentes;
import infoartex.artex.dominio.TipoMedio;
import infoartex.artex.vistas.Administrar;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class ListarTipoMedio extends ComponenteGenerico {
	private final CustomLayout container;
	private ListaPaginada<TipoMedio> listaPaginada;
	public ListarTipoMedio(final Administrar view) {
		super(view);
		container=new CustomLayout("formulario");
		listaPaginada=new ListaPaginada<>(view, 10, 200, TipoMedio.class);
		initComponents(view);
	}
	
	private void initComponents(Administrar view) {
		listaPaginada.addColumnas("Denominación");
		listaPaginada.addCampos("denominacion");
		listaPaginada.initComponent(view);
		Label nombreForm=new Label("Listar tipos de medios");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm,"nombreFormulario");
		container.addComponent(listaPaginada,"formulario");
		//aqui le pones el componente que sirve para ver la entidad cualquiera
	    listaPaginada.setVerEntidad(new VerDatosTipoMedio(view));
		        //editar piezas recuperadas
		listaPaginada.setEditorEntidad(new EditarTipoMedio(view));
		setCompositionRoot(container);	
	}
}
