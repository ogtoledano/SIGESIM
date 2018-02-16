package infoartex.artex.componentes;

import java.io.File;
import java.util.List;

import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Laptop;
import infoartex.artex.dominio.MedioInformatico;
import infoartex.artex.dominio.PC;
import infoartex.artex.dominio.Torre;
import infoartex.artex.reportes.ControladorJasperReporter;
import infoartex.artex.reportes.ReporteActaResponsabilidad;
import infoartex.artex.reportes.ReporteCaracteristicasPuestoTrabajo;
import infoartex.artex.reportes.ReporteSoftwareAutorizado;
import infoartex.artex.vistas.Administrar;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class VerDatosPC extends ComponenteGenerico {
	private final CustomLayout main;

	// entidad a mostrar
	private PC pc;

	public VerDatosPC(final Administrar view) {
		super(view);
		main = new CustomLayout("formulario");
		// usar formulario de los layouts que tiene el tema
		pc = new PC();
	}

	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		Label nombreFormulario = new Label("Ver datos de la PC");
		nombreFormulario.setStyleName("text-left encabezado");
		main.addComponent(nombreFormulario, "nombreFormulario");
		Panel datosBasico = new Panel("Datos básicos de la PC");
		datosBasico.setSizeUndefined();
		final VerticalLayout container = new VerticalLayout();
		final FormLayout formularioBasico = new FormLayout();
		formularioBasico.setMargin(true);
		formularioBasico.setWidth("100%");
		Label responsable = new Label("Responsable: " + pc.getResponsable());
		Label nombre = new Label("Nombre pc: " + pc.getNombre());
		Label dep = new Label("Departamento: " + pc.getDepartamento().getNombre());
		final NativeButton descargar = new NativeButton("Descargar");
		descargar.setIcon(FontAwesome.FILE_PDF_O);
		descargar.setStyleName("btn btn-primary");
		final NativeButton descargar2 = new NativeButton("Descargar");
		descargar2.setIcon(FontAwesome.FILE_PDF_O);
		descargar2.setStyleName("btn btn-danger");
		final NativeButton descargar3 = new NativeButton("Descargar");
		descargar3.setIcon(FontAwesome.FILE_PDF_O);
		descargar3.setStyleName("btn btn-success");
		final FileDownloader down = new FileDownloader(new FileResource(new File("")));
		final FileDownloader down2 = new FileDownloader(new FileResource(new File("")));
		final FileDownloader down3 = new FileDownloader(new FileResource(new File("")));
		final VerticalLayout controles = new VerticalLayout();
		final NativeButton masInfo = new NativeButton("Más información");
		masInfo.setIcon(FontAwesome.PLUS_CIRCLE);
		masInfo.setStyleName("botonesOpcionesTabla");
		final NativeButton closeInfo = new NativeButton("Ocultar información");
		closeInfo.setIcon(FontAwesome.MINUS_CIRCLE);
		closeInfo.setStyleName("botonesOpcionesTabla");
		final Panel mediosPanel = new Panel("Medios asociados a la PC");
		final FormLayout medios = new FormLayout();
		medios.setMargin(true);
		medios.setWidth("100%");
		List<MedioInformatico> mediosLista = pc.getMedios();
		Table tabla = new Table();
		tabla.setWidth("100%");
		tabla.addContainerProperty("Inventario", String.class, null);
		tabla.addContainerProperty("Tipo medio", String.class, null);
		tabla.addContainerProperty("Estado", String.class, null);
		tabla.addContainerProperty("Marca", String.class, null);
		tabla.addContainerProperty("Modelo", String.class, null);

		for (MedioInformatico m : mediosLista) {
			tabla.addItem(new Object[] { m.getInventario(), m.getTipo(), m.getEstado(), m.getMarca(), m.getModelo() }, m.getId());
		}
		tabla.setPageLength(tabla.size());
		HorizontalLayout expediente = new HorizontalLayout();
		final CheckBox actaResp = new CheckBox("Acta de responsabilidad");
		final CheckBox hojaHard = new CheckBox("Hoja de hardware");
		final CheckBox software = new CheckBox("Software autorizado");
		expediente.addComponents(actaResp, hojaHard, software);
		actaResp.setSizeUndefined();
		expediente.setSpacing(true);

		final NativeButton acta = new NativeButton("Generar Acta", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					int existe = 0;
					if (software.getValue()) {
						String urlReport = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/classes/infoartex/artex/reportes/";
						ControladorJasperReporter reporter = new ControladorJasperReporter("");
						ReporteSoftwareAutorizado acta1 = new ReporteSoftwareAutorizado(pc);
						reporter.generarReportePDF(urlReport + "SoftwareAutorizado-" + pc.getNombre() + ".pdf", acta1.getJasperPrint());
						FileResource st = new FileResource(new File(urlReport + "SoftwareAutorizado-" + pc.getNombre() + ".pdf"));
						down.setFileDownloadResource(st);
						down.extend(descargar);
						Label documento=new Label("SoftwareAutorizado-" + pc.getNombre() + ".pdf");
						documento.setStyleName("text text-primary");
						HorizontalLayout hor=new HorizontalLayout();
						hor.setSpacing(true);
						hor.addComponents(descargar,documento);
						hor.setComponentAlignment(documento, Alignment.MIDDLE_LEFT);
						controles.addComponent(hor);
						existe++;
					}
					if (hojaHard.getValue()) {
						String urlReport = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/classes/infoartex/artex/reportes/";
						ControladorJasperReporter reporter = new ControladorJasperReporter("");
						ReporteCaracteristicasPuestoTrabajo acta2 = new ReporteCaracteristicasPuestoTrabajo(pc);
						reporter.generarReportePDF(urlReport + "CaracteristicasPuestoTrabajo-" + pc.getNombre() + ".pdf", acta2.getJasperPrint());
						FileResource st = new FileResource(new File(urlReport + "CaracteristicasPuestoTrabajo-" + pc.getNombre() + ".pdf"));
						down2.setFileDownloadResource(st);
						down2.extend(descargar2);
						Label documento=new Label("CaracteristicasPuestoTrabajo-" + pc.getNombre() + ".pdf");
						documento.setStyleName("text text-danger");
						HorizontalLayout hor=new HorizontalLayout();
						hor.setSpacing(true);
						hor.addComponents(descargar2,documento);
						hor.setComponentAlignment(documento, Alignment.MIDDLE_LEFT);
						controles.addComponent(hor);
						existe++;
					}
					if (actaResp.getValue()) {
						String urlReport = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/classes/infoartex/artex/reportes/";
						ControladorJasperReporter reporter = new ControladorJasperReporter("");
						ReporteActaResponsabilidad acta3 = new ReporteActaResponsabilidad(pc);
						reporter.generarReportePDF(urlReport + "ActaResponsabilidad-" + pc.getNombre() + ".pdf", acta3.getJasperPrint());
						FileResource st = new FileResource(new File(urlReport + "ActaResponsabilidad-" + pc.getNombre() + ".pdf"));
						down3.setFileDownloadResource(st);
						down3.extend(descargar3);
						Label documento=new Label("ActaResponsabilidad-" + pc.getNombre() + ".pdf");
						documento.setStyleName("text text-success");
						HorizontalLayout hor=new HorizontalLayout();
						hor.setSpacing(true);
						hor.addComponents(descargar3,documento);
						hor.setComponentAlignment(documento, Alignment.MIDDLE_LEFT);
						controles.addComponent(hor);
						existe++;
					}
					if (existe != 0) {
						controles.removeComponent(event.getButton());
					}
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		acta.setStyleName("btn btn-primary");
		acta.setIcon(FontAwesome.DOWNLOAD);
		controles.setStyleName("controles");
		controles.addComponents(expediente, acta);
		controles.setSpacing(true);
		formularioBasico.addComponents(responsable, nombre, dep);
		datosBasico.setContent(formularioBasico);
		datosBasico.setWidth("100%");
		medios.addComponent(tabla);
		mediosPanel.setContent(medios);
		// aqui empiezo yo
		final Panel detalleshw = new Panel("Detalles del hardware");
		final FormLayout detalles = new FormLayout();
		detalles.setMargin(true);
		detalles.setWidth("100%");
		if (pc instanceof Laptop) {
			Label inventario = new Label("Inventario: " + ((Laptop) pc).getInventario());
			Label board = new Label("Modelo: " + ((Laptop) pc).getModelo() + " Serie: " + ((Laptop) pc).getSerie() + " Modelo Placa: " + ((Laptop) pc).getModeloplaca());
			Label procesador = new Label("Procesador: " + ((Laptop) pc).getProcesador());
			Label ram = new Label("Memoria RAM: " + ((Laptop) pc).getTiporam() + " Capacidad: " + ((Laptop) pc).getCapram());
			Label hdd = new Label("Marca de HDD: " + ((Laptop) pc).getMarcahdd() + " Capacidad: " + ((Laptop) pc).getCaphdd());
			Label optica = new Label("Unidad óptica: " + ((Laptop) pc).getOptica());
			Label red = new Label("Tarjeta de red: " + ((Laptop) pc).getRed());
			Label display = new Label("Display: " + ((Laptop) pc).getDisplay());
			detalles.addComponents(inventario, board, procesador, ram, hdd, optica, red, display);
		} else {
			Torre torre = obtenerTorre(mediosLista);
			Label fuente = new Label("Fuente: " + torre.getFuente());
			Label board = new Label("Motherboard: " + torre.getBoard());
			Label procesador = new Label("Procesador: " + torre.getProcesador());
			Label ram = new Label("Memoria RAM: " + torre.getTiporam() + " Capacidad: " + torre.getRam());
			Label hdd = new Label("Marca de HDD: " + torre.getMarcahdd() + " Capacidad: " + torre.getHdd());
			Label optica = new Label("Unidad óptica: " + torre.getOptica());
			Label red = new Label("Tarjeta de red: " + torre.getRed() + " Dir. MAC: " + torre.getMac());
			Label otros = new Label("Otros: " + torre.getOtros());
			detalles.addComponents(fuente, board, procesador, ram, hdd, optica, red, otros);
		}

		detalleshw.setContent(detalles);

		masInfo.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				container.replaceComponent(event.getComponent(), mediosPanel);
				container.addComponent(detalleshw, container.getComponentCount() - 1);
				container.addComponent(closeInfo, container.getComponentCount() - 1);
			}
		});

		closeInfo.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				container.removeComponent(detalleshw);
				container.replaceComponent(mediosPanel, masInfo);
				container.removeComponent(closeInfo);
			}
		});

		container.addComponents(datosBasico, masInfo, controles);
		main.addComponent(container, "formulario");
		setCompositionRoot(main);
	}

	// obligado
	public void setEntidad(Entidad entidad) {
		// lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la
		// entidad
		this.pc = (PC) entidad;
	}

	public Torre obtenerTorre(List<MedioInformatico> lista) {
		int i = 0;
		boolean enc = false;
		while (i < lista.size() && !enc) {
			if (lista.get(i) instanceof Torre) {
				enc = true;
			} else {
				i++;
			}
		}
		return (Torre) lista.get(i);
	}
}
