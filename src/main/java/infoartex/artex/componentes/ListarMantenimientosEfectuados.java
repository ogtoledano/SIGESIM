package infoartex.artex.componentes;

import infoartex.artex.dominio.ReporteMantenimiento;
import infoartex.artex.vistas.Administrar;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;


@SuppressWarnings("serial")
public class ListarMantenimientosEfectuados extends ComponenteGenerico {
	private final CustomLayout container;
	private ListaPaginada<ReporteMantenimiento> listaPaginada;
	
	public ListarMantenimientosEfectuados(final Administrar view) {
		super(view);
		container=new CustomLayout("formulario");
		listaPaginada=new ListaPaginada<>(view, 10, 200, ReporteMantenimiento.class);
		initComponents(view);
	}

	private void initComponents(Administrar view) {
		listaPaginada.addColumnas("Fecha","Tipo de medio","Inventario","Observación");
		//ojo con la fecha!!!!!!
		listaPaginada.addCampos("fecha");
		listaPaginada.addCampos("tipomedio");
		listaPaginada.addCampos("inventario");
		listaPaginada.addCampos("observacion");
		Label nombreForm=new Label("Listar mantenimientos efectuados");
		nombreForm.setStyleName("text-left encabezado");
		container.addComponent(nombreForm,"nombreFormulario");
		listaPaginada.initComponent(view);
		//especificarle que componente se encargara de editar la entidad
		listaPaginada.setEditorEntidad(new EditarMantenimientosEfectuados(view));
		//especificarle que componente se encargara de mostrar los datos de la entidad
		listaPaginada.setVerEntidad(new VerDatosMantenimientoEfectuado(view));
		container.addComponent(listaPaginada,"formulario");
		setCompositionRoot(container);
	}

}
