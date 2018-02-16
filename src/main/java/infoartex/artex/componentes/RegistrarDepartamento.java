package infoartex.artex.componentes;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.vistas.Administrar;


@SuppressWarnings("serial")
public class RegistrarDepartamento extends ComponenteGenerico{

	private final CustomLayout container;
    private final VerticalLayout main;
	
	public RegistrarDepartamento(final Administrar view) {
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
        final TextField nombre = new TextField("Nombre del departamento o local");
        nombre.setNullRepresentation("");
        nombre.setImmediate(true);
        nombre.setSizeFull();
        //nombre.setRequired(true);
        nombre.setStyleName("form-control");
        final TextField gerencia = new TextField("Ubicación");
        gerencia.setNullRepresentation("");
        gerencia.setImmediate(true);
        gerencia.setSizeFull();
        gerencia.setStyleName("form-control");
        final TextField ccosto = new TextField("Centro de costo");
        ccosto.setNullRepresentation("");
        ccosto.setImmediate(true);
        ccosto.setSizeFull();
        ccosto.setStyleName("form-control");
        //gerencia.setRequired(true);
        formularioBasico.addComponents(nombre,gerencia,ccosto);
        datosBasico.setContent(formularioBasico);
        //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
        //por defecto estos atributos tienen valor null
    	final BeanFieldGroup<Departamento> validador=new BeanFieldGroup<>(Departamento.class);
        validador.setItemDataSource(new Departamento());
        validador.bind(nombre, "nombre");
        validador.bind(gerencia, "gerencia");
        validador.bind(ccosto, "ccosto");
        NativeButton registrar=new NativeButton("Registrar", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                	
                    validador.commit();	
                    getFachada().registrarEntidad(validador.getItemDataSource().getBean());
                    Notificaciones.NotificarSubmit("Departamento registrado correctamente");
                    gerencia.setValue("");
                    nombre.setValue("");
                    ccosto.setValue("");
                	actualizar(validador.getItemDataSource().getBean().getClass());
                	
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
        registrar.setIcon(FontAwesome.CHECK);
        cancelar.setIcon(FontAwesome.CLOSE);
        HorizontalLayout reg=new HorizontalLayout();
        reg.addComponents(registrar,cancelar);
        reg.setSpacing(true);
        reg.setStyleName("controles");
        datosBasico.setWidth("100%");
        main.addComponents(datosBasico,reg);
        container.addComponent(main, "formulario");
        Label nombreForm=new Label("Registrar nuevo departamento o local");
        nombreForm.setStyleName("text-left encabezado");
        container.addComponent(nombreForm, "nombreFormulario");
        actualizar(validador.getItemDataSource().getBean().getClass());
        setCompositionRoot(container);
		
	}

	private void actualizar(Class<?> clazz) {
		Label departamentos =new Label(fachada.cantidadElementos(clazz)+" departamentos registrados");
		departamentos.setStyleName("text-right");
        container.addComponent(departamentos, "datos");
		
	}

}
