package infoartex.artex.componentes;

import java.text.SimpleDateFormat;

import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.ReporteMantenimiento;
import infoartex.artex.vistas.Administrar;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

@SuppressWarnings("serial")
public class VerDatosMantenimientoEfectuado extends ComponenteGenerico {

private final CustomLayout main;
    
    //entidad a mostrar
    private ReporteMantenimiento repomantenimiento;
	
    public VerDatosMantenimientoEfectuado(final Administrar view) {
		super(view);
		main = new CustomLayout("formulario");
		//usar formulario de los layouts que tiene el tema
		repomantenimiento=new ReporteMantenimiento();
	}
    
    
    public void initComponents(final Administrar view) {
  		main.removeAllComponents();
  		Label nombreFormulario=new Label("Ver datos del mantenimiento efectuado");
		nombreFormulario.setStyleName("text-left encabezado");
		main.addComponent(nombreFormulario, "nombreFormulario");
  		Panel datosBasico = new Panel("Datos del mantenimiento efectuado");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
        SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
  		Label fecha=new Label("Fecha"+ format.format(repomantenimiento.getFecha()));
  		Label tipomedio=new Label("Tipo de Medio: "+ repomantenimiento.getTipomedio());
  		Label inventario=new Label("No.Inventario: "+ repomantenimiento.getInventario());
  		Label departamento=new Label("Departamento: "+ repomantenimiento.getDepartamento().getNombre());
  		Label observacion=new Label("Observación: "+ repomantenimiento.getObservacion());
  		inventario.setStyleName("label label-info");
  		formularioBasico.addComponents(inventario,fecha,tipomedio,departamento,observacion);
  		datosBasico.setContent(formularioBasico);
  		datosBasico.setWidth("100%");
  		main.addComponent(datosBasico,"formulario");
  		setCompositionRoot(main);
  	}
  	//obligado
  	public void setEntidad(Entidad entidad){
  		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
  		this.repomantenimiento=(ReporteMantenimiento)entidad;
  	}
    
    
}
