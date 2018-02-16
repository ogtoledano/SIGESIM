package infoartex.artex.componentes;

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

import infoartex.artex.bundles.MyPasswordEncrypt;
import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Usuario;
import infoartex.artex.vistas.Administrar;

@SuppressWarnings("serial")
public class EditarPerfil extends ComponenteGenerico {

	private final CustomLayout container;
    private final  VerticalLayout main;
    private  Usuario usuario;
    private  BeanFieldGroup<Usuario> validador;
    private  String userName;
	
	public EditarPerfil(final Administrar view, String userName) {
		super(view);
		main=new VerticalLayout();
		container=new CustomLayout("formulario");
		this.usuario=new Usuario();
		this.userName=userName;
	}
	
	public void setEntidad(Entidad entidad){
		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
		this.usuario=(Usuario)entidad;
		validador=new BeanFieldGroup<Usuario>(Usuario.class);
		validador.setItemDataSource(usuario);	
	}
	
	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		container.removeAllComponents();
        Panel datosBasico = new Panel("Datos del usuario");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
        final TextField usuario = new TextField("Usuario");
        usuario.setNullRepresentation("");
        if(!userName.equals("")){
        usuario.setValue(userName);
        }
        usuario.setImmediate(true);
        usuario.setEnabled(false);
        usuario.setWidth("100%");
        usuario.setStyleName("form-control");
        final TextField nombre = new TextField("Nombre");
        nombre.setNullRepresentation("");
        nombre.setImmediate(true);
        nombre.setWidth("100%");
        nombre.setStyleName("form-control");
        final TextField apellidos = new TextField("Apellidos");
        apellidos.setNullRepresentation("");
        apellidos.setImmediate(true);
        apellidos.setWidth("100%");
        apellidos.setStyleName("form-control");
        final TextField correo = new TextField("Correo");
        correo.setNullRepresentation("");
        correo.setImmediate(true);
        correo.setWidth("100%");
        correo.setStyleName("form-control");
        final PasswordField password = new PasswordField("Contraseña");
        password.setNullRepresentation("");
        password.setImmediate(true);
        password.setWidth("100%");
        password.setStyleName("form-control");
        final PasswordField confPassword = new PasswordField("Confirmar contraseña");
        confPassword.setNullRepresentation("");
        confPassword.setImmediate(true);
        confPassword.setWidth("100%");
        password.setEnabled(false);
        confPassword.setStyleName("form-control");
        confPassword.setEnabled(false);
        final CheckBox cambiarPass=new CheckBox("Cambiar contraseña");
        cambiarPass.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(cambiarPass.getValue()){
					password.setEnabled(true);
					confPassword.setEnabled(true);
				}else{
					password.setEnabled(false);
					confPassword.setEnabled(false);
				}
				
			}
		});
        final TextField cargo = new TextField("Cargo");
        cargo.setNullRepresentation("");
        cargo.setImmediate(true);
        cargo.setWidth("100%");
        cargo.setStyleName("form-control");
        formularioBasico.addComponents(usuario,nombre,apellidos,correo,cambiarPass,password,confPassword,cargo);
        datosBasico.setContent(formularioBasico);
        //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
        //por defecto estos atributos tienen valor null
        

        if(!userName.equals("")){
        	validador=new BeanFieldGroup<Usuario>(Usuario.class);
        	validador.setItemDataSource(fachada.obtenerUsuario(userName));
        }else{
            validador.setItemDataSource(this.usuario);
        	validador.bind(usuario, "usuario");
        }
        validador.bind(nombre, "nombre");
        validador.bind(apellidos, "apellidos");
        validador.bind(correo, "email");
        validador.bind(cargo, "cargo");
        NativeButton registrar=new NativeButton("Editar perfil", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                	if(!cambiarPass.isEnabled()||password.getValue().equals(confPassword.getValue())){
                		validador.commit();
                    if(cambiarPass.isEnabled()&&!password.getValue().equals("")){
                    	validador.getItemDataSource().getBean().setPassword(MyPasswordEncrypt.encrypt(password.getValue(),"MD5", "UTF-8"));	
                    }
                    getFachada().actualizarEntidad(validador.getItemDataSource().getBean());
                    Notificaciones.NotificarSubmit("Se ha modificado el perfil");
                    view.contenidoAnterior();
                	}else{
                		Notificaciones.NotificarError("Las contraseñas no coinciden");
                	}
                } catch (Exception ex) {
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
        Label nombreForm=new Label("Editar perfil");
        nombreForm.setStyleName("text-left encabezado");
        container.addComponent(nombreForm, "nombreFormulario");
        setCompositionRoot(container);
    }

}
