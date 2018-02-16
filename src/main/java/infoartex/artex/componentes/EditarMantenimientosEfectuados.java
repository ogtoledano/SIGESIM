package infoartex.artex.componentes;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.ReporteMantenimiento;
import infoartex.artex.vistas.Administrar;

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



@SuppressWarnings("serial")
public class EditarMantenimientosEfectuados extends ComponenteGenerico {

	
	private final CustomLayout container;
    private final VerticalLayout main;
    
    //entidad a modificar
    private ReporteMantenimiento repomantenimiento;
    private BeanFieldGroup<ReporteMantenimiento> validador;
    
    public EditarMantenimientosEfectuados(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		//usar formulario de los layouts que tiene el tema
        container=new CustomLayout("formulario");
        repomantenimiento=new ReporteMantenimiento();
	}
    
  //debe llamarse asi setEntidad, en todos los que hagas
  	public void setEntidad(Entidad entidad){
  		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
  		this.repomantenimiento=(ReporteMantenimiento)entidad;
          validador=new BeanFieldGroup<ReporteMantenimiento>(ReporteMantenimiento.class);
  		validador.setItemDataSource(repomantenimiento);	
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
          final DateField fecha = new DateField("Fecha");
	        fecha.setImmediate(true);
	        GregorianCalendar gc=new GregorianCalendar();
	        gc.setTime(new Date());
	        fecha.setValue(gc.getTime());
	        
	        final ComboBox tipomedio = new ComboBox("Elija el tipo de medio");
	        tipomedio.addItem("Seleccione");
	        tipomedio.setNullSelectionItemId("Seleccione");
	        tipomedio.addItem("Torre");
	        tipomedio.addItem("Impresora");
	        tipomedio.addItem("Lapto");
	        tipomedio.setImmediate(true);
	        tipomedio.setWidth("100%");
	        
	        final TextField inventario = new TextField("Inventario");
	        inventario.setNullRepresentation("");
	        inventario.setImmediate(true);
	        inventario.setWidth("100%");
	        inventario.setStyleName("form-control");
	        
	        final ComboBox departamento=new ComboBox("Departamento");
	        departamento.addItem("Seleccione");
	        departamento.setNullSelectionItemId("Seleccione");
	        @SuppressWarnings("unused")
			final List<Departamento> lista=cargarDepartamentos(departamento);
	        departamento.setImmediate(true);
	        
	        final TextArea observacion = new TextArea("Observación");
	        observacion.setNullRepresentation("");
	        observacion.setImmediate(true);
	        observacion.setWidth("100%");
	        observacion.setStyleName("form-control");
	        
          
	      formularioBasico.addComponents(fecha,tipomedio,inventario,departamento,observacion);
          datosBasico.setContent(formularioBasico);
          //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
          //por defecto estos atributos tienen valor null
          //ojo asi se bindea para que se cargen los datos viejos
          validador.setItemDataSource(repomantenimiento);
            validador.bind(fecha, "fecha");
	        validador.bind(tipomedio, "tipomedio");
	        validador.bind(inventario, "inventario");
	        validador.bind(observacion, "observacion");
	        
          //poner el departamento de la entidad por defecto
          departamento.addItem(validador.getItemDataSource().getBean().getDepartamento().getNombre());
          departamento.setValue(validador.getItemDataSource().getBean().getDepartamento().getNombre());
          tipomedio.setValue(validador.getItemDataSource().getBean().getTipomedio());
          departamento.setEnabled(false);
          //lo de arriba es para que los combos cojan los valores de la entidad por defecto.
          NativeButton registrar=new NativeButton("Actualizar", new Button.ClickListener() {

              @Override
              public void buttonClick(Button.ClickEvent event) {
                  try {
                  	if(tipomedio.getValue()!=null){
                  	//registrar en la BD
                  	//ejecutar validacion
                      validador.commit();	
                      //no se registra, se actualiza
                      getFachada().actualizarEntidad(validador.getItemDataSource().getBean());
                      Notificaciones.NotificarSubmit("Se ha actualizado correctamente el mantenimiento efectuado");
                      view.nuevoContenido(new ListarMantenimientosEfectuados(view));
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
          Label nombreForm=new Label("Modificar mantenimiento efectuado");
          nombreForm.setStyleName("text-left encabezado");
          container.addComponent(nombreForm, "nombreFormulario");
          setCompositionRoot(container);
  	}
    
  	 private List<Departamento> cargarDepartamentos(ComboBox departamentos){
			@SuppressWarnings("unchecked")
			List<Departamento> lista=(List<Departamento>) getFachada().listarTodos(Departamento.class);
			for(Departamento d:lista){
				departamentos.addItem(d.getNombre());
			}
			return lista;
	    }
  	 
  	 
	
    
}
