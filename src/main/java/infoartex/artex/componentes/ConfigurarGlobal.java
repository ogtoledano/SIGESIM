package infoartex.artex.componentes;
import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.ConfiguracionGlobal;
import infoartex.artex.vistas.Administrar;

import java.util.List;
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
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class ConfigurarGlobal extends ComponenteGenerico{
	private final CustomLayout container;
    private final VerticalLayout main;
    
    //entidad a modificar
    private ConfiguracionGlobal configuracion;
    private BeanFieldGroup<ConfiguracionGlobal> validador;
	private List<ConfiguracionGlobal> lista;
	
	@SuppressWarnings("unchecked")
	public ConfigurarGlobal(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		container=new CustomLayout("formulario");
		lista=(List<ConfiguracionGlobal>) fachada.listarTodos(ConfiguracionGlobal.class);
		configuracion= lista.size()!=0?lista.get(0):new ConfiguracionGlobal();
		validador=new BeanFieldGroup<>(ConfiguracionGlobal.class);
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
		 Label nombreForm=new Label("Configuración global del sistema");
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
        final TextField ccosto = new TextField("Centro de costo de la sucursal");
        ccosto.setNullRepresentation("");
        ccosto.setImmediate(true);
        //nombre.setRequired(true);
        ccosto.setStyleName("form-control");
        ccosto.setSizeFull();
        
        final TextField sucursal = new TextField("Nombre de la sucursal");
        sucursal.setNullRepresentation("");
        sucursal.setImmediate(true);
        //nombre.setRequired(true);
        sucursal.setStyleName("form-control");
        sucursal.setSizeFull();
        
        formularioBasico.addComponents(sucursal,ccosto);
        datosBasico.setContent(formularioBasico);
         
       //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
         //por defecto estos atributos tienen valor null
         //ojo asi se bindea para que se cargen los datos viejos
         validador.bind(ccosto, "centroCostoSucursal");
         validador.bind(sucursal, "nombreSucursal");
         
         NativeButton registrar=new NativeButton("Guardar", new Button.ClickListener() {
             @Override
             public void buttonClick(Button.ClickEvent event) {
                 try {
                     validador.commit();
                     getFachada().actualizarEntidad(validador.getItemDataSource().getBean());
                     Notificaciones.NotificarSubmit("Se ha actualizado correctamente la configuración global del sistema");
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
         reg.addComponents(registrar);
         reg.setSpacing(true);
         reg.setStyleName("controles");
         datosBasico.setWidth("100%");
         main.addComponents(datosBasico,reg);
         container.addComponent(main, "formulario");
         Label nombreForm=new Label("Configuración global del sistema");
         nombreForm.setStyleName("text-left encabezado");
         container.addComponent(nombreForm, "nombreFormulario");
         setCompositionRoot(container);
	}
}
