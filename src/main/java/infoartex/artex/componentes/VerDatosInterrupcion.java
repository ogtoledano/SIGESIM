package infoartex.artex.componentes;

import java.text.SimpleDateFormat;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Interrupcion;
import infoartex.artex.vistas.Administrar;


@SuppressWarnings("serial")
public class VerDatosInterrupcion extends ComponenteGenerico{
	
    private final CustomLayout main;
    
    //entidad a mostrar
    private Interrupcion interrupcion;
	
	public VerDatosInterrupcion(final Administrar view) {
		super(view);
		main = new CustomLayout("formulario");
		//usar formulario de los layouts que tiene el tema
        interrupcion=new Interrupcion();
	}

	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		Label nombreFormulario=new Label("Ver datos de interrupción");
		nombreFormulario.setStyleName("text-left encabezado");
		main.addComponent(nombreFormulario, "nombreFormulario");
		Panel datosBasico = new Panel("Datos de la interrupción");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
		Label inventario=new Label("No. Inventario: "+ interrupcion.getInventario());
		SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
		Label fecha=new Label("Fecha: "+ format.format(interrupcion.getFecha()));
		Label hora=new Label("Hora: "+ interrupcion.getHora());
		Label trabajador=new Label("Trabajador: "+ interrupcion.getTrabajador());
		Label departamento=new Label("Departamento: "+ interrupcion.getDepartamento().getNombre());
		Label descripcionCaption=new Label("Descripción:");
		TextArea descripcion=new TextArea();
		descripcion.setValue(""+interrupcion.getDescripcion());
		descripcion.setEnabled(false);
		inventario.setStyleName("label label-info");
		
		final NativeButton orden=new NativeButton("Ver orden de trabajo asociada");
		orden.setIcon(FontAwesome.EYE);
		orden.setStyleName("botonesOpcionesTabla");
		
		orden.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				VerDatosOrdenTrabajo datosOrden=new VerDatosOrdenTrabajo(view);
				datosOrden.setEntidad(interrupcion.getOrden());
				datosOrden.initComponents(view);
				Window win=new Window("Datos generales");
				win.setContent(datosOrden);
				win.center();
				win.setResizable(false);
				win.setImmediate(true);
				win.setModal(true);
				win.setWidth("650px");
				getUI().addWindow(win);
			}
		});
		
		formularioBasico.addComponents(inventario,fecha,hora,trabajador,departamento,descripcionCaption,descripcion);
		if(interrupcion.getEstado().equals("No solucionada")||interrupcion.getEstado().equals("Solucionada")){
			formularioBasico.addComponent(orden);
		}
		datosBasico.setContent(formularioBasico);
		datosBasico.setWidth("100%");
		main.addComponent(datosBasico,"formulario");
		setCompositionRoot(main);
	}
	//obligado
	public void setEntidad(Entidad entidad){
		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
		this.interrupcion=(Interrupcion)entidad;
	}
	
	
	
}
