package infoartex.artex.componentes;

import infoartex.artex.dominio.Mantenimiento;
import infoartex.artex.vistas.Administrar;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class ListarMantenimientosProg extends ComponenteGenerico {
	
	private final CustomLayout container;
	private ListaPaginadaMantenimientos listaPaginada;
	
	public ListarMantenimientosProg(final Administrar view) {
		super(view);
		container=new CustomLayout("formulario");
		listaPaginada=new ListaPaginadaMantenimientos(view, 10, 200, Mantenimiento.class);
		initComponents(view);
	}

	private void initComponents(Administrar view) {
		listaPaginada.addColumnas("Número","Mes planificado","Equipo","Inventario","Estado");
		listaPaginada.addCampos("no");
		listaPaginada.addCampos("mes");
		listaPaginada.addCampos("equipo");
		listaPaginada.addCampos("inventario");
		listaPaginada.addCampos("estado");
		Label nombreForm=new Label("Listar matenimientos programados");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm,"nombreFormulario");
		listaPaginada.initComponent(view);
		//especificarle que componente se encargara de editar la entidad
		listaPaginada.setEditorEntidad(new EditarMantenimientosProg(view));
		//especificarle que componente se encargara de mostrar los datos de la entidad
		listaPaginada.setVerEntidad(new VerDatosMantenimientosPlanificado(view));
		container.addComponent(listaPaginada,"formulario");
		setCompositionRoot(container);
	}

}
