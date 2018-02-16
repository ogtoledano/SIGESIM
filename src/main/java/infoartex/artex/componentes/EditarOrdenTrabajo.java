package infoartex.artex.componentes;

import java.util.List;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
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
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.OrdenTrabajo;
import infoartex.artex.dominio.PiezaRecuperada;
import infoartex.artex.dominio.PiezaRepuesto;
import infoartex.artex.vistas.Administrar;



@SuppressWarnings("serial")
public class EditarOrdenTrabajo extends ComponenteGenerico{
	
	private final CustomLayout container;
    private final VerticalLayout main;
    private OrdenTrabajo orden;
    private  Panel piezasPanel;
    private  Panel recuperadasPanel;
    private BeanFieldGroup<OrdenTrabajo> validador;
	public EditarOrdenTrabajo(Administrar view) {
		super(view);
		main = new VerticalLayout();
		//usar formulario de los layouts que tiene el tema
        container=new CustomLayout("formulario");
        //metodo que arranca los componentes de vaadin
	}
	
	//debe llamarse asi setEntidad, en todos los que hagas
  	public void setEntidad(Entidad entidad){
  		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
  		this.orden=(OrdenTrabajo)entidad;
        validador=new BeanFieldGroup<OrdenTrabajo>(OrdenTrabajo.class);
  		validador.setItemDataSource(orden);	
  	}
  	
  	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		Panel datosBasico = new Panel("Rellene los campos");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
        
        final TextField numero = new TextField("Número consecutivo");
        numero.setNullRepresentation("");
        numero.setEnabled(false);
        numero.setValue(orden.getNumero()+"");
        numero.setEnabled(false);
        //numero.setRequired(true);
        numero.setStyleName("form-control");
        final DateField fecha = new DateField("Fecha");
        fecha.setImmediate(true);
        
        final TextField inventario = new TextField("No. Inventario");
        inventario.setNullRepresentation("");
        inventario.setImmediate(true);
        inventario.setStyleName("form-control");
        inventario.setWidth("100%");
        //inventario.setRequired(true);
        final ComboBox tipo = new ComboBox("Elija el tipo de medio");
        tipo.addItem("Seleccione");
        tipo.setNullSelectionItemId("Seleccione");
        tipo.addItem("Torre");
        tipo.addItem("Monitor");
        tipo.addItem("Mouse");
        tipo.addItem("Teclado");
        tipo.addItem("UPS");
        tipo.addItem("Impresora");
        tipo.addItem("Scanner");
        tipo.addItem("Switch");
        tipo.addItem("Modem Externo");
        tipo.addItem("Video BIN");
        final TextArea dictamen = new TextArea("Dictamen Técnico");
        dictamen.setNullRepresentation("");
        dictamen.setImmediate(true);
        dictamen.setWidth("100%");
        dictamen.setStyleName("form-control");
        //dictamen.setRequired(true);
        //Si el dictamen es mucho mas grande, que creo yo, usa mejor un texarea.
        final TextArea acciones = new TextArea("Acciones realizadas");
        acciones.setNullRepresentation("");
        acciones.setImmediate(true);
        acciones.setStyleName("form-control");
        acciones.setWidth("100%");
        //acciones.setRequired(true);  
        final DateField fechasalida = new DateField("Fecha de salida");
        fechasalida.setImmediate(true);
        final TextField trabajador = new TextField("Entregado al trabajador");
        trabajador.setNullRepresentation("");
        trabajador.setImmediate(true);
        trabajador.setStyleName("form-control");
        trabajador.setWidth("100%");
        
      //panel de piesas repuesto
        piezasPanel = new Panel("Piezas de repuesto utilizadas");
        piezasPanel.setSizeFull();
        FormLayout formularioPiezas = new FormLayout();
	    formularioPiezas.setMargin(true);
	    formularioPiezas.setWidth("100%");
        piezasPanel.setContent(formularioPiezas);
        
        //panel recuperadas
        recuperadasPanel = new Panel("Piezas recuperadas utilizadas");
        recuperadasPanel.setSizeFull();
        FormLayout formularioRecuperadas = new FormLayout();
        formularioRecuperadas.setMargin(true);
        formularioRecuperadas.setWidth("100%");
        recuperadasPanel.setContent(formularioRecuperadas);
        llenarDatosPiezas();
        formularioBasico.addComponents(numero,fecha,inventario,tipo,dictamen,acciones,fechasalida,trabajador);
        datosBasico.setContent(formularioBasico);
        validador.bind(fecha, "fecha");
        validador.bind(inventario, "inventario");
        validador.bind(tipo, "tipomedio");
        validador.bind(dictamen, "dictamen");
        validador.bind(acciones, "acciones");
        validador.bind(fechasalida, "fechasalida");
        validador.bind(trabajador, "trabajadorentregado");
        
        NativeButton registrar=new NativeButton("Actualizar", new Button.ClickListener() {

        	@Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                	if(tipo.getValue()!=null){
                	//registrar en la BD
                	//ejecutar validacion
                    validador.commit();	
                    //para validar el Combobox
                    validador.getItemDataSource().getBean().setTipomedio(tipo.getValue().toString());
                    getFachada().actualizarEntidad(validador.getItemDataSource().getBean());
                    Notificaciones.NotificarSubmit("Orden de trabajo actualizada correctamente");
                	}else{
                		Notificaciones.NotificarError("Debe elegir el tipo de pieza");
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
        registrar.setIcon(FontAwesome.EDIT);
        cancelar.setIcon(FontAwesome.CLOSE);
        HorizontalLayout controles=new HorizontalLayout();
        controles.addComponents(registrar,cancelar);
        Label nombreForm=new Label("Entrada de orden de trabajo para la interrupción");
        nombreForm.setStyleName("text-left encabezado");
        controles.setSpacing(true);
        controles.setStyleName("controles");
        datosBasico.setWidth("100%");
        main.addComponents(datosBasico,piezasPanel,recuperadasPanel,controles);
        container.addComponent(main,"formulario");
        container.addComponent(nombreForm,"nombreFormulario");
        setCompositionRoot(container);
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
        
	public void llenarDatosPiezas(){
		List<PiezaRecuperada> l1=orden.getPiezasRecuperada();
		List<PiezaRepuesto> l2=orden.getPiezasRepuesto();
		FormLayout form1=(FormLayout)recuperadasPanel.getContent();
		if(l1.isEmpty()){
			Label sinPiezas=new Label("No hay piezas elegidas");
			form1.addComponent(sinPiezas);
		}
		for(PiezaRecuperada p:l1){
			Label pieza=new Label("Pieza: "+p.getTipo());
			form1.addComponent(pieza);
		}
		
		FormLayout form2=(FormLayout)piezasPanel.getContent();
		if(l2.isEmpty()){
			Label sinPiezas=new Label("No hay piezas elegidas");
			form2.addComponent(sinPiezas);
		}
		for(PiezaRepuesto p:l2){
			Label pieza=new Label("Pieza: "+p.getTipo()+" Cantidad: "+p.getCantidad());
			form2.addComponent(pieza);
		}
	}
}
