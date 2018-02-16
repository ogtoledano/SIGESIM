package infoartex.artex.componentes;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.PiezaRepuesto;
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
public class EditarPiezaRepuesto extends ComponenteGenerico{
	
	
	private final CustomLayout container;
    private final VerticalLayout main;
    
    //entidad a modificar
    private PiezaRepuesto piezarepuesto;
    private BeanFieldGroup<PiezaRepuesto> validador;
    
    public EditarPiezaRepuesto(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		//usar formulario de los layouts que tiene el tema
        container=new CustomLayout("formulario");
        piezarepuesto=new PiezaRepuesto();
	}
    //debe llamarse asi setEntidad, en todos los que hagas
  	public void setEntidad(Entidad entidad){
  		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
  		this.piezarepuesto=(PiezaRepuesto)entidad;
          validador=new BeanFieldGroup<PiezaRepuesto>(PiezaRepuesto.class);
  		validador.setItemDataSource(piezarepuesto);	
  	}
  	
  	public void initComponents(final Administrar view) {
  		//se crea una unica instancia y este metodo lo llama java reflection
   		main.removeAllComponents();
   		container.removeAllComponents();
   		
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
         final ComboBox tipo = new ComboBox("Elija el tipo de medio");
         tipo.addItem("Seleccione");
         tipo.setNullSelectionItemId("Seleccione");
         tipo.addItem("Motherboard");
         tipo.addItem("Procesador");
         tipo.addItem("Memoria RAM");
         tipo.addItem("Disco Duro");
         tipo.addItem("Fuente Interna");
         tipo.addItem("Unidad Optica");
         tipo.addItem("Monitor");
         tipo.addItem("Mouse");
         tipo.addItem("Teclado");
         tipo.addItem("UPS");
         tipo.addItem("Impresora");
         tipo.addItem("Scanner");
         tipo.addItem("Switch");
         tipo.addItem("Modem Externo");
         tipo.addItem("Modem Interno");
         tipo.addItem("Adaptador de Red");
         tipo.addItem("Targeta de Video");
         tipo.addItem("Video BIN");
         tipo.setWidth("100%");
     
         final TextField cantidad = new TextField("Cantidad");
         cantidad.setNullRepresentation("");
         cantidad.setImmediate(true);
         cantidad.setWidth("100%");
         cantidad.setStyleName("form-control");
         final TextArea detalles = new TextArea("Detalles");
         detalles.setNullRepresentation("");
         detalles.setImmediate(true);
         detalles.setStyleName("form-control");
         detalles.setSizeFull();
         //metes pal formulario los campos
         //setear el contenido del panel
         formularioBasico.addComponents(codigo,factura,tipo,cantidad,detalles);
         datosBasico.setContent(formularioBasico);
         
       //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
         //por defecto estos atributos tienen valor null
         //ojo asi se bindea para que se cargen los datos viejos
         validador.bind(codigo, "codigo");
         validador.bind(factura, "factura");
         validador.bind(cantidad, "cantidad");
         validador.bind(tipo, "tipo");
         //revisar
         //validador.bind(cantidad, "cantidad");
         validador.bind(detalles, "detalles");
         tipo.setValue(validador.getItemDataSource().getBean().getTipo());
         //lo de arriba es para que los combos cojan los valores de la entidad por defecto.
         
         NativeButton registrar=new NativeButton("Actualizar", new Button.ClickListener() {
             @Override
             public void buttonClick(Button.ClickEvent event) {
                 try {
                 	if(tipo.getValue()!=null){
                 	//registrar en la BD
                 	//ejecutar validacion
                     validador.commit();	
                     //no se registra, se actualiza
                     getFachada().actualizarEntidad(validador.getItemDataSource().getBean());
                     Notificaciones.NotificarSubmit("Se ha actualizado correctamente la pieza de repuesto");
                     view.nuevoContenido(new ListarPiezaRepuesto(view));
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
         Label nombreForm=new Label("Modificar una pieza de repuesto");
         nombreForm.setStyleName("text-left encabezado");
         container.addComponent(nombreForm, "nombreFormulario");
         setCompositionRoot(container);
  	 
  	 }
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	

}
