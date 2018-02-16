package infoartex.artex.componentes;

import infoartex.artex.dominio.Departamento;
import infoartex.artex.vistas.Administrar;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class ListarDepartamento extends ComponenteGenerico{

	private final CustomLayout container;
	private ListaPaginada<Departamento> listaPaginada;
	public ListarDepartamento(final Administrar view) {
		super(view);
		container=new CustomLayout("formulario");
		listaPaginada=new ListaPaginada<>(view, 10, 200, Departamento.class);
		initComponents(view);
	}
	
	private void initComponents(Administrar view) {
		listaPaginada.addColumnas("Nombre","Dependencia");
		listaPaginada.addCampos("nombre");
		listaPaginada.addCampos("gerencia");
		listaPaginada.initComponent(view);
		Label nombreForm=new Label("Listar departamentos");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm,"nombreFormulario");
		container.addComponent(listaPaginada,"formulario");
		//aqui le pones el componente que sirve para ver la entidad cualquiera
				listaPaginada.setVerEntidad(new VerDatoDepartamento(view));
		        //editar piezas recuperadas
		       listaPaginada.setEditorEntidad(new EditarDepartamento(view));
		setCompositionRoot(container);
		
	}

}
