package infoartex.artex.componentes;

import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.PiezaRepuesto;
import infoartex.artex.vistas.Administrar;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;

@SuppressWarnings("serial")
public class VerDatoPiezaRepuesto extends ComponenteGenerico {

private final CustomLayout main;
    
    //entidad a mostrar
    private PiezaRepuesto piezarepuesto;
	
	public VerDatoPiezaRepuesto(final Administrar view) {
		super(view);
		main = new CustomLayout("formulario");
		//usar formulario de los layouts que tiene el tema
		piezarepuesto=new PiezaRepuesto();
	}
	
	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		Label nombreFormulario=new Label("Ver datos de la PC");
		nombreFormulario.setStyleName("text-left encabezado");
		main.addComponent(nombreFormulario, "nombreFormulario");
		Panel datosBasico = new Panel("Datos de las piezas de repuesto");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
		Label codigo=new Label("Código: "+ piezarepuesto.getCodigo());
		Label tipo=new Label("Tipo de pieza: "+ piezarepuesto.getTipo());
		Label cantidad=new Label("Cantidad: "+ piezarepuesto.getCantidad());
		Label factura=new Label("Factura: "+ piezarepuesto.getFactura());
		Label captionDetalles=new Label("Detalles:");
		TextArea detalles=new TextArea();
		detalles.setValue(""+piezarepuesto.getDetalles());
		detalles.setEnabled(false);
		codigo.setStyleName("label label-info");
		formularioBasico.addComponents(codigo,tipo,cantidad,factura,captionDetalles,detalles);
		datosBasico.setContent(formularioBasico);
		datosBasico.setWidth("100%");
		main.addComponent(datosBasico,"formulario");
		setCompositionRoot(main);
	}
	//obligado
	public void setEntidad(Entidad entidad){
		//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
		this.piezarepuesto=(PiezaRepuesto)entidad;
	}
	
	
	
}
