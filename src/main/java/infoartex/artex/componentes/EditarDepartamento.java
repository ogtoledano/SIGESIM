package infoartex.artex.componentes;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.vistas.Administrar;

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


@SuppressWarnings("serial")
public class EditarDepartamento extends ComponenteGenerico {


	private final CustomLayout container;
    private final VerticalLayout main;
    
    //entidad a modificar
    private Departamento departamento;
    private BeanFieldGroup<Departamento> validador;
    
    public EditarDepartamento(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		//usar formulario de los layouts que tiene el tema
        container=new CustomLayout("formulario");
        departamento=new Departamento();
	}
    //debe llamarse asi setEntidad, en todos los que hagas
  	public void setEntidad(Entidad entidad){
  		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
  		this.departamento=(Departamento)entidad;
          validador=new BeanFieldGroup<Departamento>(Departamento.class);
  		validador.setItemDataSource(departamento);	
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
        final TextField nombre = new TextField("Nombre del departamento o local");
        nombre.setNullRepresentation("");
        nombre.setImmediate(true);
        //nombre.setRequired(true);
        nombre.setStyleName("form-control");
        nombre.setSizeFull();
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
         //ojo asi se bindea para que se cargen los datos viejos
         validador.bind(nombre, "nombre");
         validador.bind(gerencia, "gerencia");
         validador.bind(ccosto, "ccosto");
         
         NativeButton registrar=new NativeButton("Actualizar", new Button.ClickListener() {
             @Override
             public void buttonClick(Button.ClickEvent event) {
                 try {
                 	//registrar en la BD
                 	//ejecutar validacion
                     validador.commit();	
                     //no se registra, se actualiza
                     getFachada().actualizarEntidad(validador.getItemDataSource().getBean());
                     Notificaciones.NotificarSubmit("Se ha actualizado correctamente el departamento");
                     view.nuevoContenido(new ListarDepartamento(view));
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
         Label nombreForm=new Label("Editar un departamento");
         nombreForm.setStyleName("text-left encabezado");
         container.addComponent(nombreForm, "nombreFormulario");
         setCompositionRoot(container);
  	 
  	 }	

	
}
