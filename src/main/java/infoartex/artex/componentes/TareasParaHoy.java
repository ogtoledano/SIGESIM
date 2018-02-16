package infoartex.artex.componentes;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import infoartex.artex.dominio.Interrupcion;
import infoartex.artex.dominio.Mantenimiento;
import infoartex.artex.vistas.Administrar;


@SuppressWarnings("serial")
public class TareasParaHoy extends ComponenteGenerico{
	private CustomLayout container;
	private HorizontalLayout main;
	private List<Interrupcion> interrupciones;
	private List<Mantenimiento> mantenimientoProg;
	public TareasParaHoy(final Administrar view) {
		super(view);
		this.container=new CustomLayout("tareasHoy");
		this.main=new HorizontalLayout();
		interrupciones=new LinkedList<>();
		mantenimientoProg=new LinkedList<>();
		initComponent(view);
	}
	
	public void initComponent(final CustomComponent view){
		cargarDatos();
		
		Panel panelInte = new Panel("Interrupciones");
        panelInte.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
        
        Panel panelMante = new Panel("Mantenimientos");
        panelMante.setSizeUndefined();
        FormLayout formularioMantenimiento = new FormLayout();
        formularioMantenimiento.setMargin(true);
        formularioMantenimiento.setWidth("100%");
        
        if(interrupciones.isEmpty()){
        	Label interrup=new Label("No hay interrupciones en este mes");
        	formularioBasico.addComponent(interrup);
        }
        for(Interrupcion i:interrupciones){
        	Label interrup=new Label("Interrupción: Inventario "+i.getInventario()+" Departamento: "+i.getDepartamento().getNombre());
        	formularioBasico.addComponent(interrup);
        }
        if(mantenimientoProg.isEmpty()){
        	Label mant=new Label("No hay mantenimientos programados para este mes");
        	formularioMantenimiento.addComponent(mant);
        }
        for(Mantenimiento i:mantenimientoProg){
        	Label mant=new Label("Mantenimiento: Inventario "+i.getInventario()+" Departamento: "+i.getDepartamento().getNombre());
        	formularioMantenimiento.addComponent(mant);
        }
        
        panelInte.setContent(formularioBasico);
        panelMante.setContent(formularioMantenimiento);
        panelInte.setWidth("100%");
        panelMante.setWidth("100%");
        main.addComponents(panelInte,panelMante);
        main.setSpacing(true);
        main.setSizeFull();
        container.addComponent(main, "tareas");
        setCompositionRoot(container);
	}
	
	public void cargarDatos(){
		GregorianCalendar gc=new GregorianCalendar();
		gc.setTime(new Date());
		int mes=gc.get(GregorianCalendar.MONTH)+1;
		interrupciones.addAll(fachada.listarInterupcionesXMes(mes));
		mantenimientoProg.addAll(fachada.listarMantenimientosXMes(mesActual(mes-1)));
	}
	
	public String mesActual(int mes) {
		return new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo",
				"Junio", "Julio", "Agosto", "Septiembre", "Octubre",
				"Noviembre", "Diciembre" }[mes];
	}

}
