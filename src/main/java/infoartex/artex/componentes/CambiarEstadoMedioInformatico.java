package infoartex.artex.componentes;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.MedioInformatico;
import infoartex.artex.dominio.Traslado;
import infoartex.artex.vistas.Administrar;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class CambiarEstadoMedioInformatico extends ComponenteGenerico{

	private MedioInformatico medio;
	private final VerticalLayout main;
	
	public CambiarEstadoMedioInformatico(Administrar view,MedioInformatico medio,Window win ) {
		super(view);
		this.medio=medio;
		main=new VerticalLayout();
		initComponent(view,win);
	}
	
	public void initComponent(final Administrar view,final Window win){
		Panel datosBasico = new Panel("Cambiar estado del medio de: "+ medio.getEstado());
		datosBasico.setStyleName("visorEntidad");
        datosBasico.setSizeFull();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
		final ComboBox estado=new ComboBox("Hacia: ");
		estado.addItem("Seleccione");
        estado.setNullSelectionItemId("Seleccione");
        estado.addItem("Explotación");
        estado.addItem("Ocioso");
        estado.addItem("Baja");
        estado.addItem("Espera de pieza");
        estado.setWidth("100%");
        NativeButton trasladar=new NativeButton("Trasladar", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
            	if(estado.getValue()!=null){
            		GregorianCalendar gc=new GregorianCalendar();
                    gc.setTime(new Date());
                    int horaGC=gc.get(GregorianCalendar.HOUR_OF_DAY)>12||gc.get(GregorianCalendar.HOUR_OF_DAY)==0?Math.abs(gc.get(GregorianCalendar.HOUR_OF_DAY)-12):gc.get(GregorianCalendar.HOUR_OF_DAY);
                    int minutosGC=gc.get(GregorianCalendar.MINUTE);
                    String horasave=horaGC+":"+(minutosGC>=0&&minutosGC<10?"0"+minutosGC:minutosGC)+" "+(gc.get(GregorianCalendar.HOUR_OF_DAY)>=0&&gc.get(GregorianCalendar.HOUR_OF_DAY)<12?"AM":"PM");
                    if(estado.getValue().toString().equals("Ocioso")){
            			medio.getHistorial().add(new Traslado((fachada.maximoID(Traslado.class)+1)+"","Cambio de estado de "+medio.getEstado()+" hacia "+estado.getValue().toString(),""+VaadinSession.getCurrent().getAttribute("usuario"),new Date(),horasave,"No definido","No definido"));
            			medio.getHistorial().add(new Traslado((fachada.maximoID(Traslado.class)+2)+"","Traslado de "+medio.getDepartamento().getNombre()+" hacia Taller de informática",""+VaadinSession.getCurrent().getAttribute("usuario"),new Date(),horasave,"No definido","No definido"));
            			Departamento taller=obtenerTaller();
            			medio.setDepartamento(taller);
            			taller.getMedio().add(medio);
            			try {
							fachada.actualizarEntidad(taller);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }else{
            			medio.getHistorial().add(new Traslado((fachada.maximoID(Traslado.class)+1)+"","Cambio de estado de "+medio.getEstado()+" hacia "+estado.getValue().toString(),""+VaadinSession.getCurrent().getAttribute("usuario"),new Date(),horasave,"No definido","No definido"));
            		}
                    medio.setEstado(estado.getValue().toString());	
            		((EditarMedioInformatico)view.getContenidoActual()).cambiarEstado(estado.getValue().toString());
            		try {
						getFachada().actualizarEntidad(medio);
						win.close();
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
	
	@SuppressWarnings("unchecked")
	public Departamento obtenerTaller(){
		List<Departamento> dep=(List<Departamento>) fachada.listarTodos(Departamento.class);
		boolean enc=false;
		int i=0;
		while(!enc&&i<dep.size()){
			if(dep.get(i).getNombre().equals("Taller de informática")){
				enc=true;
			}else{
				i++;
			}
		}
		return enc?dep.get(i):null;
	}
}
