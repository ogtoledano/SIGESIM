package infoartex.artex.componentes;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.PiezaRecuperada;
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
public class EditarPiezaRecuperada extends ComponenteGenerico {

	private final CustomLayout container;
    private final VerticalLayout main;
    
    //entidad a modificar
    private PiezaRecuperada piezarecuperada;
    private BeanFieldGroup<PiezaRecuperada> validador;
    
    public EditarPiezaRecuperada(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		//usar formulario de los layouts que tiene el tema
        container=new CustomLayout("formulario");
        piezarecuperada=new PiezaRecuperada();
	}
    //debe llamarse asi setEntidad, en todos los que hagas
  	public void setEntidad(Entidad entidad){
  		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
  		this.piezarecuperada=(PiezaRecuperada)entidad;
          validador=new BeanFieldGroup<PiezaRecuperada>(PiezaRecuperada.class);
  		validador.setItemDataSource(piezarecuperada);	
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
         detalles.setStyleName("form-control");
         detalles.setSizeFull();
         //metes pal formulario los campos
         //setear el contenido del panel
         formularioBasico.addComponents(inventario,tipo,detalles);
         datosBasico.setContent(formularioBasico);
         
       //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
         //por defecto estos atributos tienen valor null
         //ojo asi se bindea para que se cargen los datos viejos
         validador.setItemDataSource(piezarecuperada);
	        validador.bind(inventario, "inventario");
	        validador.bind(tipo, "tipo");
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
                     Notificaciones.NotificarSubmit("Se ha actualizado correctamente la pieza recuperada");
                     view.nuevoContenido(new ListarPiezaRecuperada(view));
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
         Label nombreForm=new Label("Editar una pieza recuperada");
         nombreForm.setStyleName("text-left encabezado");
         container.addComponent(nombreForm, "nombreFormulario");
         setCompositionRoot(container);
  	 
  	 }
  	 
      
     
	}
  	 
  	 	 
    

