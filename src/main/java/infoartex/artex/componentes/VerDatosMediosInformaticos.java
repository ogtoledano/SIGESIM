package infoartex.artex.componentes;

import java.text.SimpleDateFormat;
import java.util.List;

import infoartex.artex.dominio.*;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import infoartex.artex.dominio.Entidad;
import infoartex.artex.vistas.Administrar;

@SuppressWarnings("serial")

public class VerDatosMediosInformaticos extends ComponenteGenerico{
	
    private final CustomLayout main;
    
    //entidad a mostrar
    private MedioInformatico mediosinformaticos;
	
	public VerDatosMediosInformaticos(final Administrar view) {
		super(view);
		main = new CustomLayout("formulario");
		//usar formulario de los layouts que tiene el tema
		mediosinformaticos=new MedioInformatico();
	}

	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		final VerticalLayout container=new VerticalLayout();
		Label nombreFormulario=new Label("Ver datos del medio informático");
		nombreFormulario.setStyleName("text-left encabezado");
		main.addComponent(nombreFormulario, "nombreFormulario");
		Panel datosBasico = new Panel("Datos del medio");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
		Label inventario=new Label("No. Inventario: "+ mediosinformaticos.getInventario());
		Label tipo=new Label("Tipo de medio: "+ mediosinformaticos.getTipo());
		Label marca=new Label("Marca: "+ mediosinformaticos.getMarca());
		Label modelo=new Label("Modelo: "+ mediosinformaticos.getModelo());
		
		inventario.setStyleName("label label-info");
		formularioBasico.addComponents(inventario,tipo,marca,modelo);
		datosBasico.setContent(formularioBasico);
		datosBasico.setWidth("100%");
		container.addComponent(datosBasico);
		if(mediosinformaticos instanceof Torre){
			
			Label fuente=new Label("Cap.Fuente: "+ ((Torre)mediosinformaticos).getFuente() );
			Label board=new Label("Motherboard: "+ ((Torre)mediosinformaticos).getBoard() );
			Label procesador=new Label("Procesador: "+ ((Torre)mediosinformaticos).getProcesador() );
			Label ram=new Label("Memoria RAM: "+ ((Torre)mediosinformaticos).getRam() );
			Label hdd=new Label("Disco duro: "+ ((Torre)mediosinformaticos).getHdd());
			Label optica=new Label("Unidad óptica: "+ ((Torre)mediosinformaticos).getOptica() );
			Label modem=new Label("Adaptador de red: "+ ((Torre)mediosinformaticos).getRed() );
			Label otros=new Label("Otros medios: "+ ((Torre)mediosinformaticos).getOtros() );
			
			
			
			final Panel detalles = new Panel("Detalles torre");
			detalles.setSizeUndefined();
	        FormLayout datosTorre = new FormLayout();
	        datosTorre.setMargin(true);
	        datosTorre.setWidth("100%");
	        datosTorre.addComponents(fuente,board,procesador,ram,hdd,optica,modem,otros);
	        detalles.setContent(datosTorre);
	        detalles.setWidth("100%");
	        
	        final NativeButton masInfo=new NativeButton("Más información");
			masInfo.setIcon(FontAwesome.PLUS_CIRCLE);
			masInfo.setStyleName("botonesOpcionesTabla");
			
			final NativeButton cerrarInfo=new NativeButton("Menos información");
			cerrarInfo.setIcon(FontAwesome.MINUS_CIRCLE);
			cerrarInfo.setStyleName("botonesOpcionesTabla");
			
			
	        
			masInfo.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					container.replaceComponent(masInfo, detalles);
					container.addComponent(cerrarInfo);
				}
			});
			cerrarInfo.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					container.replaceComponent(detalles, masInfo);
					container.removeComponent(cerrarInfo);
				}
			});
			
			
			
			container.addComponents(masInfo);
		}
		final NativeButton mostrarHistorial=new NativeButton("Historial de recorrido");
		mostrarHistorial.setIcon(FontAwesome.EYE);
		mostrarHistorial.setStyleName("botonesOpcionesTabla");
		
		final NativeButton cerrarHistorial=new NativeButton("Cerrar historial de recorrido");
		cerrarHistorial.setIcon(FontAwesome.MINUS_CIRCLE);
		cerrarHistorial.setStyleName("botonesOpcionesTabla");
		
		final Panel historial = new Panel("Historial de recorrido del medio");
		historial.setSizeUndefined();
        FormLayout datosHistorial = new FormLayout();
        datosHistorial.setMargin(true);
        datosHistorial.setWidth("100%");
        historial.setWidth("100%");
		llenarHistorial(datosHistorial);
        historial.setContent(datosHistorial);
        
        mostrarHistorial.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				container.replaceComponent(mostrarHistorial, historial);
				container.addComponent(cerrarHistorial);
			}
		});
		cerrarHistorial.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				container.replaceComponent(historial, mostrarHistorial);
				container.removeComponent(cerrarHistorial);
			}
		});
		container.addComponent(mostrarHistorial);
		main.addComponent(container,"formulario");
		setCompositionRoot(main);
	}
	//obligado
	public void setEntidad(Entidad entidad){
		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
		this.mediosinformaticos=(MedioInformatico)entidad;
	}
	
	public void llenarHistorial(FormLayout datosHistorial){
		Table tabla=new Table();
		tabla.addContainerProperty("Fecha", String.class, null);
		tabla.addContainerProperty("Usuario que realizó el traslado", String.class, null);
		tabla.addContainerProperty("Descripción del recorrido", String.class, null);
		
		List<Traslado> historial=mediosinformaticos.getHistorial();
		for(Traslado t:historial){
			Object[] datos=new Object[3];
			SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
			datos[0]=format.format(t.getFecha());
			datos[1]=t.getUsuario();
			datos[2]=t.getTipoTraslado();
			tabla.addItem(datos, t.getId());
		}
		tabla.setWidth("100%");
		tabla.setPageLength(tabla.size());
		datosHistorial.addComponent(tabla);
	}
	
	
	
}
