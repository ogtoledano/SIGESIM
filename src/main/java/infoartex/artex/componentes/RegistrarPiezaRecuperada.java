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
public class RegistrarPiezaRecuperada extends ComponenteGenerico {
	
	private final CustomLayout container;
    private final VerticalLayout main;
    
  
    //Constructor del componente
    public RegistrarPiezaRecuperada (final Administrar view) {
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
        @SuppressWarnings("unchecked")
		List<ConfiguracionGlobal> config=(List<ConfiguracionGlobal>) fachada.listarTodos(ConfiguracionGlobal.class);
        String ccosto=config.isEmpty()?"0":config.get(0).getCentroCostoSucursal();
        final TextField inventario = new TextField("Inventario del medio proveniente");
        inventario.setNullRepresentation("");
        inventario.setImmediate(true);
        inventario.setWidth("100%");
        inventario.setStyleName("form-control");
        final ComboBox tipo = new ComboBox("Elija el tipo de pieza");
        tipo.addItem("Seleccione");
        tipo.setNullSelectionItemId("Seleccione");
        tipo.addItem("Motherboard");
        tipo.addItem("Procesador");
        tipo.addItem("Memoria RAM");
        tipo.addItem("Disco Duro");
        tipo.addItem("Fuente Interna");
        tipo.addItem("Unidad Optica");
        tipo.addItem("Tarjeta Gráfica");
        tipo.addItem("Módem Interno");
        tipo.addItem("Adaptador de Red");
        tipo.addItem("Tarjeta LAVA");
        tipo.addItem("Cabezal");
        tipo.addItem("Mecanismo");
        tipo.addItem("Placa Lógica");
        tipo.addItem("Fusor térmico");
        tipo.addItem("Display");
        tipo.setWidth("100%");
        final TextArea detalles = new TextArea("Detalles");
        detalles.setNullRepresentation("");
        detalles.setImmediate(true);
        detalles.setSizeFull();
        detalles.setStyleName("form-control");
        //metes pal formulario los campos
        //setear el contenido del panel
        formularioBasico.addComponents(inventario,tipo,detalles);
        datosBasico.setContent(formularioBasico);
        //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
        //por defecto estos atributos tienen valor null
        
        final BeanFieldGroup<PiezaRecuperada> validador=new BeanFieldGroup<>(PiezaRecuperada.class);
        validador.setItemDataSource(new PiezaRecuperada());
        validador.bind(inventario, "inventario");
        validador.bind(detalles, "detalles");
        inventario.setValue(ccosto+"-");
        NativeButton registrar=new NativeButton("Registrar", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                	if(tipo.getValue()!=null){
                	//registrar en la BD
                	//ejecutar validacion
                    validador.commit();	
                    //para validar el Combobox
                    validador.getItemDataSource().getBean().setTipo(tipo.getValue().toString());
                    validador.getItemDataSource().getBean().setNoOrden("Disponible");
                    getFachada().registrarEntidad(validador.getItemDataSource().getBean());
                    Notificaciones.NotificarSubmit("Pieza de recuperada registrada correctamente");
                    
                    inventario.setValue("");
                    tipo.setValue("");
                    detalles.setValue("");
                	actualizar(validador.getItemDataSource().getBean().getClass());
                	}else{
                		Notificaciones.NotificarError("Debe elegir una categoría");
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
    Label nombreForm=new Label("Entrada de nueva pieza recuperada");
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
    	Label medios =new Label(fachada.cantidadElementos(clazz)+" Piezas recuperadas Registradas");
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
    
}
