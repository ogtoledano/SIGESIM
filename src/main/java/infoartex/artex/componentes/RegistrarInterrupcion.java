package infoartex.artex.componentes;


import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.ConfiguracionGlobal;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.Interrupcion;
import infoartex.artex.notificacionesCorreo.NotificacionInterrupcion;
import infoartex.artex.vistas.Administrar;


@SuppressWarnings("serial")
public class RegistrarInterrupcion extends ComponenteGenerico{
	
	//obligado
	private final CustomLayout container;
    private final VerticalLayout main;
	
	public RegistrarInterrupcion(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		//usar formulario de los layouts que tiene el tema
        container=new CustomLayout("formulario");
        //metodo que arranca los componentes de vaadin
        initComponents(view);
	}

	private void initComponents(final Administrar view) {
		Panel datosBasico = new Panel("Rellene los campos");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
        @SuppressWarnings("unchecked")
		List<ConfiguracionGlobal> config=(List<ConfiguracionGlobal>) fachada.listarTodos(ConfiguracionGlobal.class);
        String ccosto=config.isEmpty()?"0":config.get(0).getCentroCostoSucursal();
        final TextField inventario = new TextField("Inventario");
        inventario.setNullRepresentation("");
        inventario.setImmediate(true);
        inventario.setStyleName("form-control");
        inventario.setSizeFull();
        final DateField fecha = new DateField("Fecha");
        fecha.setImmediate(true);
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
        final TextField trabajador = new TextField("Trabajador");
        trabajador.setNullRepresentation("");
        trabajador.setImmediate(true);
        trabajador.setStyleName("form-control");
        trabajador.setSizeFull();
        final ComboBox departamentos=new ComboBox("Departamento");
        departamentos.addItem("Seleccione");
        departamentos.setNullSelectionItemId("Seleccione");
        final List<Departamento> lista=cargarDepartamentos(departamentos);
        departamentos.setImmediate(true);
        final TextArea descripcion = new TextArea("Descripción");
        descripcion.setNullRepresentation("");
        descripcion.setImmediate(true);
        descripcion.setStyleName("form-control");
        descripcion.setSizeFull();
        formularioBasico.addComponents(inventario,fecha,hora,trabajador,departamentos,descripcion);
        datosBasico.setContent(formularioBasico);
        //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
        //por defecto estos atributos tienen valor null
        final BeanFieldGroup<Interrupcion> validador=new BeanFieldGroup<Interrupcion>(Interrupcion.class);
        validador.setItemDataSource(new Interrupcion());
        validador.bind(inventario, "inventario");
        validador.bind(fecha, "fecha");
        validador.bind(trabajador, "trabajador");
        validador.bind(descripcion, "descripcion");
        inventario.setValue(ccosto+"-");
        NativeButton registrar=new NativeButton("Registrar", new Button.ClickListener() {

            @SuppressWarnings("unchecked")
			@Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                	//registrar en la BD
                	//ejecutar validacion
                	if(departamentos.getValue()!=null){
                    validador.commit();	
                    validador.getItemDataSource().getBean().setUsuario(VaadinSession.getCurrent().getAttribute("usuario").toString());
                    validador.getItemDataSource().getBean().setEstado("Sin procesar");
                    String horaSave=horaCombo.getValue()+":"+minutosCombo.getValue()+" "+am.getValue();
                    validador.getItemDataSource().getBean().setHora(horaSave);
                    //arreglar duplicidad mediante jpa annotations
                    actualizarDepartamento(lista, departamentos.getValue().toString(), validador.getItemDataSource().getBean());
                    NotificacionInterrupcion correo=new NotificacionInterrupcion();
                    correo.Notificar();
                    Notificaciones.NotificarSubmit("Interrupcion registrada correctamente");
                    validador.setItemDataSource(new Interrupcion());
					lista.clear();
					lista.addAll((List<Departamento>) getFachada().listarTodos(Departamento.class));
                	actualizar(validador.getItemDataSource().getBean().getClass());
                	}else{
                		Notificaciones.NotificarError("Debe elegir un departamento");
                	}
                } catch (Exception ex) {
                	Notificaciones.NotificarError("Errores en los datos por favor revise los campos señalados");
                    Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        NativeButton cancelar=new NativeButton("Cancelar", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                view.contenidoAnterior();
            }
        });
        
        registrar.setStyleName("btn btn-info botonControl");
        cancelar.setStyleName("btn btn-danger botonControl");
        registrar.setIcon(FontAwesome.CHECK);
        cancelar.setIcon(FontAwesome.CLOSE);
        HorizontalLayout reg=new HorizontalLayout();
        reg.addComponents(registrar,cancelar);
        reg.setSpacing(true);
        reg.setStyleName("controles");
        datosBasico.setWidth("100%");
        main.addComponents(datosBasico,reg);
        container.addComponent(main, "formulario");
        Label nombreForm=new Label("Registrar nueva interrupción");
        nombreForm.setStyleName("text-left encabezado");
        container.addComponent(nombreForm, "nombreFormulario");
        actualizar(validador.getItemDataSource().getBean().getClass());
        setCompositionRoot(container);
		
	}

	private void actualizar(Class<?> clazz) {
		Label interrupciones =new Label(fachada.cantidadElementos(clazz)+" interrupciones registradas");
		interrupciones.setStyleName("text-right");
        container.addComponent(interrupciones, "datos");
		
	}
	
	private List<Departamento> cargarDepartamentos(ComboBox departamentos){
		@SuppressWarnings("unchecked")
		List<Departamento> lista=(List<Departamento>) getFachada().listarTodos(Departamento.class);
		for(Departamento d:lista){
			departamentos.addItem(d.getNombre());
		}
		return lista;
	}
	
	private void actualizarDepartamento(List<Departamento> lista, String departamento ,Interrupcion i) throws Exception{
		boolean encontrado=false;
		int k=0;
		while(!encontrado&&k<lista.size()){
			if(lista.get(k).getNombre().equals(departamento)){
				encontrado=true;
			}else{
				k++;
			}
		}
		Departamento dep=lista.get(k);
		i.setDepartamento(dep);
		dep.getInterrupciones().add(i);
		fachada.actualizarEntidad(dep);
	}
	
	

}
