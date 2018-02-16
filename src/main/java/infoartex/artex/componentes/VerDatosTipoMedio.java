package infoartex.artex.componentes;

import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.TipoMedio;
import infoartex.artex.vistas.Administrar;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;


@SuppressWarnings("serial")
public class VerDatosTipoMedio extends ComponenteGenerico {

private final CustomLayout main;
    
    //entidad a mostrar
    private TipoMedio tipomedio;
	
	public VerDatosTipoMedio(final Administrar view) {
		super(view);
		main = new CustomLayout("formulario");
		//usar formulario de los layouts que tiene el tema
		tipomedio=new TipoMedio();
	}
	
	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		Label nombreFormulario=new Label("Ver datos del tipo de medio");
		nombreFormulario.setStyleName("text-left encabezado");
		main.addComponent(nombreFormulario, "nombreFormulario");
		Panel datosBasico = new Panel("Datos del medio");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
		Label denominacion=new Label("Nombre: "+ tipomedio.getDenominacion());
		denominacion.setStyleName("text text-info");
		formularioBasico.addComponents(denominacion);
		datosBasico.setContent(formularioBasico);
		datosBasico.setWidth("100%");
		main.addComponent(datosBasico,"formulario");
		setCompositionRoot(main);
	}
	//obligado
	public void setEntidad(Entidad entidad){
		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
		this.tipomedio=(TipoMedio)entidad;
	}
}
