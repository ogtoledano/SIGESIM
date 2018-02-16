package infoartex.artex.componentes;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;

import infoartex.artex.dominio.Interrupcion;
import infoartex.artex.dominio.Mantenimiento;
import infoartex.artex.dominio.MedioInformatico;
import infoartex.artex.dominio.OrdenTrabajo;
import infoartex.artex.vistas.Administrar;


@SuppressWarnings("serial")
public class Dashboard extends ComponenteGenerico{

	private final CustomLayout container;
	
	public Dashboard(final Administrar view) {
		super(view);
		container=new CustomLayout("dashboard");
		initComponent(view);
	}

	private void initComponent(Administrar view) {
		container.addComponent(new InterrupcionesDepartamentoChart(view), "contenido");
		container.addComponent(new TiposInterrupcionesChart(view), "interrupciones");
		container.addComponent(new MantenimientosDepartamentoChart(view), "mantenimientos");
		container.addComponent(new MediosInformaticosDepartamentoChart(view), "medios");
		container.addComponent(new Label(""+fachada.cantidadElementos(Interrupcion.class)), "cantInterrupciones");
		container.addComponent(new Label(""+fachada.cantidadElementos(Mantenimiento.class)), "cantMantenimientos");
		container.addComponent(new Label(""+fachada.cantidadElementos(MedioInformatico.class)), "cantMedios");
		container.addComponent(new Label(""+fachada.cantidadElementos(OrdenTrabajo.class)), "cantOrdenes");
		container.addComponent(new TareasParaHoy(view), "tareasHoy");
		setCompositionRoot(container);
	}

}
