package infoartex.artex.componentes;

import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.PiezaRecuperada;
import infoartex.artex.vistas.Administrar;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;

@SuppressWarnings("serial")
public class VerDatoPiezaRecuperada extends ComponenteGenerico {
	
private final CustomLayout main;
    
    //entidad a mostrar
    private PiezaRecuperada piezarecuperada;
	
	public VerDatoPiezaRecuperada(final Administrar view) {
		super(view);
		main = new CustomLayout("formulario");
		//usar formulario de los layouts que tiene el tema
		piezarecuperada=new PiezaRecuperada();
	}
	
	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		Label nombreFormulario=new Label("Ver datos de la pieza recuperada");
		nombreFormulario.setStyleName("text-left encabezado");
		main.addComponent(nombreFormulario, "nombreFormulario");
		Panel datosBasico = new Panel("Datos de las pieza recuperada");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
		Label inventario=new Label("No.Inventario: "+ piezarecuperada.getInventario());
		Label tipo=new Label("Tipo de pieza: "+ piezarecuperada.getTipo());
		Label captionDetalles=new Label("Detalles:");
		TextArea detalles=new TextArea();
		detalles.setValue(""+piezarecuperada.getDetalles());
		detalles.setEnabled(false);
		inventario.setStyleName("label label-info");
		formularioBasico.addComponents(inventario,tipo,captionDetalles,detalles);
		datosBasico.setContent(formularioBasico);
		datosBasico.setWidth("100%");
		main.addComponent(datosBasico,"formulario");
		setCompositionRoot(main);
	}
	//obligado
	public void setEntidad(Entidad entidad){
		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
		this.piezarecuperada=(PiezaRecuperada)entidad;
	}
	
	
}
