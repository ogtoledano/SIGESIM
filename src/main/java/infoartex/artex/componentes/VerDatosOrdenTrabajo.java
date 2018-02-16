package infoartex.artex.componentes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.OrdenTrabajo;
import infoartex.artex.dominio.PiezaRecuperada;
import infoartex.artex.dominio.PiezaRepuesto;
import infoartex.artex.reportes.ControladorJasperReporter;
import infoartex.artex.reportes.ReporteOrdenTrabajo;
import infoartex.artex.vistas.Administrar;

@SuppressWarnings("serial")
public class VerDatosOrdenTrabajo extends ComponenteGenerico {

	private final CustomLayout main;
	private OrdenTrabajo orden;

	private Panel piezasPanel;
	private Panel recuperadasPanel;

	public VerDatosOrdenTrabajo(final Administrar view) {
		super(view);
		main = new CustomLayout("formulario");
	}

	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		final VerticalLayout container = new VerticalLayout();
		Label nombreFormulario = new Label("Ver datos del medio informático");
		nombreFormulario.setStyleName("text-left encabezado");
		main.addComponent(nombreFormulario, "nombreFormulario");
		Panel datosBasico = new Panel("Datos del medio");
		datosBasico.setSizeUndefined();
		final FormLayout formularioBasico = new FormLayout();
		formularioBasico.setMargin(true);
		formularioBasico.setWidth("100%");
		Label inventario = new Label("No. Inventario: " + orden.getInventario());
		Label noOrden = new Label("No. Orden: " + orden.getNumero());
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Label fecha = new Label("Fecha: " + format.format(orden.getFecha()));
		Label tipoMedio = new Label("Tipo medio: " + orden.getTipomedio());
		Label fechaSalida = new Label("Fecha de salida: " + format.format(orden.getFechasalida()));
		Label trabajador = new Label("Trabajador entregado: " + orden.getTrabajadorentregado());
		Label estado = new Label("Estado: " + orden.getEstado());
		inventario.setStyleName("label label-info");
		formularioBasico.addComponents(inventario, noOrden, fecha, tipoMedio, fechaSalida, trabajador, estado);
		datosBasico.setContent(formularioBasico);
		datosBasico.setWidth("100%");
		container.addComponent(datosBasico);

		TextArea dictamen = new TextArea("Dictamen técnico: ");
		dictamen.setValue(orden.getDictamen());
		dictamen.setEnabled(false);

		TextArea acciones = new TextArea("Acciones realizadas: ");
		acciones.setValue(orden.getDictamen());
		acciones.setEnabled(false);

		final Panel info = new Panel("Dictamen y acciones");
		info.setSizeUndefined();
		FormLayout datosInfo = new FormLayout();
		datosInfo.setMargin(true);
		datosInfo.setWidth("100%");
		datosInfo.addComponents(dictamen, acciones);
		info.setContent(datosInfo);
		info.setWidth("100%");

		final NativeButton masInfo = new NativeButton("Más información");
		masInfo.setIcon(FontAwesome.PLUS_CIRCLE);
		masInfo.setStyleName("botonesOpcionesTabla");

		final NativeButton cerrarInfo = new NativeButton("Menos información");
		cerrarInfo.setIcon(FontAwesome.MINUS_CIRCLE);
		cerrarInfo.setStyleName("botonesOpcionesTabla");

		// panel de piesas repuesto
		piezasPanel = new Panel("Piezas de repuesto utilizadas");
		piezasPanel.setSizeFull();
		FormLayout formularioPiezas = new FormLayout();
		formularioPiezas.setMargin(true);
		formularioPiezas.setWidth("100%");
		piezasPanel.setContent(formularioPiezas);

		// panel recuperadas
		recuperadasPanel = new Panel("Piezas recuperadas utilizadas");
		recuperadasPanel.setSizeFull();
		FormLayout formularioRecuperadas = new FormLayout();
		formularioRecuperadas.setMargin(true);
		formularioRecuperadas.setWidth("100%");
		recuperadasPanel.setContent(formularioRecuperadas);
		llenarDatosPiezas();

		masInfo.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				container.replaceComponent(masInfo, info);
				container.addComponents(piezasPanel,recuperadasPanel,cerrarInfo);
			}
		});
		cerrarInfo.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				container.replaceComponent(info, masInfo);
				container.removeComponent(cerrarInfo);
				container.removeComponent(piezasPanel);
				container.removeComponent(recuperadasPanel);
			}
		});
		final NativeButton descargar = new NativeButton("Descargar");
		descargar.setIcon(FontAwesome.FILE_PDF_O);
		descargar.setStyleName("btn btn-primary");
		final FileDownloader down = new FileDownloader(new FileResource(new File("")));
		final NativeButton acta = new NativeButton("Generar Acta", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
						String urlReport = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/classes/infoartex/artex/reportes/";
						ControladorJasperReporter reporter = new ControladorJasperReporter("");
						ReporteOrdenTrabajo acta1 = new ReporteOrdenTrabajo(orden);
						reporter.generarReportePDF(urlReport + "OrdenTrabajo-" + orden.getNumero() + ".pdf", acta1.getJasperPrint());
						FileResource st = new FileResource(new File(urlReport + "OrdenTrabajo-" + orden.getNumero() + ".pdf"));
						down.setFileDownloadResource(st);
						down.extend(descargar);
						Label documento=new Label("OrdenTrabajo-" + orden.getNumero() + ".pdf");
						documento.setStyleName("text text-primary");
						HorizontalLayout hor=new HorizontalLayout();
						hor.setSpacing(true);
						hor.addComponents(descargar,documento);
						hor.setComponentAlignment(documento, Alignment.MIDDLE_LEFT);
						formularioBasico.addComponent(hor);
						formularioBasico.removeComponent(event.getButton());
					
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		acta.setStyleName("btn btn-primary");
		acta.setIcon(FontAwesome.DOWNLOAD);
		formularioBasico.addComponent(acta);
		container.addComponent(masInfo);
		main.addComponent(container, "formulario");
		setCompositionRoot(main);
	}

	// obligado
	public void setEntidad(Entidad entidad) {
		// lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la
		// entidad
		this.orden = (OrdenTrabajo) entidad;
	}

	public void llenarDatosPiezas() {
		List<PiezaRecuperada> l1 = orden.getPiezasRecuperada();
		List<PiezaRepuesto> l2 = orden.getPiezasRepuesto();
		FormLayout form1 = (FormLayout) recuperadasPanel.getContent();
		if (l1.isEmpty()) {
			Label sinPiezas = new Label("No hay piezas elegidas");
			form1.addComponent(sinPiezas);
		}
		for (PiezaRecuperada p : l1) {
			Label pieza = new Label("Pieza: " + p.getTipo());
			form1.addComponent(pieza);
		}

		FormLayout form2 = (FormLayout) piezasPanel.getContent();
		if (l2.isEmpty()) {
			Label sinPiezas = new Label("No hay piezas elegidas");
			form2.addComponent(sinPiezas);
		}
		for (PiezaRepuesto p : l2) {
			Label pieza = new Label("Pieza: " + p.getTipo() + " Cantidad: " + p.getCantidad());
			form2.addComponent(pieza);
		}
	}

}
