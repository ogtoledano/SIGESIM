package infoartex.artex.componentes;

import java.util.List;

import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import infoartex.artex.bundles.MyPasswordEncrypt;
import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.ConfiguracionCorreo;
import infoartex.artex.vistas.Administrar;


@SuppressWarnings("serial")
public class ConfigurarCorreo extends ComponenteGenerico{

	private final CustomLayout container;
    private final VerticalLayout main;
    
    //entidad a modificar
    private ConfiguracionCorreo configuracion;
    private BeanFieldGroup<ConfiguracionCorreo> validador;
	private List<ConfiguracionCorreo> lista;
	
	@SuppressWarnings("unchecked")
	public ConfigurarCorreo(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		container=new CustomLayout("formulario");
		lista=(List<ConfiguracionCorreo>) fachada.listarTodos(ConfiguracionCorreo.class);
		configuracion= lista.size()!=0?lista.get(0):new ConfiguracionCorreo();
		validador=new BeanFieldGroup<>(ConfiguracionCorreo.class);
		validador.setItemDataSource(configuracion);
		initComponents(view);
	}
	
	public void initComponents(final Administrar view) {
		if(lista.size()==0){
		Label msg=new Label("No hay ninguna configuración registrada");
		msg.setStyleName("text text-danger");
		NativeButton nueva=new NativeButton("Nueva");
		nueva.setStyleName("btn btn-primary");
		nueva.setIcon(FontAwesome.PLUS_CIRCLE);
		nueva.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				cargarEditor(view);
			}
		});
		 Label nombreForm=new Label("Configuración de correo");
         nombreForm.setStyleName("text-left encabezado");
         container.addComponent(nombreForm, "nombreFormulario");
         main.addComponents(msg,nueva);
		 container.addComponent(main, "formulario");
		 setCompositionRoot(container);
		}else{
			cargarEditor(view);
		}
  	 
  	 }	
	
	public void cargarEditor(final Administrar view){
		main.removeAllComponents();
   		container.removeAllComponents();
   		Panel datosBasico = new Panel("Rellene los campos");
        datosBasico.setSizeUndefined();
        final FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
        final TextField servidor = new TextField("Servidor saliente");
        servidor.setNullRepresentation("");
        servidor.setImmediate(true);
        //nombre.setRequired(true);
        servidor.setStyleName("form-control");
        servidor.setSizeFull();
        final TextField puerto = new TextField("Puerto");
        puerto.setNullRepresentation("");
        puerto.setImmediate(true);
        //nombre.setRequired(true);
        puerto.setStyleName("form-control");
        puerto.setSizeFull();
        final TextField direccion = new TextField("Dirección de correo");
        direccion.setNullRepresentation("");
        direccion.setImmediate(true);
        direccion.setSizeFull();
        direccion.setStyleName("form-control");
        final PasswordField password = new PasswordField("Contraseña");
        password.setNullRepresentation("");
        password.setImmediate(true);
        password.setSizeFull();
        password.setStyleName("form-control");
        try {
			password.setValue(MyPasswordEncrypt.DesencriptarConDES(configuracion.getPassword()));
		} catch (ReadOnlyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        final TextField password2 = new TextField("Contraseña");
        password2.setNullRepresentation("");
        password2.setImmediate(true);
        password2.setSizeFull();
        password2.setStyleName("form-control");
        final CheckBox verPassword=new CheckBox("Ver contraseña");
        verPassword.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(verPassword.getValue()){
					password2.setValue(password.getValue());
					formularioBasico.replaceComponent(password, password2);	
				}else{
					password.setValue(password2.getValue());
					formularioBasico.replaceComponent(password2, password);	
				}
			}
		});
        verPassword.setImmediate(true);
        //gerencia.setRequired(true);
        final CheckBox activarEnvio=new CheckBox("Activar envío de notificaciones");
        activarEnvio.setValue(configuracion.isEnvioHabilitado());
        formularioBasico.addComponents(servidor,puerto,activarEnvio,direccion,password,verPassword);
        datosBasico.setContent(formularioBasico);
         
       //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
         //por defecto estos atributos tienen valor null
         //ojo asi se bindea para que se cargen los datos viejos
         validador.bind(servidor, "servidorSaliente");
         validador.bind(direccion, "direccion");
         validador.bind(puerto, "puerto");
         
         NativeButton registrar=new NativeButton("Guardar", new Button.ClickListener() {
             @Override
             public void buttonClick(Button.ClickEvent event) {
                 try {
                 	//registrar en la BD
                 	//ejecutar validacion
                     validador.commit();	
                     //no se registra, se actualiza
                     validador.getItemDataSource().getBean().setPassword(MyPasswordEncrypt.EncriptarConDES(password.getValue()));
                     validador.getItemDataSource().getBean().setEnvioHabilitado(activarEnvio.getValue());
                     getFachada().actualizarEntidad(validador.getItemDataSource().getBean());
                     Notificaciones.NotificarSubmit("Se ha actualizado correctamente la configuración de correo");
                 		}
                 catch (Exception ex) {
                 	Notificaciones.NotificarError("Errores en los datos por favor revise los campos señalados");
                     //Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
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
         registrar.setIcon(FontAwesome.EDIT);
         cancelar.setIcon(FontAwesome.CLOSE);
         HorizontalLayout reg=new HorizontalLayout();
         reg.addComponents(registrar,cancelar);
         reg.setSpacing(true);
         reg.setStyleName("controles");
         datosBasico.setWidth("100%");
         main.addComponents(datosBasico,reg);
         container.addComponent(main, "formulario");
         Label nombreForm=new Label("Configuración de correo");
         nombreForm.setStyleName("text-left encabezado");
         container.addComponent(nombreForm, "nombreFormulario");
         setCompositionRoot(container);
	}

}
