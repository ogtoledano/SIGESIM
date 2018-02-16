package infoartex.artex.componentes;

import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.vistas.Administrar;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;


@SuppressWarnings("serial")
public class VerDatoDepartamento extends ComponenteGenerico {

private final CustomLayout main;
    
    //entidad a mostrar
    private Departamento departamento;
	
	public VerDatoDepartamento(final Administrar view) {
		super(view);
		main = new CustomLayout("formulario");
		//usar formulario de los layouts que tiene el tema
		departamento=new Departamento();
	}
	
	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		Label nombreFormulario=new Label("Ver datos del departamento");
		nombreFormulario.setStyleName("text-left encabezado");
		main.addComponent(nombreFormulario, "nombreFormulario");
		Panel datosBasico = new Panel("Datos del departamento");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
		Label nombre=new Label("Nombre: "+ departamento.getNombre());
		Label ubicacion=new Label("Ubicación: "+ departamento.getGerencia());
		Label ccosto=new Label("Centro de Costo: "+ departamento.getCcosto());
		nombre.setStyleName("text text-info");
		ubicacion.setStyleName("text text-info");
		ccosto.setStyleName("text text-info");
		formularioBasico.addComponents(nombre,ubicacion,ccosto);
		datosBasico.setContent(formularioBasico);
		datosBasico.setWidth("100%");
		main.addComponent(datosBasico,"formulario");
		setCompositionRoot(main);
	}
	//obligado
	public void setEntidad(Entidad entidad){
		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
		this.departamento=(Departamento)entidad;
	}
}
