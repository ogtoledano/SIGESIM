package infoartex.artex.componentes;

import java.util.List;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

import infoartex.artex.dominio.Mantenimiento;
import infoartex.artex.dominio.PC;
import infoartex.artex.vistas.Administrar;


@SuppressWarnings("serial")
public class MantenimientosPC extends ComponenteGenerico{
	private final CustomLayout main;
	private PC pc;
	public MantenimientosPC(final Administrar view, PC pc) {
		super(view);
		main = new CustomLayout("formulario");
		this.pc=pc;
	}
	
	public void initComponent(){
		main.removeAllComponents();
		Table tabla=new Table();
		tabla.addContainerProperty("Estado", String.class, null);
		tabla.addContainerProperty("Mes", String.class, null);
		tabla.addContainerProperty("Departamento", String.class, null);
		tabla.addContainerProperty("Reporte", String.class, null);
		List<Mantenimiento> lista=pc.getMantenimientosEfectuados();
		for(Mantenimiento m:lista){
			String reporte=m.getReporte()!=null?m.getReporte().getObservacion():"Sin reporte de mantenimiento efectuado";
			tabla.addItem(new Object[]{m.getEstado(), m.getMes(),m.getDepartamento().getNombre(),reporte},m.getId());
		}
		tabla.setWidth("100%");
		tabla.setPageLength(tabla.size());
		Label nombreFormulario=new Label("Mantenimientos a la PC");
		nombreFormulario.setStyleName("text-left encabezado");
		main.addComponent(nombreFormulario, "nombreFormulario");
		if(lista.isEmpty()){
			Label msg=new Label("No hay mantenimientos asociados");
			msg.setStyleName("label label-danger");
			main.addComponent(msg, "formulario");
		}else{
			main.addComponent(tabla, "formulario");
		}
		setCompositionRoot(main);
	}
	
}
