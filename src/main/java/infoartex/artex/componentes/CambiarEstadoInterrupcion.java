package infoartex.artex.componentes;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Interrupcion;
import infoartex.artex.dominio.OrdenTrabajo;
import infoartex.artex.vistas.Administrar;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class CambiarEstadoInterrupcion extends ComponenteGenerico{

	private final VerticalLayout main;
	private Interrupcion interrupcion;
	
	public CambiarEstadoInterrupcion(Administrar view, Window win, Interrupcion interrupcion) {
		super(view);
		main=new VerticalLayout();
		this.interrupcion=interrupcion;
		initComponent(view,win);
	}
	
	public void initComponent(final Administrar view,final Window win){
		Panel datosBasico = new Panel("Cambiar estado de la interrupción de : "+ interrupcion.getEstado());
		datosBasico.setStyleName("visorEntidad");
        datosBasico.setSizeFull();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
		final ComboBox estado=new ComboBox("Hacia: ");
		estado.addItem("Seleccione");
        estado.setNullSelectionItemId("Seleccione");
        estado.addItem("Sin procesar");
        estado.addItem("Siendo procesada");
        estado.setWidth("100%");
        NativeButton trasladar=new NativeButton("Cambiar estado", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
            	if(estado.getValue()!=null){
            		interrupcion.setEstado(estado.getValue().toString());
            		try {
            			if(interrupcion.getEstado().equals("Sin procesar")){
            				int idOrden=interrupcion.getOrden().getId();
            				interrupcion.setOrden(null);
            				getFachada().actualizarEntidad(interrupcion);
            				fachada.eliminarEntidad(idOrden, OrdenTrabajo.class);
            			}else{
            				if(interrupcion.getOrden()!=null){
            					OrdenTrabajo orden=interrupcion.getOrden();
            					orden.setEstado("Abierta");
            					getFachada().actualizarEntidad(orden);
            				}
            			}
						getFachada().actualizarEntidad(interrupcion);
						Notificaciones.NotificarSubmit("Se ha modificado el estado de la interrupción");
						win.close();
						view.nuevoContenido(new ListarInterrupciones(view));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}else{
            		Notificaciones.NotificarError("Debe elegir un estado");
            	}
                	
                
            }
        }); 
        trasladar.setStyleName("btn btn-info botonControl");
        trasladar.setIcon(FontAwesome.EDIT);
		formularioBasico.addComponents(estado,trasladar);
		formularioBasico.setSpacing(true);
		datosBasico.setContent(formularioBasico);
		main.addComponents(datosBasico);
		setCompositionRoot(main);
	}
	
}
