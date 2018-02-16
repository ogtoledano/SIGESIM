package infoartex.artex.componentes;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Mantenimiento;
import infoartex.artex.vistas.Administrar;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


@SuppressWarnings("serial")
public class EditarMantenimientosProg extends ComponenteGenerico {

	
	private final CustomLayout container;
    private final VerticalLayout main;
    
    //entidad a modificar
    private Mantenimiento mantenimiento;
    private BeanFieldGroup<Mantenimiento> validador;
    
    public EditarMantenimientosProg(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		//usar formulario de los layouts que tiene el tema
        container=new CustomLayout("formulario");
        mantenimiento=new Mantenimiento();
	}
	//debe llamarse asi setEntidad, en todos los que hagas
	public void setEntidad(Entidad entidad){
		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
		this.mantenimiento=(Mantenimiento)entidad;
        validador=new BeanFieldGroup<Mantenimiento>(Mantenimiento.class);
		validador.setItemDataSource(mantenimiento);	
	}

	public void initComponents(final Administrar view) {
		//se crea una unica instancia y este metodo lo llama java reflection
		main.removeAllComponents();
		container.removeAllComponents();
		Panel datosBasico = new Panel("Rellene los campos");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
        final TextField numero = new TextField("Número");
        numero.setNullRepresentation("");
        numero.setImmediate(true);
        numero.setWidth("100%");
        numero.setStyleName("form-control");
        
        final ComboBox mes=new ComboBox("Mes");
        mes.addItem("Seleccione");
		mes.setNullSelectionItemId("Seleccione");
		mes.addItem("Enero");
		mes.addItem("Febrero");
		mes.addItem("Marzo");
		mes.addItem("Abril");
		mes.addItem("Mayo");
		mes.addItem("Junio");
		mes.addItem("Julio");
		mes.addItem("Agosto");
		mes.addItem("Septiembre");
		mes.addItem("Octubre");
		mes.addItem("Noviembre");
		mes.addItem("Diciembre");
		mes.setImmediate(true);
        
        final ComboBox departamentos=new ComboBox("Departamento");
        departamentos.setImmediate(true);
        
        final ComboBox tipomedio=new ComboBox("Tipo de medio");
        tipomedio.addItem("Seleccione");
        tipomedio.setNullSelectionItemId("Seleccione");
        tipomedio.addItem("PC");
		tipomedio.addItem("Laptop");
		tipomedio.addItem("Impresora");
		tipomedio.setImmediate(true);
		tipomedio.setEnabled(false);
		
		final TextField inventario = new TextField("No.Inventario");
		inventario.setNullRepresentation("");
		inventario.setImmediate(true);
		inventario.setWidth("100%");
		inventario.setStyleName("form-control");
        inventario.setEnabled(false);
    
        formularioBasico.addComponents(numero,mes,departamentos,tipomedio,inventario);
        datosBasico.setContent(formularioBasico);
        //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
        //por defecto estos atributos tienen valor null
        //ojo asi se bindea para que se cargen los datos viejos
        validador.setItemDataSource(mantenimiento);
        validador.bind(numero, "no");
        validador.bind(mes, "mes");
        validador.bind(tipomedio, "equipo");
        validador.bind(inventario, "inventario");
        
        //poner el departamento de la entidad por defecto
        departamentos.addItem(validador.getItemDataSource().getBean().getDepartamento().getNombre());
        departamentos.setValue(validador.getItemDataSource().getBean().getDepartamento().getNombre());
        tipomedio.setValue(validador.getItemDataSource().getBean().getEquipo());
        mes.setValue(validador.getItemDataSource().getBean().getMes());
        departamentos.setEnabled(false);
        tipomedio.setEnabled(false);
        //lo de arriba es para que los combos cojan los valores de la entidad por defecto.
        NativeButton registrar=new NativeButton("Actualizar", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                	if(tipomedio.getValue()!=null){
                		if(mes.getValue()!=null){
                	//registrar en la BD
                	//ejecutar validacion
                    validador.commit();	
                    //no se registra, se actualiza
                    getFachada().actualizarEntidad(validador.getItemDataSource().getBean());
                    Notificaciones.NotificarSubmit("Se ha actualizado correctamente el mantenimiento programado");
                    view.nuevoContenido(new ListarMantenimientosProg(view));
                		}else{
                			Notificaciones.NotificarError("Debe elegir un mes del año");
                		}
                	}else{
                		Notificaciones.NotificarError("Debe elegir un tipo de medio");
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
        Label nombreForm=new Label("Modificar mantenimiento programado");
        nombreForm.setStyleName("text-left encabezado");
        container.addComponent(nombreForm, "nombreFormulario");
        setCompositionRoot(container);
	}
}
