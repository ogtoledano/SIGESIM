package infoartex.artex.componentes;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.MedioInformatico;
import infoartex.artex.dominio.Traslado;
import infoartex.artex.vistas.Administrar;

@SuppressWarnings("serial")
public class TrasladarMedio extends ComponenteGenerico{

	private MedioInformatico medio;
	private final CustomLayout main;
	private final VerticalLayout container;
	 private List<Departamento> lista;
	 
	
	public TrasladarMedio(Administrar view, MedioInformatico medio) {
		super(view);
		this.medio=medio;
		container=new VerticalLayout();
		lista=new LinkedList<>();
		main=new CustomLayout("formulario");
		initComponent(view);
	}
	
	public void initComponent(final Administrar view){
		main.removeAllComponents();
		container.removeAllComponents();
		Panel datosBasico = new Panel("Rellene los campos");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
        
        final TextField numero = new TextField("Número consecutivo");
        numero.setNullRepresentation("");
        numero.setImmediate(true);
        numero.setValue(""+(fachada.maximoID(Traslado.class)+1));
        numero.setStyleName("form-control");
        
        final ComboBox tipomov = new ComboBox("Elija el tipo de movimiento");
        tipomov.addItem("Seleccione");
        tipomov.setNullSelectionItemId("Seleccione");
        tipomov.addItem("Movimiento interno");
        tipomov.addItem("Ajuste baja");
        tipomov.addItem("Enviado a reparar");
        tipomov.setImmediate(true);
        
        final TextField trabentrega = new TextField("Trabajador que entrega");
        trabentrega.setNullRepresentation("");
        trabentrega.setImmediate(true);
        trabentrega.setStyleName("form-control");
        trabentrega.setSizeFull();
        //inventario.setRequired(true);
        
        final TextField trabrecibe = new TextField("Trabajador que recibe");
        trabrecibe.setNullRepresentation("");
        trabrecibe.setImmediate(true);
        trabrecibe.setStyleName("form-control");
        trabrecibe.setSizeFull();
        //inventario.setRequired(true);
        
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(new Date());
        int horaGC=gc.get(GregorianCalendar.HOUR_OF_DAY)>12||gc.get(GregorianCalendar.HOUR_OF_DAY)==0?Math.abs(gc.get(GregorianCalendar.HOUR_OF_DAY)-12):gc.get(GregorianCalendar.HOUR_OF_DAY);
        int minutosGC=gc.get(GregorianCalendar.MINUTE);
        HorizontalLayout hora=new HorizontalLayout();
        final ComboBox horaCombo=new ComboBox("Hora");
        horaCombo.setImmediate(true);
        for(int i=1;i<13;i++){
        	horaCombo.addItem(""+i);
        }
        
        horaCombo.setNullSelectionAllowed(false);
        horaCombo.setValue(horaGC+"");
        horaCombo.setTextInputAllowed(false);
        final ComboBox minutosCombo=new ComboBox("Minutos");
        for(int i=0;i<60;i++){
        	if(i>=0&&i<10){
        		minutosCombo.addItem("0"+i);
        	}else{
        	minutosCombo.addItem(""+i);
        	}
        }
        minutosCombo.setNullSelectionAllowed(false);
        minutosCombo.setValue(minutosGC>=0&&minutosGC<10?"0"+minutosGC:minutosGC+"");
        minutosCombo.setImmediate(true);
        minutosCombo.setTextInputAllowed(false);
        final ComboBox am=new ComboBox("AM/PM");
        am.addItem("AM");
        am.addItem("PM");
        am.setNullSelectionAllowed(false);
        am.setImmediate(true);
        am.setValue("AM");
        am.setTextInputAllowed(false);
        if(gc.get(GregorianCalendar.HOUR_OF_DAY)>=12){
        	am.setValue("PM");
        }
        hora.addComponents(horaCombo,minutosCombo,am);
        hora.setSpacing(true);
        
        
        final DateField fecha = new DateField("Fecha");
        fecha.setImmediate(true);
        fecha.setValue(new Date());
        
        formularioBasico.addComponents(numero,tipomov,fecha,hora,trabentrega,trabrecibe);
		datosBasico.setContent(formularioBasico);
		Panel panelTraslado = new Panel("Trasladar medio de: "+ medio.getDepartamento().getNombre());
		panelTraslado.setStyleName("visorEntidad");
        panelTraslado.setSizeFull();
        FormLayout formularioTraslado = new FormLayout();
        formularioTraslado.setMargin(true);
        formularioTraslado.setWidth("100%");
		final ComboBox departamentos=new ComboBox("Hacia: ");
		departamentos.addItem("Seleccione");
        lista=cargarDepartamentos(departamentos);
        departamentos.setNullSelectionItemId("Seleccione");
        departamentos.setImmediate(true);
        departamentos.setWidth("100%");
        final BeanFieldGroup<Traslado> validador=new BeanFieldGroup<>(Traslado.class);
        validador.setItemDataSource(new Traslado());
        validador.bind(trabentrega, "trabajadorentrega");
        validador.bind(trabrecibe, "trabajadorrecibe");
        validador.bind(fecha, "fecha");
        NativeButton trasladar=new NativeButton("Trasladar", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
            	
            	if(departamentos.getValue()!=null){
            		try {
            			if(Integer.parseInt(numero.getValue()+"")>0){
            			if(fachada.obtenerTraslado(Integer.parseInt(numero.getValue()+""))==null){
            			if(!medio.getEstado().equals("Espera de pieza")){
            				if(medio.getComputadora()==null){
            					if(tipomov.getValue()!=null){
            						validador.commit();
            						String horaSave=horaCombo.getValue()+":"+minutosCombo.getValue()+" "+am.getValue();
            						validador.getItemDataSource().getBean().setHora(horaSave);
            						validador.getItemDataSource().getBean().setNumero(numero.getValue());
            						validador.getItemDataSource().getBean().setUsuario(""+VaadinSession.getCurrent().getAttribute("usuario"));
            						validador.getItemDataSource().getBean().setTipoTraslado("Traslado de "+medio.getDepartamento().getNombre()+" hacia "+departamentos.getValue().toString()+", con tipo de traslado: "+tipomov.getValue().toString());
            					actualizarDepartamento(lista, departamentos.getValue().toString(), medio,validador.getItemDataSource().getBean());
            					view.reemplazarContenido(new ListarMediosInformaticos(view));
            					Notificaciones.NotificarSubmit("El medio se ha trasladado exitosamente");
            					}else{
            						Notificaciones.NotificarError("Debe elegir un tipo de movimiento para el medio");
            					}
            				}else{
            					Notificaciones.NotificarError("El medio no se puede trasladar de departamento porque está asociado a una PC");
            				}
            			}else{
            				Notificaciones.NotificarError("El medio no se puede trasladar de departamento porque está en espera de pieza de repuesto");
            			}}else{
                    		Notificaciones.NotificarError("Ya existe un traslado registrado con ese número");
                    	}
					}else{
						Notificaciones.NotificarError("El número del traslado debe ser un entero mayor que 0");
						}
					}
            		catch (NumberFormatException e) {
            			Notificaciones.NotificarError("El número del traslado debe ser un entero");
					}
            		catch (Exception e) {
            			Notificaciones.NotificarError("Errores en los datos por favor verifique los campos señalados");
					}
            	}else{
            		Notificaciones.NotificarError("Debe elegir un departamento");
            	}
            	
                
            }
        }); 
        trasladar.setStyleName("btn btn-info botonControl");
        trasladar.setIcon(FontAwesome.EDIT);
        NativeButton cancelar=new NativeButton("Cancelar", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                view.contenidoAnterior();
            }
        });
        cancelar.setStyleName("btn btn-danger botonControl");
        cancelar.setIcon(FontAwesome.CLOSE);
        HorizontalLayout reg=new HorizontalLayout();
        reg.addComponents(trasladar,cancelar);
        reg.setSpacing(true);
        reg.setStyleName("controles");
		formularioTraslado.addComponents(departamentos);
		formularioTraslado.setSpacing(true);
		panelTraslado.setContent(formularioTraslado);
		container.addComponents(datosBasico,panelTraslado,reg);
		main.addComponent(container,"formulario");
		Label nombreFormulario=new Label("Trasladar medio");
		nombreFormulario.setStyleName("text-left encabezado");
		main.addComponent(nombreFormulario, "nombreFormulario");
		setCompositionRoot(main);
	}
	
	private List<Departamento> cargarDepartamentos(ComboBox departamentos){
		@SuppressWarnings("unchecked")
		List<Departamento> lista=(List<Departamento>) getFachada().listarTodos(Departamento.class);
		for(Departamento d:lista){
			departamentos.addItem(d.getNombre());
		}
		return lista;
	}

	private void actualizarDepartamento(List<Departamento> lista, String departamento ,MedioInformatico i,Traslado t) throws Exception{
		boolean encontrado=false;
		int k=0;
		while(!encontrado&&k<lista.size()){
			if(lista.get(k).getNombre().equals(departamento)){
				encontrado=true;
			}else{
				k++;
			}
		}
		Departamento dep0=i.getDepartamento();
		if(dep0!=null){
		dep0.getMedio().remove(i);
		fachada.actualizarEntidad(dep0);
		}
		i.getHistorial().add(t);
		Departamento dep=lista.get(k);
		i.setDepartamento(dep);
		dep.getMedio().add(i);
		fachada.actualizarEntidad(dep);
		fachada.actualizarEntidad(i);
	}
	
}
