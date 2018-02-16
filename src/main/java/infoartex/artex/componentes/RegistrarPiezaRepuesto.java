package infoartex.artex.componentes;



import java.util.List;
import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.*;
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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


@SuppressWarnings("serial")
public class RegistrarPiezaRepuesto extends ComponenteGenerico {
	
	private final CustomLayout container;
    private final VerticalLayout main;
    
  
    //Constructor del componente
    public RegistrarPiezaRepuesto (final Administrar view) {
		super(view);
		main = new VerticalLayout();
		//usar formulario de los layouts que tiene el tema
        container=new CustomLayout("formulario");
        //metodo que arranca los componentes de vaadin
        initComponents(view);
	}  	
	
    private void initComponents(final Administrar view) {
		Panel datosBasico = new Panel("Rellene los siguientes campos: ");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
        
        final TextField codigo = new TextField("Codigo");
        codigo.setNullRepresentation("");
        codigo.setImmediate(true);
        codigo.setWidth("100%");
        codigo.setStyleName("form-control");
        final TextField factura = new TextField("Factura");
        factura.setNullRepresentation("");
        factura.setImmediate(true);
        factura.setWidth("100%");
        factura.setStyleName("form-control");
        
        final ComboBox tipo=new ComboBox("Tipo de medio");
        tipo.addItem("Seleccione");
        tipo.setNullSelectionItemId("Seleccione");
        @SuppressWarnings("unused")
		final List<TipoMedio> lista=cargarTipomedio(tipo);
        tipo.setImmediate(true);
    
        final TextField cantidad = new TextField("Cantidad");
        cantidad.setNullRepresentation("");
        cantidad.setImmediate(true);
        cantidad.setWidth("100%");
        cantidad.setStyleName("form-control");
        final TextArea detalles = new TextArea("Detalles");
        detalles.setNullRepresentation("");
        detalles.setImmediate(true);
        detalles.setSizeFull();
        detalles.setStyleName("form-control");
        //metes pal formulario los campos
        //setear el contenido del panel
        formularioBasico.addComponents(codigo,factura,tipo,cantidad,detalles);
        datosBasico.setContent(formularioBasico);
        //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
        //por defecto estos atributos tienen valor null
        
        final BeanFieldGroup<PiezaRepuesto> validador=new BeanFieldGroup<>(PiezaRepuesto.class);
        validador.setItemDataSource(new PiezaRepuesto());
        validador.bind(codigo, "codigo");
        validador.bind(factura, "factura");
        validador.bind(cantidad, "cantidad");
        validador.bind(detalles, "detalles");
        NativeButton registrar=new NativeButton("Registrar", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                	if(tipo.getValue()!=null){
                    validador.commit();	
                    validador.getItemDataSource().getBean().setTipo(tipo.getValue().toString());
                    validador.getItemDataSource().getBean().setNoOrden("Disponible");
                    //validador.getItemDataSource().getBean().setCantidad(cantidad(cantidad.getValue()));
                    getFachada().registrarEntidad(validador.getItemDataSource().getBean());
                    Notificaciones.NotificarSubmit("Pieza de repuesto registrada correctamente");
                    
                    codigo.setValue("");
                    factura.setValue("");
                    tipo.setValue("");
                    cantidad.setValue("");
                    detalles.setValue("");
                	actualizar(validador.getItemDataSource().getBean().getClass());
                	}else{
                		Notificaciones.NotificarError("Debe elegir una tipo de medio informático");
                	}
                } catch (Exception ex) {
                	Notificaciones.NotificarError("Errores en los datos por favor revise los campos señalados");
                    //Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); 
       registrar.setStyleName("btn btn-info botonControl");
    
    
    NativeButton cancelar=new NativeButton("Cancelar", new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            view.contenidoAnterior();
        }
    });
    cancelar.setStyleName("btn btn-danger botonControl");
    registrar.setIcon(FontAwesome.CHECK);
    cancelar.setIcon(FontAwesome.CLOSE);
    HorizontalLayout controles=new HorizontalLayout();
    controles.addComponents(registrar,cancelar);
    Label nombreForm=new Label("Entrada de nueva pieza de repuesto al almacén");
    nombreForm.setStyleName("text-left encabezado");
    controles.setSpacing(true);
    controles.setStyleName("controles");
    datosBasico.setWidth("100%");
    main.addComponents(datosBasico,controles);
    container.addComponent(main,"formulario");
    container.addComponent(nombreForm,"nombreFormulario");
    actualizar(validador.getItemDataSource().getBean().getClass());
    setCompositionRoot(container);
    }
    
    public void actualizar(Class<?> clazz){
    	Label medios =new Label(fachada.cantidadElementos(clazz)+" Medios Registrados");
    	medios.setStyleName("text-right");
        container.addComponent(medios, "datos");
    }
    
    public int cantidad(String cant){
		String aux="";
		for(int i=0;i<cant.length();i++){
			if(cant.charAt(i)!='.'){
				aux+=cant.charAt(i);
			}
		}
		return Integer.parseInt(aux);
	}
    
    private List<TipoMedio> cargarTipomedio(ComboBox tipomedios){
		@SuppressWarnings("unchecked")
		List<TipoMedio> lista=(List<TipoMedio>) getFachada().listarTodos(TipoMedio.class);
		for(TipoMedio d:lista){
			tipomedios.addItem(d.getDenominacion());
		}
		return lista;
	}
}
