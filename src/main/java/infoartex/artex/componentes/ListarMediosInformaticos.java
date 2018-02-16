package infoartex.artex.componentes;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;

import infoartex.artex.dominio.MedioInformatico;
import infoartex.artex.vistas.Administrar;



@SuppressWarnings("serial")
public class ListarMediosInformaticos extends ComponenteGenerico{

	private final CustomLayout container;
	private ListaPaginada<MedioInformatico> listaPaginada;
	
	public ListarMediosInformaticos(final Administrar view) {
		super(view);
		container=new CustomLayout("formulario");
		listaPaginada=new ListaPaginadaMediosInformaticos(view, 10, 200, MedioInformatico.class);
		initComponents(view);
	}

	private void initComponents(final Administrar view) {
		listaPaginada.addColumnas("Inventario","Tipo de medio","Marca","Modelo");
		listaPaginada.addCampos("inventario");
		listaPaginada.addCampos("tipo");
		listaPaginada.addCampos("marca");
		listaPaginada.addCampos("modelo");
		Label nombreForm=new Label("Listar medios informáticos");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm,"nombreFormulario");
		listaPaginada.initComponent(view);
		listaPaginada.setVerEntidad(new VerDatosMediosInformaticos(view));
		listaPaginada.setEditorEntidad(new EditarMedioInformatico(view));
		container.addComponent(listaPaginada,"formulario");
		setCompositionRoot(container);
		
	}

}
